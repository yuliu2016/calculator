package org.fugalang.grammar.transform;

import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CTransform {

    public static String getFuncDeclarations(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : ruleSet.namedRules()) {
            addUnitRuleDeclaration(namedRule.root(), sb);
            for (UnitRule component : namedRule.components()) {
                addUnitRuleDeclaration(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleDeclaration(UnitRule unit, StringBuilder sb) {
        var rn = unit.ruleName();

        sb.append("static ").append(rn.returnTypeOr("void")).append(" *")
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
            sb.append(StringUtil.inlinedoc(namedRule.root().grammarString()));
            var args = namedRule.args();
            addUnitRuleBody(namedRule.root(), sb, args);
            for (UnitRule component : namedRule.components()) {
                addUnitRuleBody(component, sb, Collections.emptyMap());
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleBody(UnitRule unit, StringBuilder sb, Map<String, String> args) {
        var rn = unit.ruleName();

        boolean memoize = args.containsKey("memo") || unit.leftRecursive();

        sb.append("\nstatic ").append(rn.returnTypeOr("void")).append(" *")
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

        var resultName = "res_" + unit.ruleIndex();
        sb.append("    return exit_frame(p, &f, ").append(resultName).append(");\n");
        sb.append("}\n");

        for (UnitField field : unit.fields()) {
            getLoopParser(field, sb);
        }
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
//        sb.append("    if (!enter_frame(p, &f)) {\n        return exit_frame(p, &f, 0);\n    }\n");

        var rtype = unit.ruleName().returnTypeOr("void");
        var resultName = "res_" + unit.ruleIndex();
        var altName = "alt_" + unit.ruleIndex();

        sb.append("    ").append(rtype).append(" *").append(resultName).append(" = 0;\n");
        sb.append("    ").append(rtype).append(" *").append(altName).append(";\n");
        sb.append("    size_t maxpos;\n");
        sb.append("    ").append(rtype).append(" *max;\n");

        sb.append("    if (enter_frame(p, &f)) {\n");
        sb.append("        do {\n");
        sb.append("            maxpos = p->pos;\n");
        sb.append("            max = ").append(resultName).append(";\n");
        sb.append("            memoize(p, &f, max, maxpos);\n");
        sb.append("            p->pos = f.f_pos;\n");
        sb.append("            ").append(resultName).append(" = (\n");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("                (").append(altName).append(" = ");
            sb.append(getParserFieldExpr(fields.get(i)));

            if (i == fields.size() - 1) {
                sb.append(")");
            } else {
                sb.append(") ||\n");
            }
        }
        sb.append("\n            ) ? ").append(altName).append(" : 0;\n");
        sb.append("""
                        } while (p->pos > maxpos);
                        p->pos = maxpos;
                        r = max;
                    }
                """.replace("r", resultName));
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() ||
                (field.resultSource().isTokenLiteral() && field.isRequired()));
    }

    private static void addConjunctionBody(UnitRule unit, StringBuilder sb) {
        var rtype = unit.ruleName().returnTypeOr("void");

        List<String> fieldNames = new ArrayList<>();

        for (UnitField field : unit.fields()) {
            if (isImportantField(field)) {
                var type = getParserFieldType(field);
                var name = field.fieldName().snakeCaseUnconflicted();
                fieldNames.add(name);
                sb.append("    ").append(type).append(" *").append(name).append(";\n");
            }
        }

        var resultName = "res_" + unit.ruleIndex();
        sb.append("    ").append(rtype).append(" *").append(resultName).append(";\n");

        sb.append("    ").append(resultName).append(" = enter_frame(p, &f) && (\n");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        ");
            sb.append("(");
            var field = fields.get(i);
            if (isImportantField(field)) {
                var fieldName = field.fieldName().snakeCaseUnconflicted();
                sb.append(fieldName);
                sb.append(" = ");
            }
            sb.append(getParserFieldExpr(field));

            if (i == fields.size() - 1) {
                sb.append(")");
            } else {
                sb.append(") &&\n");
            }
        }

        sb.append("\n    ) ? ");
        if (unit.resultClause() == null) {
            sb.append("node(p, &f)");
        } else {
            var resultClause = unit.resultClause().template();
            for (int i = 0; i < fieldNames.size(); i++) {
                var name = fieldNames.get(i);
                var templateName = "%" + ((char) ('a' + i));
                resultClause = resultClause.replace(templateName, name);
            }
            sb.append(resultClause);
        }
        sb.append(" : 0;\n");
    }


    private static String getRawParserFieldType(UnitField field) {
        var kind = field.resultSource().kind();
        return switch (kind) {
            case TokenLiteral, TokenType -> "token_t";
            case UnitRule -> field.resultSource().asRuleName().returnTypeOr("void");
        };
    }

    private static String getParserFieldType(UnitField field) {
        String type;
        if (field.isSingular() || field.isPredicate()) {
            type = getRawParserFieldType(field);
        } else type = "ast_list_t";
        return type;
    }

    private static void addDisjunctionBody(UnitRule unit, StringBuilder sb) {

        for (UnitField field : unit.fields()) {
            var template = field.resultClause().template().strip();
            // Direct fallthrough
            if (template.equals("%a")) continue;
            // Still need to AND the result, but don't need separate variable
            if (!template.contains("%a")) continue;

            var type = getParserFieldType(field);
            var name = field.fieldName().snakeCaseUnconflicted();
            sb.append("    ").append(type).append(" *").append(name).append(";\n");
        }

        var resultType = unit.ruleName().returnTypeOr("void");
        var resultName = "res_" + unit.ruleIndex();
        var altName = "alt_" + unit.ruleIndex();

        sb.append("    ").append(resultType).append(" *").append(altName).append(";\n");
        sb.append("    ").append(resultType).append(" *").append(resultName).append(";\n");
        sb.append("    ").append(resultName).append(" = enter_frame(p, &f) && (");

        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            var field = fields.get(i);
            var fieldName = field.fieldName().snakeCaseUnconflicted();

            if (field.resultClause() == null)
                throw new IllegalStateException();

            var template = field.resultClause().template().strip();

            if (template.equals("%a")) {
                // Fall-through; no extra variables needed
                sb.append("\n        (").append(altName).append(" = ")
                        .append(getParserFieldExpr(field));
            } else {
                if (i != 0) sb.append(" ");
                sb.append("(\n            (");

                if (template.contains("%a")) {
                    // Only assign to variable if it's actually used
                    sb.append(fieldName).append(" = ");
                }

                sb.append(getParserFieldExpr(field)).append(") &&\n");

                sb.append("            ");
                sb.append("(").append(altName).append(" = ");

                sb.append(field.resultClause().template().replace("%a", fieldName));
                if (i == fields.size() - 1) {
                    sb.append(")");
                } else {
                    sb.append(")\n        ");
                }
            }

            if (i == fields.size() - 1) {
                sb.append(")");
            } else {
                sb.append(") ||");
            }
        }

        sb.append("\n    ) ? ").append(altName).append(" : 0;\n");
    }

    private static String getParserFieldExpr(UnitField field) {
        return switch (field.fieldType()) {
            case RequireTrue -> getTestExpr(field);
            case RequireFalse -> "!" + getTestExpr(field);
            case Required -> getResultExpr(field);
            case Optional -> getResultExpr(field) + ", 1";
            case RequiredList, OptionalList -> getNewLoopExpr(field);
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
        var rawType = getRawParserFieldType(field);

        TokenEntry delimiter = field.delimiter();
        if (delimiter == null) {
            return "\nstatic ast_list_t *" + ruleName + "_loop(parser_t *p) {\n" +
                    "    " + rawType + " *" + fname + " = " + resultExpr + ";\n" +
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
                    "    " + rawType + " *" + fname + " = " + resultExpr + ";\n" +
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
        var rawType = getRawParserFieldType(field);

        return "\nstatic ast_list_t *" + rule_name + "_loop(parser_t *p) {\n" +
                "    ast_list_t *" + listName + " = ast_list_new(p);\n" +
                "    " + rawType + " *" + fname + ";\n" +
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


    private static String getTestExpr(UnitField field) {
        return "test_and_reset(p, &f, " + getResultExpr(field) + ")";
    }

    private static String getResultExpr(UnitField field) {
        var rs = field.resultSource();
        String innerName;
        if (rs.isUnitRule()) {
            innerName = rs.asRuleName().symbolicName() + "(p)";
        } else if (rs.isTokenType() || rs.isTokenLiteral()) {
            var te = rs.asTokenEntry();
            innerName = "consume(p, " + te.index() + ", \"" + te.literalValue() + "\")";
        } else throw new IllegalArgumentException();
        return innerName;
    }

    public static String getTokenMap(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();
        for (var tk : ruleSet.tokenMap().values()) {
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
