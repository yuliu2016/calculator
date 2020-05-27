package org.fugalang.grammar.classbuilder;


import org.fugalang.grammar.util.ParserStringUtil;
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
        switch (resultSource.getType()) {
            case Class:
                if (isSingular()) {
                    return "        return get(" + index + ", " + className.getType() + ".class);\n";
                }
                return "        return getList(" + index + ", " + className.getRealClassName() + ".class);\n";
            case TokenType:
                if (isSingular()) {
                    return "        return get(" + index + ", " + resultSource.getValue() + ");\n";
                }
                return "        return getList(" + index + ", ParseTreeNode::asString);\n";
            case TokenLiteral:
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    return null;
                }
                if (isSingular()) {
                    return "        return is(" + index + ");\n";
                }
                return "        return getList(" + index + ", ParseTreeNode::asBoolean);\n";
            default:
                throw new IllegalArgumentException();
        }
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
        switch (resultSource.getType()) {
            case Class:
            case TokenType:
                if (ruleType == RuleType.Conjunction && fieldType == FieldType.Required) {
                    return null;
                }
                if (isSingular()) {
                    return "        return has(" + index + ");\n";
                }
                return null;
            case TokenLiteral:
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

    public String getParserFieldStatement(RuleType ruleType, boolean isFirst) {
        var resultExpr = getResultExpr();

        switch (fieldType) {
            case Required:
                return getRequiredStmt(resultExpr, ruleType, isFirst);
            case Optional:
                return getOptionalStmt(resultExpr, ruleType, isFirst);
            case RequiredList:
                return getRequiredStmt(getLoopExpr(), ruleType, isFirst);
            case OptionalList:
                return getOptionalStmt(getLoopExpr(), ruleType, isFirst);
            default:
                throw new IllegalArgumentException();
        }
    }

    private String getLoopExpr() {
        var rule_name = className.getRuleName().replace(":", "_");
        var rule_suffix = resultSource.getType() == SourceType.Class ? "_loop(t, lv)" : "_loop(t)";
        return rule_name + rule_suffix;
    }

    private String getOptionalStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "" :
                (ruleType == RuleType.Conjunction ?
                        "if (r) " : "if (!r) ");
        return condition + resultExpr + ";\n";
    }

    private String getRequiredStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        var condition = isFirst ? "r = " :
                (ruleType == RuleType.Conjunction ?
                        "r = r && " : "r = r || ");
        return condition + resultExpr + ";\n";
    }

    private String getResultExpr() {
        switch (resultSource.getType()) {
            case Class:
                return resultSource.getValue().replace(":", "_") + "(t, lv + 1)";
            case TokenType:
                return "t.consume(" + resultSource.getValue() + ")";
            case TokenLiteral:
                return "t.consume(\"" + resultSource.getValue() + "\")";
            default:
                throw new IllegalArgumentException();
        }
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
        var maybeLevel = resultSource.getType() == SourceType.Class ? ", int lv" : "";
        return "\n    private static boolean " + rule_name + "_loop(ParseTree t" + maybeLevel + ") {\n" +
                "        t.enterLoop();\n" +
                "        var r = " + resultExpr + ";\n" +
                "        if (r) while (true) {\n" +
                "            var p = t.position();\n" +
                "            if (!" + resultExpr + " || t.loopGuard(p)) break;\n" +
                "        }\n" +
                "        t.exitLoop();\n" +
                "        return r;\n" +
                "    }\n";
    }

    private String getOptionalLoopParser() {
        var resultExpr = getResultExpr();
        var rule_name = className.getRuleName().replace(":", "_");
        var maybeLevel = resultSource.getType() == SourceType.Class ? ", int lv" : "";
        return "\n    private static void " + rule_name + "_loop(ParseTree t" + maybeLevel + ") {\n" +
                "        t.enterLoop();\n" +
                "        while (true) {\n" +
                "            var p = t.position();\n" +
                "            if (!" + resultExpr + " || t.loopGuard(p)) break;\n" +
                "        }\n" +
                "        t.exitLoop();\n" +
                "    }\n";
    }
}