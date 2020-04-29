package org.fugalang.core.grammar.classbuilder;

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

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String generateClassCode() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("package ")
                .append(packageName)
                .append(";\n\n");

        for (String classImport : classImports) {
            sb.append("import ")
                    .append(classImport)
                    .append(";\n");
        }

        if (!classImports.isEmpty()) {
            sb.append("\n");
        }

        if (headerComments != null && !headerComments.isBlank()) {
            sb.append("// ")
                    .append(headerComments)
                    .append("\n");
        }

        sb
                .append("public class ")
                .append(className)
                .append(" {\n");

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
            if ( i < fields.size() - 1) {
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

        sb.append("    }\n}\n");

        return sb.toString();
    }

    public void addField(String type, String name) {

        String actualName;

        if (fieldNameCounter.containsKey(name)) {
            int cnt = fieldNameCounter.get(name) + 1;
            actualName = name + cnt;
            fieldNameCounter.put(name, cnt);
        } else {
            actualName = name;
            fieldNameCounter.put(name, 0);
        }

        if (type.startsWith("List")) {
            classImports.add("java.util.List");
        }

        // add imports here
        fields.add(new ClassField(type, actualName));
    }

    public void setHeaderComments(String headerComments) {
        this.headerComments = headerComments;
    }
}
