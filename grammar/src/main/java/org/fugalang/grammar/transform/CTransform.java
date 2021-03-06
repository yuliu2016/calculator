package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.util.Collections;
import java.util.Map;

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
    }

    public static String getFunctionBodies(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            sb.append("\n");
            sb.append(StringUtil.inlinedoc(namedRule.getRoot().getGrammarString()));
            var args = namedRule.getArgs();
            addUnitRuleBody(namedRule.getRoot(), sb, args);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleBody(component, sb, Collections.emptyMap());
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleBody(UnitRule unit, StringBuilder sb, Map<String, String> args) {
        var rn = unit.getRuleName();
        sb.append("\nRULE(")
                .append(rn.getRuleNameSymbolic());
        sb.append(") {\n");
        sb.append("    ENTER(")
                .append(unit.getRuleIndex())
                .append(");\n");
        if (args.containsKey("memo") && !unit.isLeftRecursive()) {
            sb.append("RETURN_IF_MEMOIZED();\n");
        }
        var ws = args.get("allow_whitespace");
        if ("true".equals(ws)) {
            sb.append("    WS_PUSH_1();\n");
        } else if ("false".equals(ws)) {
            sb.append("    WS_PUSH_0();\n");
        }
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
        if (ws != null) {
            sb.append("    WS_POP();\n");
        }
        if (args.containsKey("memo") && !unit.isLeftRecursive()) {
            sb.append("MEMOIZE();\n");
        }
        sb.append("    EXIT();\n");
        sb.append("}\n");
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
        sb.append("    RETURN_IF_MEMOIZED();\n");
        sb.append("    ENTER_LEFT_RECURSION();\n");

        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("    (a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append(";\n");

        sb.append("    EXIT_LEFT_RECURSION();\n");
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
        sb.append("()) : 0; \n");
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
        sb.append("\n    ? (r = NODE_1()) : 0;\n");
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
        } else if (rs.getKind() == SourceKind.TokenType || rs.getKind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.getValue();
            var end = te.getIndex() + ", \"" + te.getLiteralValue() + "\")";
            if (field.getFieldType() == FieldType.RequiredList) {
                if (field.getDelimiter() == null) {
                    return "TOKEN_SEQUENCE(p, " + end;
                } else {
                    var delim = field.getDelimiter();
                    return "TOKEN_DELIMITED(p, " + +delim.getIndex() +
                            ", \"" + delim.getLiteralValue() +
                            "\", " + end;
                }
            } else if (field.getFieldType() == FieldType.OptionalList) {
                return "TOKEN_SEQ_OR_NONE(p, " + end;
            }
            throw new IllegalArgumentException("optional list with token not supported");
        }
        throw new IllegalArgumentException();
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
        return "TEST(" + getResultExpr(field) + ")";
    }

    private static String getResultExpr(UnitField field) {
        var rs = field.getResultSource();
        if (rs.getKind() == SourceKind.UnitRule) {
            return ((RuleName) rs.getValue()).getRuleNameSymbolic() + "(p)";
        } else if (rs.getKind() == SourceKind.TokenType || rs.getKind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.getValue();
            return "TOKEN(" + te.getIndex() + ", \"" + te.getLiteralValue() + "\")";
        }
        throw new IllegalArgumentException();
    }

    public static String getASTGen(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();

        sb.append("#define FVAR(name, node, i) FAstNode *name = (node)->ast_v.fields[i]\n");
        sb.append("#define TVAR(name, node, i) FToken *name = (node)->ast_v.fields[i]->ast_v.token\n");

        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            addStructFields(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addStructFields(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addStructFields(UnitRule unit, StringBuilder sb) {
        var upName = unit.getRuleName().getRuleNameSymbolic().toUpperCase();

        sb.append("\n#define R_").append(upName)
                .append(" ").append(unit.getRuleIndex()).append("\n");

        if (unit.isLeftRecursive() || unit.getRuleType() == RuleType.Disjunction) {
            return;
        }

        if (unit.getFields().stream().noneMatch(CTransform::isImportantField)) {
            return;
        }

        sb.append("#define UNPACK_").append(upName).append("(n) \\\n");
        int i = 0;
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field)) {
                if (field.getResultSource().getKind() == SourceKind.UnitRule) {
                    sb.append("    FVAR(");
                } else {
                    sb.append("    TVAR(");
                }

                sb.append(field.getProperFieldName()).append(", n, ").append(i).append(")");
                sb.append("; \\\n");
                i++;
            }
        }
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

    public static String getDummyCompiler(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();

        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            addDummyDeclaration(sb, namedRule.getRoot());
            for (UnitRule component : namedRule.getComponents()) {
                addDummyDeclaration(sb, component);
            }
        }

        for (NamedRule namedRule : ruleSet.getNamedRules()) {
            sb.append("\n");
            sb.append(StringUtil.inlinedoc(namedRule.getRoot().getGrammarString()));
            addDummyFunction(sb, namedRule.getRoot());
            for (UnitRule component : namedRule.getComponents()) {
                addDummyFunction(sb, component);
            }
        }

        return sb.toString();
    }

    private static void addDummyDeclaration(StringBuilder sb, UnitRule unit) {
        sb.append("void dummy_").append(unit.getRuleName().getRuleNameSymbolic())
                .append("(FAstNode *n);\n");
    }

    private static void addDummyFunction(StringBuilder sb, UnitRule unit) {

        sb.append("\nvoid dummy_").append(unit.getRuleName().getRuleNameSymbolic())
                .append("(FAstNode *n) {\n");
        if (unit.isLeftRecursive() || unit.getRuleType() == RuleType.Disjunction) {
            addDummyDisjunct(sb, unit);
        } else {
            addDummyUnpack(sb, unit);
        }
        sb.append("}\n");
    }

    private static void addDummyDisjunct(StringBuilder sb, UnitRule unit) {
        sb.append("    FAstNode *o = n->ast_v.fields[0];\n");

        for (UnitField field : unit.getFields()) {
            if (field.getResultSource().getKind() == SourceKind.UnitRule) {
                var rn = (RuleName) field.getResultSource().getValue();
                sb.append("    if (R_CHECK(n, R_").append(rn.getRuleNameSymbolic().toUpperCase())
                        .append(")) {\n")
                        .append("        dummy_").append(rn.getRuleNameSymbolic())
                        .append("(o);\n        return;\n")
                        .append("    }\n");
            } else {
                var te = (TokenEntry) field.getResultSource().getValue();
                sb.append("    if (T_CHECK(n, ")
                        .append("T_").append(te.getNameSnakeCase().toUpperCase())
                        .append(")) {\n        return;\n    }\n");
            }
        }
    }

    private static void addDummyUnpack(StringBuilder sb, UnitRule unit) {
        if (unit.getFields().stream().noneMatch(CTransform::isImportantField)) {
            return;
        }
        sb.append("    UNPACK_").append(unit.getRuleName().getRuleNameSymbolic().toUpperCase())
                .append("(n)\n");
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field) &&
                    field.getResultSource().getKind() == SourceKind.UnitRule) {
                var rn = (RuleName) field.getResultSource().getValue();
                sb.append("    dummy_").append(rn.getRuleNameSymbolic())
                        .append("(").append(field.getProperFieldName()).append(");\n");
            }
        }
    }
}
