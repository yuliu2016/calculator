package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.util.FirstAndMore;
import org.fugalang.core.grammar.util.ParserStringUtil;
import org.fugalang.core.parser.RuleType;

import java.util.*;

public class ClassBuilder {
    private final String packageName;
    private final String className;
    private final String printName;

    private final List<ClassField> fields = new ArrayList<>();
    private final Map<String, Integer> fieldNameCounter = new HashMap<>();

    private final Set<String> classImports = new HashSet<>();

    private String headerComments = null;

    private RuleType ruleType = null;

    public ClassBuilder(String packageName, String className, String printName) {
        this.packageName = packageName;
        this.className = className;
        this.printName = printName;

        resolvePrelude();
    }

    private void resolvePrelude() {
        addImport("org.fugalang.core.parser.*");
    }

    public String getClassName() {
        return className;
    }

    public String generateClassCode() {
        return generateClassCode(List.of());
    }

    public Set<String> getClassImports() {
        return classImports;
    }

    public String generateClassCode(List<ClassBuilder> componentClasses) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("package ")
                .append(packageName)
                .append(";\n\n");

        Set<String> userImports = new TreeSet<>();
        Set<String> javaImports = new TreeSet<>();

        var importIterator = componentClasses.stream().map(ClassBuilder::getClassImports).iterator();

        for (Set<String> importSet : FirstAndMore.of(classImports, importIterator)) {
            for (String theImport : importSet) {
                if (theImport.startsWith("java")) {
                    javaImports.add(theImport);
                } else {
                    userImports.add(theImport);
                }
            }
        }

        for (String classImport : userImports) {
            sb.append("import ")
                    .append(classImport)
                    .append(";\n");
        }

        if (!userImports.isEmpty()) {
            sb.append("\n");
        }

        for (String classImport : javaImports) {
            sb.append("import ")
                    .append(classImport)
                    .append(";\n");
        }

        if (!javaImports.isEmpty()) {
            sb.append("\n");
        }

        sb.append(generateClassBody(false));

        for (ClassBuilder componentClass : componentClasses) {
            var classDef = componentClass.generateClassBody(true);
            sb.append(ParserStringUtil.indent(classDef, 4));
        }

        // add final closing bracket
        sb.append("}\n");

        return sb.toString();
    }

    public String generateClassBody(boolean isStaticInnerClass) {
        StringBuilder sb = new StringBuilder();

        if (isStaticInnerClass) {
            sb.append("\n");
        }

        if (headerComments != null && !headerComments.isBlank()) {
            sb.append("/**\n * ")
                    .append(headerComments)
                    .append("\n */\n");
        }

        if (isStaticInnerClass) {
            sb.append("public static final class ");
        } else {
            sb.append("public final class ");
        }

        sb.append(className);

        // extends

        if (ruleType != null) {
            // add the parent classes
            sb.append(" extends NodeWrapper");
        } else {
            throw new IllegalStateException("No Rule Type");
        }

        sb.append(" {\n\n");

        // rule name constant
        sb.append("    public static final ParserRule RULE =\n")
                .append("            new ParserRule(\"")
                .append(printName)
                .append("\", RuleType.")
                .append(ruleType.name())
                .append(", ")
                .append(isStaticInnerClass ? "false" : "true")
                .append(");\n\n");

        sb.append("    public static ").append(className).append(" of(ParseTreeNode node) {\n")
                .append("        return new ").append(className).append("(node);\n")
                .append("    }\n\n");

        sb.append("    private ")
                .append(className);

        sb.append("(ParseTreeNode node) {\n" +
                "        super(RULE, node);\n" +
                "    }\n");

        for (int i = 0; i < fields.size(); i++) {
            ClassField field = fields.get(i);

            var getter = field.asGetter(ruleType, i);
            if (getter != null) {
                sb.append(getter);
            }

            var absentCheck = field.asAbsentCheck(ruleType, i);
            if (absentCheck != null) {
                sb.append(absentCheck);
            }
        }

        generateParsingFunc(sb);

        for (ClassField field : fields) {
            var loopParser = field.getLoopParser();
            if (loopParser != null) {
                sb.append(loopParser);
            }
        }

        if (isStaticInnerClass) {
            sb.append("}\n");
        }
        // else don't append, since external classes need
        // to add the final quotes

        return sb.toString();
    }

    private void generateParsingFunc(StringBuilder sb) {
        sb.append("\n");
        sb.append("    public static boolean parse(ParseTree parseTree, int level) {\n");

        var mb = new StringBuilder();
        mb.append("if (!ParserUtil.recursionGuard(level, RULE)) return false;\n");
        mb.append("var marker = parseTree.enter(level, RULE);\n");
        mb.append("boolean result;\n\n");

        var first = true;
        for (ClassField field : fields) {
            var result = field.asParserStmt(ruleType, first);
            if (field.isRequired()) {
                first = false;
            }
            mb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule for class " + className +
                    " may match an empty string");
        }

        mb.append("\nparseTree.exit(level, marker, result);\n");
        mb.append("return result;\n");

        sb.append(ParserStringUtil.indent(mb.toString(), 8));

        sb.append("    }\n");
    }

    public void addImport(String classImport) {
        classImports.add(classImport);
    }

    public void addField(ClassField classField) {
        var fieldName = classField.getFieldName();

        if (fieldNameCounter.containsKey(fieldName)) {
            int cnt = fieldNameCounter.get(fieldName) + 1;
            fieldNameCounter.put(fieldName, cnt);

            // modify the field name with the field count
            fields.add(classField.withFieldName(fieldName + cnt));
        } else {
            fieldNameCounter.put(fieldName, 0);
            fields.add(classField);
        }
    }

    /**
     * A rule cannot be matched against an empty string
     */
    public void guardMatchEmptyString() {
        if (fields.stream().noneMatch(ClassField::isRequired)) {
            throw new IllegalStateException("The rule for class " + className +
                    " may match an empty string");
        }
    }

    public void setHeaderComments(String headerComments) {
        this.headerComments = headerComments;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }
}
