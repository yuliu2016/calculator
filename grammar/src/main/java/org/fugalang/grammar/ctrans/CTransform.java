package org.fugalang.grammar.ctrans;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

public class CTransform {

    public static String getFuncDeclarations(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            addUnitRuleDeclaration(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleDeclaration(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleDeclaration(UnitRule unit, StringBuilder sb) {
        var rn = unit.getRuleName();
        sb.append("RULE(")
                .append(rn.getRuleNameSymbolic());
        sb.append(");\n");
        for (UnitField field : unit.getFields()) {
            if (field.isSingular() || field.isPredicate()) continue; // not a loop
            if (field.getResultSource().getKind() == SourceKind.UnitRule) continue; // covered by macro
            sb.append("RULE(");
            sb.append(field.getRuleName().getRuleNameSymbolic());
            sb.append("_loop);\n");
        }
    }

    public static String getFunctionBodies(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            sb.append("\n");
            sb.append(StringUtil.inlinedoc(namedRule.getRoot().getGrammarString()));
            addUnitRuleBody(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleBody(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleBody(UnitRule unit, StringBuilder sb) {
        var rn = unit.getRuleName();
        sb.append("\nRULE(")
                .append(rn.getRuleNameSymbolic());
        sb.append(") {\n");
        sb.append("    ENTER_FRAME(p, ")
                .append(unit.getRuleIndex())
                .append(");\n");
        if (unit.isLeftRecursive()) {
            addLeftRecursiveUnitRuleBody(unit, sb);
        } else {
            switch (unit.getRuleType()) {
                case Disjunction:
                    addDisjunctionBody(unit, sb);
                    break;
                case Conjunction:
                    addConjunctionBody(unit, sb);
                    break;
            }
        }
        sb.append("    EXIT_FRAME(p);\n");
        sb.append("}\n");

        for (UnitField field : unit.getFields()) {
            if (field.isSingular() || field.isPredicate()) continue; // not a loop
            if (field.getResultSource().getKind() == SourceKind.UnitRule) continue; // covered by macro
            sb.append("\nRULE(");
            sb.append(field.getRuleName().getRuleNameSymbolic());
            sb.append("_loop) {\n");
            addLoopFuncBody(field, sb);
            sb.append("}\n");
        }
    }

    private static void addLoopFuncBody(UnitField field, StringBuilder sb) {
        if (field.getFieldType() == FieldType.RequiredList) {
            addRequiredLoopParser(field, sb);
        } else {
            addOptionalLoopParser(field, sb);
        }
    }

    private static void addRequiredLoopParser(UnitField field, StringBuilder sb) {
        var resultExpr = getResultExpr(field);

        TokenEntry delimiter = field.getDelimiter();

        String whileCondition;
        if (delimiter == null) {
            whileCondition = "(node = " + resultExpr + ")";
        } else {
            whileCondition = "pos = p->pos,\n" +
                    "            (TOKEN(p, " + delimiter.getIndex() + ", \""
                    + delimiter.getLiteralValue() + "\")) &&\n" +
                    "            (node = " + resultExpr + ")";
        }

        var body = "    FAstNode *node, *seq;\n" +
                "    if (!(node = " + resultExpr + ")) { return 0; }\n" +
                "    seq = AST_SEQ_NEW(p);\n" +
                (delimiter == null ? "" : "    size_t pos;\n") +
                "    do { AST_SEQ_APPEND(p, seq, node); }\n" +
                "    while (" + whileCondition + ");\n" +
                (delimiter == null ? "" : "    p->pos = pos;\n") +
                "    return seq;\n";
        sb.append(body);
    }

    private static void addOptionalLoopParser(UnitField field, StringBuilder sb) {
        var resultExpr = getResultExpr(field);
        var body = "    FAstNode *node, *seq = AST_SEQ_NEW(p);\n" +
                "    while ((node = " + resultExpr + ")) {\n" +
                "        AST_SEQ_APPEND(p, seq, node);\n" +
                "    }\n" +
                "    return seq;\n";

        sb.append(body);
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
        sb.append("    RETURN_IF_MEMOIZED(p);\n");
        sb.append("    ENTER_LEFT_RECURSION(p);\n");

        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    (a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append(";\n");

        sb.append("    EXIT_LEFT_RECURSION(p);\n");
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() || (field.getResultSource().getKind()
                == SourceKind.TokenLiteral && field.isRequired()));
    }

    private static void addConjunctionBody(UnitRule unit, StringBuilder sb) {

        int importantCount = 0;

        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    ");
            sb.append("(");
            var field = fields.get(i);
            if (isImportantField(field)) {
                var fieldName = ((char) ('a' + importantCount)) + "";
                importantCount++;
                sb.append(fieldName);
                sb.append(" = ");
            }
            var result = getParserFieldExpr(field,
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }

        if (importantCount == 0) {
            sb.append(";\n");
            return;
        }

        sb.append("\n    ? (r = NODE_");
        sb.append(importantCount);
        sb.append("(p");

        importantCount = 0;
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field)) {
                var fieldName = ((char) ('a' + importantCount)) + "";
                importantCount++;
                sb.append(", ");
                sb.append(fieldName);
            }
        }
        sb.append(")) : 0;\n");
    }

    private static void addDisjunctionBody(UnitRule unit, StringBuilder sb) {
        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    ");
            sb.append("(a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append("\n    ? (r = NODE_1(p, a)) : 0;\n");
    }

    private static String getParserFieldExpr(
            UnitField field, RuleType ruleType, boolean isLast) {
        switch (field.getFieldType()) {
            case RequireTrue:
                return getRequiredExprPart(getTestExpr(field), ruleType, isLast);
            case RequireFalse:
                var resultExpr = "!" + getTestExpr(field);
                return getRequiredExprPart(resultExpr, ruleType, isLast);
            case Required:
                return getRequiredExprPart(getResultExpr(field), ruleType, isLast);
            case Optional:
                return getOptionalExprPart(getResultExpr(field), ruleType, isLast);
            case RequiredList:
            case OptionalList:
                return getRequiredExprPart(getLoopExpr(field), ruleType, isLast);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String getLoopExpr(UnitField field) {
        var rs = field.getResultSource();
        if (rs.getKind() == SourceKind.UnitRule) {
            // shortcut
            var func = ((RuleName) rs.getValue()).getRuleNameSymbolic();
            if (field.getFieldType() == FieldType.RequiredList) {
                if (field.getDelimiter() == null) {
                    return "SEQUENCE(p, " + func + ")";
                } else {
                    return "DELIMITED(p, " + field.getDelimiter().getIndex() +
                            ", \"" + field.getDelimiter().getLiteralValue() +
                            "\", " + func + ")";
                }
            } else if (field.getFieldType() == FieldType.OptionalList) {
                return "SEQ_OR_NONE(p, " + func + ")";
            }
            throw new IllegalStateException();
        }
        var rule_name = field.getRuleName().getRuleNameSymbolic();
        return rule_name + "_loop(p)";
    }

    private static String getOptionalExprPart(String resultExpr, RuleType ruleType, boolean isLast) {
        return getRequiredExprPart("OPTIONAL(" + resultExpr + ")", ruleType, isLast);
    }

    private static String getRequiredExprPart(String resultExpr, RuleType ruleType, boolean isLast) {
        // N.W. there is no starting bracket because stuff can go before resultExpr
        return resultExpr + (isLast ? ")" :
                ruleType == RuleType.Conjunction ? ") &&\n" : ") ||\n");
    }

    private static String getTestExpr(UnitField field) {
        return "TEST(p, " + getResultExpr(field) + ")";
    }

    private static String getResultExpr(UnitField field) {
        var rs = field.getResultSource();
        switch (rs.getKind()) {
            case UnitRule:
                return ((RuleName) rs.getValue()).getRuleNameSymbolic() + "(p)";
            case TokenType:
            case TokenLiteral:
                var te = (TokenEntry) rs.getValue();
                return "TOKEN(p, " + te.getIndex() + ", \"" + te.getLiteralValue() + "\")";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String getASTGen(RuleSet ruleSet, String name, String lowname, String macro) {
        StringBuilder sb = new StringBuilder();

        sb.append("typedef union ").append(lowname).append(" ").append(name).append(";\n\n");

        sb.append("#define ").append(macro).append("(node) ((").append(name).append(" *) &(node)->ast_v)\n\n");

        sb.append("union ").append(lowname).append(" {\n");
        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            addStructFields(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addStructFields(component, sb);
            }
        }
        sb.append("} ").append(";\n");
        return sb.toString();
    }

    private static void addStructFields(UnitRule unit, StringBuilder sb) {
        sb.append("\n#define R_").append(unit.getRuleName().getRuleNameSymbolic().toUpperCase())
                .append(" ").append(unit.getRuleIndex()).append("\n");

        if (unit.isLeftRecursive() || unit.getRuleType() == RuleType.Disjunction) {
            return;
        }

        if (unit.getFields().stream().noneMatch(CTransform::isImportantField)) {
            return;
        }

        sb.append("    struct {\n");
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field)) {
                sb.append("        FAstNode *").append(field.getProperFieldName()).append(";\n");
            }
        }
        sb.append("    } ").append(unit.getRuleName().getRuleNameSymbolic()).append(";\n");
    }

    public static String getTokenMap(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (var tk : ruleSet.getTokenMap().values()) {
            sb.append("#define T_")
                    .append(tk.getNameSnakeCase().toUpperCase())
                    .append(" ")
                    .append(tk.getIndex())
                    .append("  // ")
                    .append(tk.getLiteralValue())
                    .append("\n");
        }
        return sb.toString();
    }
}
