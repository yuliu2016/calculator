package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.util.FirstAndMore;
import org.fugalang.core.grammar.util.ParserStringUtil;
import org.fugalang.core.parser.RuleType;

import java.util.*;

public class ClassBuilder {
    private final String packageName;
    private final String className;
    private final String ruleName;

    private final List<ClassField> fields = new ArrayList<>();
    private final Map<String, Integer> fieldNameCounter = new HashMap<>();

    private final Set<String> classImports = new HashSet<>();

    private String headerComments = null;

    private RuleType ruleType = null;

    public ClassBuilder(String packageName, String className, String ruleName) {
        this.packageName = packageName;
        this.className = className;
        this.ruleName = ruleName;

        resolvePrelude();
    }

    private void resolvePrelude() {
        addImport("org.fugalang.core.parser.*");
    }

    public String getClassName() {
        return className;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String generateClassCode() {
        return generateClassCode(List.of());
    }

    public Set<String> getClassImports() {
        return classImports;
    }

    public String generateClassCode(List<ClassBuilder> components) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("package ")
                .append(packageName)
                .append(";\n\n");

        Set<String> userImports = new TreeSet<>();
        Set<String> javaImports = new TreeSet<>();

        var importIterator = components.stream().map(ClassBuilder::getClassImports).iterator();

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

        for (ClassBuilder componentClass : components) {
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

        sb.append(" {\n");

        // rule name constant
        sb.append("    public static final ParserRule RULE =\n")
                .append("            ParserRule.of(\"")
                .append(ruleName)
                .append("\", RuleType.")
                .append(ruleType.name())
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

        if (isStaticInnerClass) {
            sb.append("}\n");
        }
        // else don't append, since external classes need
        // to add the final quotes

        return sb.toString();
    }

    public void generateRuleDeclaration(StringBuilder sb) {
        var small_name = ruleName.replace(":", "_");
        var cap_name = small_name.toUpperCase();
        sb.append("    public static final ParserRule ")
                .append(cap_name)
                .append(" = ")
                .append(switch (ruleType) {
                    case Conjunction -> "and_rule";
                    case Disjunction -> "or_rule";
                })
                .append("(\"")
                .append(ruleName)
                .append("\");\n");
    }

    public void generateParsingFunction(StringBuilder sb) {
        var small_name = ruleName.replace(":", "_");
        var cap_name = small_name.toUpperCase();
        sb.append("\n");

        // rule name constant

        if (headerComments != null && !headerComments.isBlank()) {
            sb.append("    /**\n     * ")
                    .append(headerComments)
                    .append("\n     */\n");
        }

        sb.append("    public static boolean ")
                .append(small_name)
                .append("(ParseTree t, int lv) {\n");

        var mb = new StringBuilder();
        mb.append("if (t.recursionGuard(lv)) return false;\n" + "t.enter(lv, ")
                .append(cap_name)
                .append(");\n" + "boolean r;\n");

        var first = true;
        for (ClassField field : fields) {
            var result = field.getParserFieldStatement(ruleType, first);
            if (field.isRequired()) {
                first = false;
            }
            mb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule for class " + className +
                    " may match an empty string");
        }

        mb.append("t.exit(r);\n" +
                "return r;\n");

        sb.append(ParserStringUtil.indent(mb.toString(), 8));

        sb.append("    }\n");

        for (ClassField field : fields) {
            var loopParser = field.getLoopParser();
            if (loopParser != null) {
                sb.append(loopParser);
            }
        }
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
