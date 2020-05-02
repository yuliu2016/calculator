package org.fugalang.core.grammar.classbuilder;


import org.fugalang.core.grammar.gen.ParserStringUtil;
import org.fugalang.core.parser.RuleType;

public class ClassField {
    private final ClassName className;
    private final String fieldName;
    private final FieldType fieldType;
    private final ResultSource resultSource;

    public ClassField(ClassName className, String fieldName, FieldType fieldType, ResultSource resultSource) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.resultSource = resultSource;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isOptionalSingle() {
        return fieldType == FieldType.Optional;
    }

    public boolean isRequired() {
        return fieldType == FieldType.Required || fieldType == FieldType.RequiredList;
    }

    public boolean isSingular() {
        return fieldType == FieldType.Required || fieldType == FieldType.Optional;
    }

    /**
     * Used for field name conflict resolution
     */
    public ClassField withFieldName(String newFieldName) {
        return new ClassField(className, newFieldName, fieldType, resultSource);
    }

    public String asFieldDeclaration(int index) {
        if (isSingular()) {
            return "";
        }
        return "    private " + className.asType() + " " + fieldName + ";\n";
    }

    public String asGetter(int index) {
        return "    public " + className.asType() + " " + fieldName + "() {\n" +
                asGetterBody(index) +
                "    }\n";
    }

    private String asGetterBody(int index) {
        return switch (resultSource.getSourceType()) {
            case Class -> {
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        if (!element.isPresent()) return null;\n" +
                            "        return " + className.asType() + ".of(element);\n";
                }
                yield listTemplate(index, className.getRealClassName() + ".of(node)");
            }

            case TokenType -> {
                if (isSingular()) {
                    var cast = className.asType().equals("Object") ? "" :
                            "(" + className.asType() + ") ";
                    yield "        var element = getItem(" + index + ");\n" +
                            "        if (!element.isPresent()) return null;\n" +
                            "        return " + cast + "element.asObject();\n";
                }
                var cast = className.getRealClassName().equals("Object") ? "" :
                        "(" + className.getRealClassName() + ") ";
                yield listTemplate(index, cast + "node.asObject()");
            }
            case TokenLiteral -> {
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        return element.asBoolean();\n";
                }
                yield listTemplate(index, "node.asBoolean()");
            }
        };
    }

    private String listTemplate(int index, String covertExpr) {
        return "        if (" + fieldName + " != null) {\n" +
                "            return " + fieldName + ";\n" +
                "        }\n" +
                "        " + className.asType() + " result = null;\n" +
                "        var element = getItem(" + index + ");\n" +
                "        for (var node : element.asCollection()) {\n" +
                "            if (result == null) result = new ArrayList<>();\n" +
                "            result.add(" + covertExpr + ");\n" +
                "        }\n" +
                "        " + fieldName + " = result == null ? Collections.emptyList() : result;\n" +
                "        return " + fieldName + ";\n";
    }

    public String asNullCheck() {
        if (className.isNotNull()) {
            return "";
        }
        return "\n    public boolean has" +
                ParserStringUtil.capitalizeFirstCharOnly(fieldName) +
                "() {\n" +
                "        return " + fieldName + "() != null;\n" +
                "    }\n";
    }

    public String asRuleStmt(RuleType ruleType) {
        switch (ruleType) {
            case Disjunction -> {
                return "addChoice(\"" + fieldName + "\", " + fieldName + "());";
            }
            case Conjunction -> {
                if (isOptionalSingle()) {
                    return "addOptional(\"" + fieldName + "\", " + fieldName + "());";
                } else {
                    return "addRequired(\"" + fieldName + "\", " + fieldName + "());";
                }
            }
            default -> throw new IllegalArgumentException("ClassField does not support RuleType" + ruleType);
        }
    }

    public String asParserStmt(RuleType ruleType, boolean isFirst) {
        var resultExpr = getResultExpr();

        return switch (fieldType) {
            case Required -> getRequiredStmt(resultExpr, ruleType, isFirst);
            case Optional -> resultExpr + ";\n";
            case OptionalList -> getLoopStmt("", resultExpr);
            case RequiredList -> getLoopStmt(getRequiredStmt(resultExpr, ruleType, isFirst), resultExpr);
        };
    }

    private String getLoopStmt(String requiredStmt, String resultExpr) {
        return "parseTree.enterCollection();\n" + requiredStmt +
                "while (true) {\n" +
                "    var pos = parseTree.position();\n" +
                "    if (!" + resultExpr + " ||\n" +
                "            parseTree.guardLoopExit(pos)) {\n" +
                "        break;\n" +
                "    }\n" +
                "}\n" +
                "parseTree.exitCollection();\n";
    }

    private String getRequiredStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        return isFirst ?
                "result = " + resultExpr + ";\n" :
                switch (ruleType) {
                    case Conjunction -> "result = result && " + resultExpr + ";\n";
                    case Disjunction -> "result = result || " + resultExpr + ";\n";
                };
    }

    private String getResultExpr() {
        return switch (resultSource.getSourceType()) {
            case Class -> resultSource.getValue() + ".parse(parseTree, level + 1)";
            case TokenType -> "parseTree.consumeTokenType(\"" + resultSource.getValue() + "\")";
            case TokenLiteral -> "parseTree.consumeTokenLiteral(\"" + resultSource.getValue() + "\")";
        };
    }
}
