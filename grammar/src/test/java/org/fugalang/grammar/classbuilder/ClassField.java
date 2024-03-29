package org.fugalang.grammar.classbuilder;


import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.FieldType;
import org.fugalang.grammar.util.StringUtil;

import static org.fugalang.grammar.common.FieldType.*;

@Deprecated
public class ClassField {
    private final ClassName className;
    private final String fieldName;
    private final FieldType fieldType;
    private final ResultSource resultSource;
    private final String delimiter;

    public ClassField(
            ClassName className,
            String fieldName,
            FieldType fieldType,
            ResultSource resultSource,
            String delimiter
    ) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.resultSource = resultSource;
        this.delimiter = delimiter;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isRequired() {
        return fieldType == Required || fieldType == RequiredList;
    }

    public boolean isSingular() {
        return fieldType == Required || fieldType == Optional;
    }

    public boolean isPredicate() {
        return fieldType == RequireTrue || fieldType == RequireFalse;
    }

    /**
     * Used for field name conflict resolution
     */
    public ClassField withFieldName(String newFieldName) {
        return new ClassField(className, newFieldName, fieldType, resultSource, delimiter);
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
        if (isPredicate()) {
            return null;
        }
        switch (resultSource.getType()) {
            case Class:
                if (isSingular()) {
                    return "        return new " + className.getType() + "(get(" + index + "));\n";
                }
                return "        return getList(" + index + ", " + className.getRealClassName() + "::new);\n";
            case TokenType:
                if (isSingular()) {
                    return "        return get(" + index + ", " + resultSource.getValue() + ");\n";
                }
                return "        return getList(" + index + ", ParseTreeNode::asString);\n";
            case TokenLiteral:
                if (ruleType == RuleType.Conjunction && fieldType == Required) {
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
                StringUtil.capitalizeFirstChar(fieldName) +
                "() {\n" + body + "    }\n";
    }

    private String absentCheckBody(RuleType ruleType, int index) {
        switch (resultSource.getType()) {
            case Class:
            case TokenType:
                if (ruleType == RuleType.Conjunction && fieldType == Required) {
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
        switch (fieldType) {
            case RequireTrue:
                return getRequiredStmt(getResultExpr(true), ruleType, isFirst);
            case RequireFalse:
                var resultExpr = "!" + getResultExpr(true);
                return getRequiredStmt(resultExpr, ruleType, isFirst);
            case Required:
                return getRequiredStmt(getResultExpr(false), ruleType, isFirst);
            case Optional:
                return getOptionalStmt(getResultExpr(false), ruleType, isFirst);
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
        return rule_name + "_loop(t)";
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

    private String getResultExpr(boolean isPredicate) {
        var parseTreeInst = isPredicate ? "t.test()" : "t";
        switch (resultSource.getType()) {
            case Class:
                return resultSource.getValue().replace(":", "_") +
                        "(" + parseTreeInst + ")";
            case TokenType:
                return parseTreeInst + ".consume(" + resultSource.getValue() + ")";
            case TokenLiteral:
                return parseTreeInst + ".consume(\"" + resultSource.getValue() + "\")";
            default:
                throw new IllegalArgumentException();
        }
    }

    public String getLoopParser() {
        if (isSingular() || isPredicate()) {
            return null;
        }
        return fieldType == RequiredList ?
                getRequiredLoopParser() : getOptionalLoopParser();
    }

    private String getRequiredLoopParser() {
        var resultExpr = getResultExpr(false);
        var rule_name = className.getRuleName().replace(":", "_");

        String whileBody;
        if (delimiter == null) {
            whileBody = "            if (!" + resultExpr + ") break;\n";
        } else {
            whileBody = "            var p = t.position();\n" +
                    "            if (t.skip(\"" + delimiter + "\") && "
                    + resultExpr + ") continue;\n" +
                    "            t.reset(p);\n" +
                    "            break;\n";
        }

        return "\n    private static boolean " + rule_name + "_loop(ParseTree t) {\n" +
                "        t.enterLoop();\n" +
                "        var r = " + resultExpr + ";\n" +
                "        if (r) while (true) {\n" +
                whileBody +
                "        }\n" +
                "        t.exitLoop();\n" +
                "        return r;\n" +
                "    }\n";
    }

    private String getOptionalLoopParser() {
        var resultExpr = getResultExpr(false);
        var rule_name = className.getRuleName().replace(":", "_");
        return "\n    private static void " + rule_name + "_loop(ParseTree t) {\n" +
                "        t.enterLoop();\n" +
                "        while (true) {\n" +
                "            if (!" + resultExpr + ") break;\n" +
                "        }\n" +
                "        t.exitLoop();\n" +
                "    }\n";
    }
}
