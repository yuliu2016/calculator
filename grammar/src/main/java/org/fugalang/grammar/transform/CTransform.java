package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.util.Collections;
import java.util.Map;

public class CTransform {

    public static String getFuncDeclarations(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : ruleSet.namedRules()) {
            addUnitRuleDeclaration(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleDeclaration(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleDeclaration(UnitRule unit, StringBuilder sb) {
        var rn = unit.ruleName();
        sb.append("static void *")
                .append(rn.symbolicName());
        sb.append("(parser_t *);\n");
        for (UnitField field : unit.fields()) {
            if (field.isSingular() || field.isPredicate()) {
                continue;
            }
            sb.append("static ast_list_t *");
            sb.append(field.ruleName().symbolicName());
            if (field.delimiter() == null) {
                sb.append("_loop");
            } else {
                sb.append("_delimited");
            }
            sb.append("(parser_t *);\n");
        }
    }

    public static String getFunctionBodies(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : ruleSet.namedRules()) {
            sb.append("\n");
            sb.append(StringUtil.inlinedoc(namedRule.getRoot().grammarString()));
            var args = namedRule.getArgs();
            addUnitRuleBody(namedRule.getRoot(), sb, args);
            for (UnitRule component : namedRule.getComponents()) {
                addUnitRuleBody(component, sb, Collections.emptyMap());
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleBody(UnitRule unit, StringBuilder sb, Map<String, String> args) {
        var rn = unit.ruleName();

        boolean memoize = args.containsKey("memo") || unit.leftRecursive();

        sb.append("\nstatic void *")
                .append(rn.symbolicName());
        sb.append("(parser_t *p) {\n");
        sb.append("    frame_t f = {")
                .append(unit.ruleIndex())
                .append(", p->pos, FUNC, 0, ")
                .append(memoize ? 1 : 0)
                .append("};\n");

        var ws = args.get("allow_whitespace");
        if ("true".equals(ws)) {
            sb.append("    int ws = p->ignore_whitespace;\n");
            sb.append("    p->ignore_whitespace = 1;\n");
        } else if ("false".equals(ws)) {
            sb.append("    int ws = p->ignore_whitespace;\n");
            sb.append("    p->ignore_whitespace=0;\n");
        }

        if (unit.leftRecursive()) {
            addLeftRecursiveUnitRuleBody(unit, sb);
        } else {
            switch (unit.ruleType()) {
                case Disjunction -> addDisjunctionBody(unit, sb);
                case Conjunction -> addConjunctionBody(unit, sb);
            }
        }

        if (ws != null) {
            sb.append("    p->ignore_whitespace = ws;\n");
        }

        sb.append("    return exit_frame(p, &f, r);\n");
        sb.append("}\n");

        for (UnitField field : unit.fields()) {
            getLoopParser(field, sb);
        }
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
        sb.append("    if (!enter_frame(p, &f)) {\n        return exit_frame(p, &f, 0);\n    }\n");

        sb.append("    void *a = 0;\n");
        sb.append("    void *r = 0;\n");
        sb.append("    void *max = 0;\n");
        sb.append("    size_t maxpos;\n");


        sb.append("    do {\n");
        sb.append("        memoize(p, &f, max = a, maxpos = p->pos);\n");
        sb.append("        p->pos = f.f_pos;\n");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        (a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.ruleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append(";\n");
        sb.append("""
                    } while (p->pos > maxpos);
                    p->pos = maxpos;
                    r = max ? node_1(p, &f, max) : 0;
                """);
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() || (field.resultSource().kind()
                == SourceKind.TokenLiteral && field.isRequired()));
    }

    private static void addConjunctionBody(UnitRule unit, StringBuilder sb) {

        int importantCount = 0;

        int j = 0;
        for (UnitField field : unit.fields()) {
            if (isImportantField(field)) {
                addFieldVarDeclaration(field, sb, j);
                ++j;
            }
        }
        sb.append("    void *r;\n");

        sb.append("    r = enter_frame(p, &f) && (\n");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        ");
            sb.append("(");
            var field = fields.get(i);
            if (isImportantField(field)) {
                var fieldName = ((char) ('a' + importantCount)) + "";
                importantCount++;
                sb.append(fieldName);
                sb.append(" = ");
            }
            var result = getParserFieldExpr(field,
                    unit.ruleType(), i == fields.size() - 1);
            sb.append(result);
        }

        sb.append("\n    ) ? ");
        switch (importantCount) {
            case 0 -> sb.append("node_0(p, &f)");
            case 1 -> sb.append("node_1(p, &f, a)");
            case 2 -> sb.append("node_2(p, &f, a, b)");
            case 3 -> sb.append("node_3(p, &f, a, b, c)");
            case 4 -> sb.append("node_4(p, &f, a, b, c, d)");
        }
        sb.append(" : 0;\n");
    }

    private static void addFieldVarDeclaration(UnitField field, StringBuilder sb, int i) {
        if (isImportantField(field)) {
            String type;
            if (field.isSingular() || field.isPredicate()) {
                var kind = field.resultSource().kind();
                type = switch (kind) {
                    case TokenLiteral, TokenType -> "token_t";
                    default -> "void";
                };
            } else type = "ast_list_t";
            char letter = (char) ('a' + i);
            sb.append("    ").append(type).append(" *").append(letter).append(";\n");
        }
    }

    private static void addDisjunctionBody(UnitRule unit, StringBuilder sb) {
        sb.append("    void *a;\n");
        sb.append("    void *r;\n");
        sb.append("    r = enter_frame(p, &f) && (\n");
        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        ");
            sb.append("(a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.ruleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append("\n    ) ? a : 0;\n");
    }

    private static String getParserFieldExpr(
            UnitField field, RuleType ruleType, boolean isLast) {
        return switch (field.fieldType()) {
            case RequireTrue -> getRequiredExprPart(getTestExpr(field), ruleType, isLast);
            case RequireFalse -> getRequiredExprPart("!" + getTestExpr(field), ruleType, isLast);
            case Required -> getRequiredExprPart(getResultExpr(field), ruleType, isLast);
            case Optional -> getOptionalExprPart(getResultExpr(field), ruleType, isLast);
            case RequiredList, OptionalList -> getRequiredExprPart(getNewLoopExpr(field), ruleType, isLast);
        };
    }

    private static void getLoopParser(UnitField field, StringBuilder sb) {
        if (field.isSingular() || field.isPredicate()) {
            return;
        }
        String s = field.fieldType() == FieldType.RequiredList ?
                getRequiredLoopParser(field) : getOptionalLoopParser(field);
        sb.append(s);
    }

    private static String getRequiredLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field);
        var ruleName = field.ruleName().symbolicName();

        var listName = "list";
        var fname = "_" + field.fieldName().snakeCase();

        TokenEntry delimiter = field.delimiter();
        if (delimiter == null) {
            return "\nstatic ast_list_t *" + ruleName + "_loop(parser_t *p) {\n" +
                    "    void *" + fname + " = " + resultExpr + ";\n" +
                    "    if (!" + fname + ") {\n" +
                    "        return 0;\n" +
                    "    }\n" +
                    "    ast_list_t *" + listName + " = ast_list_new(p);\n" +
                    "    do {\n" +
                    "        ast_list_append(p, " + listName + ", " + fname + ");\n" +
                    "    } while ((" + fname + " = " + resultExpr + "));\n" +
                    "    return " + listName + ";\n" +
                    "}\n";
        } else {
            var delimExpr = "consume(p, " + delimiter.index() + ", \"" + delimiter.literalValue() + "\")";
            return "\nstatic ast_list_t *" + ruleName + "_delimited(parser_t *p) {\n" +
                    "    void *" + fname + " = " + resultExpr + ";\n" +
                    "    if (!" + fname + ") {\n" +
                    "        return 0;\n" +
                    "    }\n" +
                    "    ast_list_t *" + listName + " = ast_list_new(p);\n" +
                    "    size_t pos;\n" +
                    "    do {\n" +
                    "        ast_list_append(p, " + listName + ", " + fname + ");\n" +
                    "        pos = p->pos;\n" +
                    "    } while (" + delimExpr + " &&\n" +
                    "            (" + fname + " = " + resultExpr + "));\n" +
                    "    p->pos = pos;\n" +
                    "    return " + listName + ";\n" +
                    "}\n";
        }
    }

    private static String getOptionalLoopParser(UnitField field) {
        var resultExpr = getResultExpr(field);
        var rule_name = field.ruleName().symbolicName();

        var listName = "list";
        var fname = "_" + field.fieldName().snakeCase();

        return "\nstatic ast_list_t *" + rule_name + "_loop(parser_t *p) {\n" +
                "    ast_list_t *" + listName + " = ast_list_new(p);\n" +
                "    void *" + fname + ";\n" +
                "    while ((" + fname + " = " + resultExpr + ")) {\n" +
                "        ast_list_append(p, " + listName + ", " + fname + ");\n" +
                "    }\n" +
                "    return " + listName + ";\n" +
                "}\n";
    }

    private static String getNewLoopExpr(UnitField field) {
        var name = field.ruleName().symbolicName();
        if (field.delimiter() == null) {
            return name + "_loop(p)";
        } else {
            return name + "_delimited(p)";
        }
    }

    private static String getOptionalExprPart(String resultExpr, RuleType ruleType, boolean isLast) {
        return getRequiredExprPart(resultExpr + ", 1", ruleType, isLast);
    }

    private static String getRequiredExprPart(String resultExpr, RuleType ruleType, boolean isLast) {
        // N.W. there is no starting bracket because stuff can go before resultExpr
        return resultExpr + (isLast ? ")" :
                ruleType == RuleType.Conjunction ? ") &&\n" : ") ||\n");
    }

    private static String getTestExpr(UnitField field) {
        return "test_and_reset(p, &f, " + getResultExpr(field) + ")";
    }

    private static String getResultExpr(UnitField field) {
        var rs = field.resultSource();
        if (rs.kind() == SourceKind.UnitRule) {
            return ((RuleName) rs.value()).symbolicName() + "(p)";
        } else if (rs.kind() == SourceKind.TokenType || rs.kind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.value();
            return "consume(p, " + te.index() + ", \"" + te.literalValue() + "\")";
        }
        throw new IllegalArgumentException();
    }

    public static String getTokenMap(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (var tk : ruleSet.getTokenMap().values()) {
            sb.append("#define T_")
                    .append(tk.snakeCase().toUpperCase())
                    .append(" ")
                    .append(tk.index())
                    .append("  // ")
                    .append(tk.literalValue())
                    .append("\n");
        }
        return sb.toString();
    }
}
