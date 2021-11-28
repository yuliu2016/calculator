package org.fugalang.grammar.classbuilder;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.transform.JPackageOutput;
import org.fugalang.grammar.util.FirstAndMore;
import org.fugalang.grammar.util.StringUtil;

import java.util.*;

@Deprecated
public class ClassBuilder {
    private final JPackageOutput packageOutput;
    private final ClassName className;
    private final boolean leftRecursive;

    private final List<ClassField> fields = new ArrayList<>();
    private final Map<String, Integer> fieldNameCounter = new HashMap<>();

    private final Set<String> classImports = new HashSet<>();

    private String headerComments = null;

    private RuleType ruleType = null;

    public ClassBuilder(JPackageOutput packageOutput,
                        ClassName className, boolean leftRecursive) {
        this.packageOutput = packageOutput;
        this.className = className;
        this.leftRecursive = leftRecursive;

        resolvePrelude();
    }

    private void resolvePrelude() {
        addImport("org.fugalang.core.parser.NodeWrapper");
        addImport("org.fugalang.core.parser.ParseTreeNode");
    }

    public String getClassName() {
        return className.getType();
    }

    public String getRuleName() {
        return className.getRuleName();
    }

    public String generateClassCode() {
        return generateClassCode(List.of());
    }

    public Set<String> getClassImports() {
        return classImports;
    }

    public boolean isLeftRecursive() {
        return leftRecursive;
    }

    public String generateClassCode(List<ClassBuilder> components) {
        StringBuilder sb = new StringBuilder();

        sb
                .append("package ")
                .append(packageOutput.getWrapperPackage())
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
            sb.append(StringUtil.indent(classDef, 4));
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
            sb.append(StringUtil.javadoc(headerComments, 0));
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

        sb.append("    public ")
                .append(className);

        sb.append("(ParseTreeNode node) {\n" + "        super(node);\n    }\n");

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

    public void generateVisitor(StringBuilder sb) {
        sb.append("\n");
        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 4));
        }
        sb.append("    default T visit")
                .append(className.getType())
                .append("(")
                .append(className.getType())
                .append(" ")
                .append(className.decapName())
                .append(") {\n        return null;\n    }\n");
    }

    public void generateRuleDeclaration(StringBuilder sb) {
        var small_name = getRuleName().replace(":", "_");
        var cap_name = small_name.toUpperCase();
        sb.append("    public static final ParserRule ")
                .append(cap_name)
                .append(" = ")
                .append(ruleType == RuleType.Conjunction ? "and_rule" :
                        leftRecursive ? "leftrec_rule" : "or_rule")
                .append("(\"")
                .append(getRuleName())
                .append("\");\n");
    }

    public void generateParsingFunction(StringBuilder sb, boolean isNamedRule) {
        var small_name = getRuleName().replace(":", "_");
        var cap_name = small_name.toUpperCase();
        sb.append("\n");

        // rule name constant

        if (headerComments != null && !headerComments.isBlank()) {
            sb.append(StringUtil.javadoc(headerComments, 4));
        }

        var visibility = isNamedRule ? "public" : "private";
        sb.append("    ").append(visibility).append(" static boolean ")
                .append(small_name)
                .append("(ParseTree t) {\n");

        sb.append("        var m = t.enter(")
                .append(cap_name).append(");\n");
        sb.append("        if (m != null) return m;\n");

        if (leftRecursive) {
            if (!isNamedRule) {
                throw new IllegalStateException(
                        "Cannot be left-recursive and not a named rule");
            }
            generateLeftRecursiveBody(sb);
        } else {
            generateNormalParserBody(sb);
        }

        sb.append("    }\n");

        for (ClassField field : fields) {
            var loopParser = field.getLoopParser();
            if (loopParser != null) {
                sb.append(loopParser);
            }
        }
    }

    private void generateNormalParserBody(StringBuilder sb) {
        sb.append("        boolean r;\n");

        var first = true;
        for (ClassField field : fields) {
            var result = field.getParserFieldStatement(ruleType, first);
            if (field.isRequired()) {
                first = false;
            }
            sb.append("        ");
            sb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule for class " + className +
                    " may match an empty string");
        }

        sb.append("        t.exit(r);\n" +
                "        return r;\n");
    }

    private void generateLeftRecursiveBody(StringBuilder sb) {
        sb.append("        var p = t.position();\n" +
                "        boolean s = false;\n" +
                "        while (true) {\n" +
                "            t.cache(s);\n" +
                "            boolean r;\n");
        var first = true;
        for (ClassField field : fields) {
            var result = field.getParserFieldStatement(ruleType, first);
            if (field.isRequired()) {
                first = false;
            }
            sb.append("            ");
            sb.append(result);
        }

        if (first) {
            throw new IllegalStateException("The rule for class " + className +
                    " may match an empty string");
        }

        sb.append("            s = r || s;\n" +
                "            var e = t.position();\n" +
                "            if (e <= p) break;\n" +
                "            p = e;\n" +
                "        }\n" +
                "        t.restore(p);\n" +
                "        t.exit(s);\n" +
                "        return s;\n");
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
