package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.ParseStringUtil;

import java.util.*;

public class ClassBuilder {
    private final String packageName;
    private final String className;

    private final List<ClassField> fields = new ArrayList<>();
    private final Map<String, Integer> fieldNameCounter = new HashMap<>();

    private final Set<String> classImports = new HashSet<>();

    private String headerComments = null;

    public ClassBuilder(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
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

        if (!classImports.isEmpty()){
            sb.append("\n");
        }

        sb.append(generateClassBody(false));

        for (ClassBuilder componentClass : componentClasses) {
            var classDef = componentClass.generateClassBody(true);
            sb.append(ParseStringUtil.indent(classDef, 4));
        }

        // add final closing bracket
        sb.append("}\n");

        return sb.toString();
    }

    public String generateClassBody(boolean isStaticInnerClass) {
        StringBuilder sb = new StringBuilder();

        if(isStaticInnerClass) {
            sb.append("\n");
        }

        if (headerComments != null && !headerComments.isBlank()) {
            sb.append("// ")
                    .append(headerComments)
                    .append("\n");
        }

        if (isStaticInnerClass) {
            sb.append("public static class ");
        } else {
            sb.append("public class ");
        }

        sb.append(className).append(" {\n");

        for (ClassField field : fields) {
            sb.append("    ");
            sb.append(field.asFieldDeclaration());
            sb.append("\n");
        }

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

        sb.append("    }\n");

        for (ClassField field: fields) {
            sb.append("\n");
            sb.append(field.asGetter());
        }

        if (isStaticInnerClass) {
            sb.append("}\n");
        }
        // else don't append, since external classes need
        // to add the final quotes

        return sb.toString();
    }

    public void addImport(String classImport) {
        classImports.add(classImport);
    }

    public void addFieldByClassName(ClassName type, String name, boolean isOptional) {
        String actualName;

        if (fieldNameCounter.containsKey(name)) {
            int cnt = fieldNameCounter.get(name) + 1;
            actualName = name + cnt;
            fieldNameCounter.put(name, cnt);
        } else {
            actualName = name;
            fieldNameCounter.put(name, 0);
        }

        if (isOptional) {
            addImport("java.util.Optional");
        }

        // add imports here
        fields.add(new ClassField(type, actualName, isOptional));
    }

    public void setHeaderComments(String headerComments) {
        this.headerComments = headerComments;
    }
}
