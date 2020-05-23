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
        return "\n    public " + className.getType() + " " + fieldName + "() {\n" +
                body +
                "    }\n";
    }

    private String asGetterBody(RuleType ruleType, int index) {
        return switch (resultSource.getType()) {
            case Class -> {
                if (isSingular()) {
                    yield "        return get(" + index + ", " + className.getType() + "::of);\n";
                }
                yield "        return getList(" + index + ", " + className.getRealClassName() + "::of);\n";
            }

            case TokenType -> {
                if (isSingular()) {
                    yield "        return get(" + index + ", " + resultSource.getValue() + ");\n";
                }
                yield "        return getList(" + index + ", ParseTreeNode::asString);\n";
            }
            case TokenLiteral -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        return is(" + index + ");\n";
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
                ParserStringUtil.capitalizeFirstChar(fieldName) +
                "() {\n" + body + "    }\n";
    }

    private String absentCheckBody(RuleType ruleType, int index) {
        return switch (resultSource.getType()) {
            case Class -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        return has(" + index + ", " +
                            className.getType() + ".RULE);\n";
                }
                yield null;
            }

            case TokenType -> {
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    yield null;
                }
                if (isSingular()) {
                    yield "        return has(" + index + ", " +
                            resultSource.getValue() + ");\n";
                }
                yield null;
            }
            case TokenLiteral -> null;
        };
    }

    public String getParserFieldStatement(RuleType ruleType, boolean isFirst) {
        var resultExpr = getResultExpr();

        return switch (fieldType) {
            case Required -> getRequiredStmt(resultExpr, ruleType, isFirst);
            case Optional -> getOptionalStmt(resultExpr, ruleType, isFirst);
            case RequiredList -> getRequiredStmt(getLoopExpr(), ruleType, isFirst);
            case OptionalList -> getOptionalStmt(getLoopExpr(), ruleType, isFirst);
        };
    }

    private String getLoopExpr() {
        var rule_name = className.getRuleName().replace(":", "_");
        return rule_name + "_loop(t, lv)";
    }

    private String getOptionalStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "" : switch (ruleType) {
            case Conjunction -> "if (r) ";
            case Disjunction -> "if (!r) ";
        };
        return condition + resultExpr + ";\n";
    }

    private String getRequiredStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "r = " :
                switch (ruleType) {
                    case Conjunction -> "r = r && ";
                    case Disjunction -> "r = r || ";
                };
        return condition + resultExpr + ";\n";
    }

    private String getResultExpr() {
        return switch (resultSource.getType()) {
            case Class -> resultSource.getValue().replace(":", "_") + "(t, lv + 1)";
            case TokenType -> "t.consume(" + resultSource.getValue() + ")";
            case TokenLiteral -> "t.consume(\"" + resultSource.getValue() + "\")";
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
        var rule_name = className.getRuleName().replace(":", "_");
        return "\n    private static boolean " + rule_name + "_loop(ParseTree t, int lv) {\n" +
                "        t.enterCollection();\n" +
                "        var r = " + resultExpr + ";\n" +
                "        if (r) while (true) {\n" +
                "            var p = t.position();\n" +
                "            if (!" + resultExpr + " || t.loopGuard(p)) break;\n" +
                "        }\n" +
                "        t.exitCollection();\n" +
                "        return r;\n" +
                "    }\n";
    }

    private String getOptionalLoopParser() {
        var resultExpr = getResultExpr();
        var rule_name = className.getRuleName().replace(":", "_");
        return "\n    private static void " + rule_name + "_loop(ParseTree t, int lv) {\n" +
                "        t.enterCollection();\n" +
                "        while (true) {\n" +
                "            var p = t.position();\n" +
                "            if (!" + resultExpr + " || t.loopGuard(p)) break;\n" +
                "        }\n" +
                "        t.exitCollection();\n" +
                "    }\n";
    }
}
