package org.fugalang.core.grammar.classbuilder;


import org.fugalang.core.grammar.util.ParserStringUtil;
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

    public String asFieldDeclaration() {
        if (isSingular()) {
            return "";
        }
        return "    private " + className.asType() + " " + fieldName + ";\n";
    }

    public String asGetter(RuleType ruleType, int index) {
        return "\n    public " + className.asType() + " " + fieldName + "() {\n" +
                asGetterBody(ruleType, index) +
                "    }\n";
    }

    private String asGetterBody(RuleType ruleType, int index) {
        return switch (resultSource.getType()) {
            case Class -> {
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        element.failIfAbsent(" + className.asType() + ".RULE);\n" +
                            "        return " + className.asType() + ".of(element);\n";
                }
                yield listTemplate(index, className.getRealClassName() + ".of(node)");
            }

            case TokenType -> {
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        element.failIfAbsent(" + resultSource.getValue() + ");\n" +
                            "        return element.asString();\n";
                }
                yield listTemplate(index, "node.asString()");
            }
            case TokenLiteral -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        element.failIfAbsent();\n" +
                            "        return element.asBoolean();\n";
                }
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
                "            if (result == null) {\n" +
                "                result = new ArrayList<>();\n" +
                "            }\n" +
                "            result.add(" + covertExpr + ");\n" +
                "        }\n" +
                "        " + fieldName + " = result == null ? Collections.emptyList() : result;\n" +
                "        return " + fieldName + ";\n";
    }

    public String asGetOrNull(RuleType ruleType, int index) {
        var body = getterOrNull(ruleType, index);
        if (body == null) {
            return null;
        }
        return "\n    public " + className.asType() + " " + fieldName +
                "OrNull() {\n" + body + "    }\n";
    }

    private String getterOrNull(RuleType ruleType, int index) {
        return switch (resultSource.getType()) {
            case Class -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        if (!element.isPresent(" + className.asType() + ".RULE)) {\n" +
                            "            return null;\n" +
                            "        }\n" +
                            "        return " + className.asType() + ".of(element);\n";
                }
                yield null;
            }

            case TokenType -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        if (!element.isPresent(" + resultSource.getValue() + ")) {\n" +
                            "            return null;\n" +
                            "        }\n" +
                            "        return element.asString();\n";
                }
                yield null;
            }
            case TokenLiteral -> null;
        };
    }

    public String asAbsentCheck(RuleType ruleType, int index) {
        var body = absentCheckBody(ruleType, index);
        if (body == null) {
            return null;
        }
        return "\n    public boolean has" +
                ParserStringUtil.capitalizeFirstCharOnly(fieldName) +
                "() {\n" + body + "    }\n";
    }

    private String absentCheckBody(RuleType ruleType, int index) {
        return switch (resultSource.getType()) {
            case Class -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        return element.isPresent(" + className.asType() + ".RULE);\n";
                }
                yield null;
            }

            case TokenType -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        var element = getItem(" + index + ");\n" +
                            "        return element.isPresent(" + resultSource.getValue() + ");\n";
                }
                yield null;
            }
            case TokenLiteral -> null;
        };
    }

    public String asRuleStmt(RuleType ruleType) {
        String callParams = switch (resultSource.getType()) {
            case Class, TokenType -> {
                String postfix;
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    postfix = "()";
                } else if (isSingular()) {
                    postfix = "OrNull()";
                } else {
                    postfix = "()";
                }
                yield fieldName + postfix;
            }
            case TokenLiteral -> {
                if (isSingular()) {
                    yield fieldName + "(), \"" + resultSource.getValue() + "\"";
                } else {
                    yield fieldName + "()";
                }
            }
        };

        switch (ruleType) {
            case Disjunction -> {
                return "addChoice(" + callParams + ");";
            }
            case Conjunction -> {
                if (fieldType == FieldType.Optional) {
                    return "addOptional(" + callParams + ");";
                } else {
                    return "addRequired(" + callParams + ");";
                }
            }
            default -> throw new IllegalArgumentException("ClassField does not support RuleType" + ruleType);
        }
    }

    public String asParserStmt(RuleType ruleType, boolean isFirst) {
        var resultExpr = getResultExpr();

        return switch (fieldType) {
            case Required -> getRequiredStmt(resultExpr, ruleType, isFirst);
            case Optional -> getOptionalStmt(resultExpr, ruleType, isFirst);
            case RequiredList -> getRequiredStmt(getLoopExpr(), ruleType, isFirst);
            case OptionalList -> getOptionalStmt(getLoopExpr(), ruleType, isFirst);
        };
    }

    private String getLoopExpr() {
        var name = ParserStringUtil.capitalizeFirstCharOnly(fieldName);
        return "parse" + name + "(parseTree, level + 1)";
    }

    private String getOptionalStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "" : switch (ruleType) {
            case Conjunction -> "if (result) ";
            case Disjunction -> "if (!result) ";
        };
        return condition + resultExpr + ";\n";
    }

    private String getRequiredStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "result = " :
                switch (ruleType) {
                    case Conjunction -> "result = result && ";
                    case Disjunction -> "result = result || ";
                };
        return condition + resultExpr + ";\n";
    }

    private String getResultExpr() {
        return switch (resultSource.getType()) {
            case Class -> resultSource.getValue() + ".parse(parseTree, level + 1)";
            case TokenType -> "parseTree.consumeToken(" + resultSource.getValue() + ")";
            case TokenLiteral -> "parseTree.consumeToken(\"" + resultSource.getValue() + "\")";
        };
    }

    public String getLoopParser() {
        if (isSingular()) {
            return null;
        }
        return fieldType == FieldType.RequiredList ?
                getRequiredLoopParser() : getOptionalLoopParser();
    }

    private String getRequiredLoopParser() {
        var resultExpr = getResultExpr();
        var name = ParserStringUtil.capitalizeFirstCharOnly(fieldName);
        return "\n    private static boolean parse" + name + "(ParseTree parseTree, int level) {\n" +
                "        if (!ParserUtil.recursionGuard(level, RULE)) {\n" +
                "            return false;\n" +
                "        }\n" +
                "        parseTree.enterCollection();\n" +
                "        var result = " + resultExpr + ";\n" +
                "        if (result) while (true) {\n" +
                "            var pos = parseTree.position();\n" +
                "            if (!" + resultExpr + " ||\n" +
                "                    parseTree.guardLoopExit(pos)) {\n" +
                "                break;\n" +
                "            }\n" +
                "        }\n" +
                "        parseTree.exitCollection();\n" +
                "        return result;\n" +
                "    }\n";
    }

    private String getOptionalLoopParser() {
        var resultExpr = getResultExpr();
        var name = ParserStringUtil.capitalizeFirstCharOnly(fieldName);
        return "\n    private static void parse" + name + "(ParseTree parseTree, int level) {\n" +
                "        if (!ParserUtil.recursionGuard(level, RULE)) {\n" +
                "            return;\n" +
                "        }\n" +
                "        parseTree.enterCollection();\n" +
                "        while (true) {\n" +
                "            var pos = parseTree.position();\n" +
                "            if (!" + resultExpr + " ||\n" +
                "                    parseTree.guardLoopExit(pos)) {\n" +
                "                break;\n" +
                "            }\n" +
                "        }\n" +
                "        parseTree.exitCollection();\n" +
                "    }\n";
    }
}
