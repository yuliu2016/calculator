package org.fugalang.core.grammar.classbuilder;

import java.util.ArrayList;
import java.util.List;

public class ClassBuilder {
    private final String packageName;
    private final String className;

    private final List<ClassField> fields = new ArrayList<>();
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

        if (headerComments != null && !headerComments.isBlank()) {
            sb.append("// ")
                    .append(headerComments)
                    .append("\n");
        }

        sb
                .append("public class ")
                .append(className)
                .append(" {\n");

        for (ClassField field : getFields()) {
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

        for (ClassField field : getFields()) {
            sb.append("        ");
            sb.append(field.asConstructorStmt());
            sb.append("\n");
        }

        sb.append("    }\n}\n");

        return sb.toString();
    }

    public List<ClassField> getFields() {
        return fields;
    }

    public void addField(String type, String name) {
        // add imports here
        fields.add(new ClassField(type, name));
    }

    public String getHeaderComments() {
        return headerComments;
    }

    public void setHeaderComments(String headerComments) {
        this.headerComments = headerComments;
    }
}
