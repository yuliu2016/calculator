package org.fugalang.core.grammar.classbuilder;

import java.util.ArrayList;
import java.util.List;

public class ClassBuilder {
    private final String packageName;
    private final String className;

    private final List<ClassField> fields = new ArrayList<>();

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

    public String getClassCode() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("// Auto Generated Parser\npackage ")
                .append(packageName)
                .append(";\n\npublic class ")
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
                .append("(");

        for (int i = 0; i < fields.size(); i++) {
            ClassField field = fields.get(i);
            sb.append(field.asConstructorArg());
            if ( i < fields.size() - 1) {
                sb.append(",");
            }
        }

        sb.append(") {\n");

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
}
