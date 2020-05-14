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

    public String asGetter(RuleType ruleType, int index) {
        var body = asGetterBody(ruleType, index);
        if (body == null) {
            return null;
        }
        return "\n    public " + className.asType() + " " + fieldName + "() {\n" +
                body +
                "    }\n";
    }

    private String asGetterBody(RuleType ruleType, int index) {
        return switch (resultSource.getType()) {
            case Class -> {
                if (isSingular()) {
                    yield "        return " + className.asType() + ".of(getItem(" + index + "));\n";
                }
                yield "        return getList(" + index + ", " + className.getRealClassName() + "::of);\n";
            }

            case TokenType -> {
                if (isSingular()) {
                    yield "        return getItemOfType(" + index + "," +
                            resultSource.getValue() + ");\n";
                }
                yield "        return getList(" + index + ", ParseTreeNode::asString);\n";
            }
            case TokenLiteral -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        return getBoolean(" + index + ");\n";
                }
                yield "        return getList(" + index + ", ParseTreeNode::asBoolean);\n";
            }
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
                    yield "        return hasItemOfRule(" + index + ", " +
                            className.asType() + ".RULE);\n";
                }
                yield null;
            }

            case TokenType -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        return hasItemOfType(" + index + ", " +
                            resultSource.getValue() + ");\n";
                }
                yield null;
            }
            case TokenLiteral -> null;
        };
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
                "        if (!ParserUtil.recursionGuard(level, RULE)) return false;\n" +
                "        parseTree.enterCollection();\n" +
                "        var result = " + resultExpr + ";\n" +
                "        if (result) while (true) {\n" +
                "            var pos = parseTree.position();\n" +
                "            if (!" + resultExpr + ") break;\n" +
                "            if (parseTree.guardLoopExit(pos)) break;\n" +
                "        }\n" +
                "        parseTree.exitCollection();\n" +
                "        return result;\n" +
                "    }\n";
    }

    private String getOptionalLoopParser() {
        var resultExpr = getResultExpr();
        var name = ParserStringUtil.capitalizeFirstCharOnly(fieldName);
        return "\n    private static void parse" + name + "(ParseTree parseTree, int level) {\n" +
                "        if (!ParserUtil.recursionGuard(level, RULE)) return;\n" +
                "        parseTree.enterCollection();\n" +
                "        while (true) {\n" +
                "            var pos = parseTree.position();\n" +
                "            if (!" + resultExpr + ") break;\n" +
                "            if (parseTree.guardLoopExit(pos)) break;\n" +
                "        }\n" +
                "        parseTree.exitCollection();\n" +
                "    }\n";
    }
}
