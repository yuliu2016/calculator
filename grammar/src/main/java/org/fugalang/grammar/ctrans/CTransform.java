package org.fugalang.grammar.ctrans;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.util.StringJoiner;

public class CTransform {
    public static String getParserFileContent(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        addFuncDeclarations(ruleSet, sb);
        sb.append("\n\n");
        addFunctionBodies(ruleSet, sb);
        return sb.toString();
    }

    private static void addFuncDeclarations(RuleSet ruleSet, StringBuilder sb) {
        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            addUnitRuleDeclaration(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleDeclaration(component, sb);
            }
        }
    }

    private static void addUnitRuleDeclaration(UnitRule unit, StringBuilder sb) {
        var rn = unit.getRuleName();
        sb.append("RULE(")
                .append(rn.getRuleNameSymbolic());
//        if (!rn.hasSuffix()) {
//            sb.append("_rule");
//        }
        sb.append(");\n");
    }

    private static void addFunctionBodies(RuleSet ruleSet, StringBuilder sb) {
        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            addUnitRuleBody(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleBody(component, sb);
            }
        }
    }

    private static void addUnitRuleBody(UnitRule unit, StringBuilder sb) {
        var rn = unit.getRuleName();
        sb.append(StringUtil.inlinedoc(unit.getGrammarString()));
        sb.append("\nRULE(")
                .append(rn.getRuleNameSymbolic());
//        if (!rn.hasSuffix()) {
//            sb.append("_rule");
//        }
        sb.append(") {\n");
        sb.append("    ENTER_FRAME(p, ")
                .append(unit.getRuleIndex())
                .append(", \"")
                .append(rn.getRuleNameFull())
                .append("\");\n");
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
        sb.append("}\n\n");
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
        sb.append("    RETURN_IF_MEMOIZED(p);\n");
        sb.append("    ENTER_LEFT_RECURSION(p);\n");

        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    (res = ");
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

        StringJoiner sj = new StringJoiner(", ");
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field)) {
                importantCount++;
                sj.add("*" + field.getProperFieldName() + "_");
            }
        }

        if (importantCount > 0) {
            sb.append("    FAstNode ");
            sb.append(sj.toString());
            sb.append(";\n");
        }

        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    ");
            sb.append("(");
            var field = fields.get(i);
            if (isImportantField(field)) {
                sb.append(field.getProperFieldName());
                sb.append("_ = ");
            }
            var result = getParserFieldExpr(field,
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }

        if (importantCount == 0) {
            sb.append(";\n");
            return;
        }

        sb.append("\n    ? (res = AST_NODE_");
        sb.append(importantCount);
        sb.append("(p, ").append(unit.getRuleIndex());
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field)) {
                sb.append(", ");
                sb.append(field.getProperFieldName()).append("_");
            }
        }
        sb.append(")) : 0;\n");
    }

    private static void addDisjunctionBody(UnitRule unit, StringBuilder sb) {
        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    ");
            sb.append("(res = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append(";\n");
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
                return getRequiredExprPart(getLoopExpr(field), ruleType, isLast);
            case OptionalList:
                return getOptionalExprPart(getLoopExpr(field), ruleType, isLast);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String getLoopExpr(UnitField field) {
        var rule_name = field.getRuleName().getRuleNameSymbolic();
        return rule_name + "_loop(p)";
    }

    private static String getOptionalExprPart(String resultExpr, RuleType ruleType, boolean isLast) {
        return getRequiredExprPart("OPTIONAL(" + resultExpr + ")", ruleType, isLast);
    }

    private static String getRequiredExprPart(String resultExpr, RuleType ruleType, boolean isLast) {
        // N.W. there is no starting bracket because stuff can go before resultExpr
        return resultExpr + (isLast ? ")" :
                ruleType ==RuleType.Conjunction ? ") &&\n" : ") ||\n");
    }

    private static String getTestExpr(UnitField field) {
        return "TEST(p, " + getResultExpr(field) + ")";
    }

    private static String getResultExpr(UnitField field) {
        var rs = field.getResultSource();
        switch (rs.getKind()) {
            case UnitRule:
                return ((RuleName)rs.getValue()).getRuleNameSymbolic() + "(p)";
            case TokenType:
            case TokenLiteral:
                var te = (TokenEntry) rs.getValue();
                return  "AST_CONSUME(p, " + te.getIndex() + ", \"" + te.getLiteralValue() + "\")";
            default:
                throw new IllegalArgumentException();
        }
    }
}
