package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.ParserStringUtil;

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
        addImport("org.fugalang.core.parser.ParseTree");
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

        Set<String> classImports = new HashSet<>(this.classImports);

        // merge the imports
        for (ClassBuilder componentClass : componentClasses) {
            classImports.addAll(componentClass.getClassImports());
        }

        for (String classImport : classImports) {
            sb.append("import ")
                    .append(classImport)
                    .append(";\n");
        }

        if (!classImports.isEmpty()) {
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
            sb.append("// ")
                    .append(headerComments)
                    .append("\n");
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
            sb.append(" extends ").append(ruleType.getSuperClassShort());
        } else {
            throw new IllegalStateException("No Rule Type");
        }

        sb.append(" {\n");

        // rule name constant
        sb.append("    public static final String RULE_NAME = \"")
                .append(printName)
                .append("\";\n\n");

        for (ClassField field : fields) {
            sb.append("    ");
            sb.append(field.asFieldDeclaration());
            sb.append("\n");
        }

        generateConstructorAndOverride(sb, isStaticInnerClass);

        for (ClassField field : fields) {
            sb.append("\n");
            sb.append(field.asGetter());
        }

        generateParsingFunc(sb);

        if (isStaticInnerClass) {
            sb.append("}\n");
        }
        // else don't append, since external classes need
        // to add the final quotes

        return sb.toString();
    }

    private void generateConstructorAndOverride(StringBuilder sb, boolean isStaticInnerClass) {
        sb.append("\n")
                .append("    public ")
                .append(className)
                .append("(\n");

        for (int i = 0; i < fields.size(); i++) {
            ClassField field = fields.get(i);
            sb.append("            ");
            sb.append(field.asConstructorArg());
            if (i < fields.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }

        sb.append("    ) {\n");

        for (ClassField field : fields) {
            sb.append("        ");
            sb.append(field.asConstructorStmt());
            sb.append("\n");
        }
        sb.append("    }\n\n");
        sb.append("    @Override\n    protected void buildRule() {\n");

        if (isStaticInnerClass) {
            sb.append("        setImpliedName(RULE_NAME);\n");
        } else {
            sb.append("        setExplicitName(RULE_NAME);\n");
        }

        for (ClassField field : fields) {
            sb.append("        ");
            sb.append(field.asRuleStmt(ruleType));
            sb.append("\n");
        }
        sb.append("    }\n");
    }

    private void generateParsingFunc(StringBuilder sb) {
        sb.append("\n");
        sb.append("    public static boolean parse(ParseTree parseTree, int level) {\n");

        var mb = new StringBuilder();
        mb.append("if (!ParseTree.recursionGuard(level, RULE_NAME)) {\n    return false;\n}\n");
        mb.append("var marker = parseTree.enter(level, RULE_NAME);\n");
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

        if (classField.isOptionalSingle()) {
            addImport("java.util.Optional");
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
        // remove extra imports if already set
        if (this.ruleType != null) {
            for (RuleType value : RuleType.values()) {
                classImports.remove(value.getSuperClass());
            }
        }
        classImports.add(ruleType.getSuperClass());
        this.ruleType = ruleType;
    }
}
