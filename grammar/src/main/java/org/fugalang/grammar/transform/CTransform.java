package org.fugalang.grammar.transform;

import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class CTransform {

    public static String getFuncDeclarations(GrammarSpec spec) {
        StringBuilder sb = new StringBuilder();
        for (NamedRule namedRule : spec.namedRules()) {
            addUnitRuleDeclaration(namedRule.root(), sb);
            for (UnitRule component : namedRule.components()) {
                addUnitRuleDeclaration(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addUnitRuleDeclaration(UnitRule unit, StringBuilder sb) {
        var rn = unit.ruleName();

        if (rn.returnType() != null &&
                !rn.returnType().endsWith("*") && !unit.isInline()) {
            throw new IllegalStateException(
                    "Only pointer types allowed for non-inline rule: " + rn );
        }

        sb.append("static ").append(rn.returnTypeOr("void *"))
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

    private static final List<Integer> hashes = new ArrayList<>();

    public static String getFunctionBodies(GrammarSpec spec) {
        StringBuilder sb = new StringBuilder();
        hashes.clear();
        for (NamedRule namedRule : spec.namedRules()) {
            addUnitRuleBody(namedRule.root(), sb, namedRule.args());
            for (UnitRule component : namedRule.components()) {
                addUnitRuleBody(component, sb, Collections.emptyMap());
            }
        }
        return sb.toString();
    }

    private static String ruleNameHash(RuleName ruleName) {
        String sym = ruleName.symbolicName();

        int h = 0;
        for (byte v : sym.getBytes(StandardCharsets.UTF_8)) {
            h = 31 * h + (v & 0xff);
        }

        int hash = Math.floorMod(h, 1000);
        while (hashes.contains(hash)) {
            hash++;
        }
        hashes.add(hash);
        return String.valueOf(hash);
    }

    private static void addUnitRuleBody(UnitRule unit, StringBuilder sb, Map<String, String> args) {
        sb.append("\n");
        sb.append(StringUtil.inlinedoc(unit.grammarString()));

        var rn = unit.ruleName();
        var returnType = rn.returnTypeOr("void *");
        sb.append("\nstatic ").append(returnType)
                .append(rn.symbolicName());
        sb.append("(parser_t *p) {\n");

        String resultName;
        String hashStr;
        if (unit.isInline()) {
            // not needed
            resultName = "ERROR";
            hashStr = "ERROR";

            sb.append("    size_t pos = p->pos;\n");
        } else {
            hashStr = ruleNameHash(rn);
            sb.append("    const frame_t f = {")
                    .append(hashStr)
                    .append(", p->pos, FUNC};\n");
            resultName = "res_" + hashStr;

            var resultDeclare = "    " + returnType + resultName;
            sb.append(resultDeclare);
            sb.append(unit.leftRecursive() ? " = 0;\n" : ";\n");
        }

        boolean memoize = args.containsKey("memo") || unit.leftRecursive();
        if (memoize) {
            if (unit.isInline())
                throw new IllegalStateException("Inline rules cannot be memoized");
            sb.append("    if (is_memoized(p, &f, (void **) &")
                    .append(resultName).append(")) {\n");
            sb.append("        return ").append(resultName).append(";\n");
            sb.append("    }\n");
        }

        if (unit.leftRecursive()) {
            addLeftRecursiveUnitRuleBody(unit, hashStr, sb);
        } else {
            switch (unit.ruleType()) {
                case Disjunction -> addDisjunctionBody(unit, hashStr, sb);
                case Conjunction -> addConjunctionBody(unit, hashStr, sb);
            }
        }

        if (memoize) {
            sb.append("    insert_memo(p, &f, ").append(resultName).append(");\n");
        }

        if (!unit.isInline()) {
            sb.append("    return exit_frame(p, &f, ").append(resultName).append(");\n");
        }

        sb.append("}\n");

        for (UnitField field : unit.fields()) {
            getLoopParser(field, sb);
        }
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit,  String hashStr, StringBuilder sb) {
        var rtype = unit.ruleName().returnTypeOr("void *");
        var resultName = "res_" + hashStr;
        var altName = "alt_" + hashStr;

        sb.append("    ").append(rtype).append(altName).append(";\n");
        sb.append("    size_t maxpos;\n");
        sb.append("    ").append(rtype).append("max;\n");

        if (unit.isInline())
            throw new IllegalStateException("LR rules cannot be inline");

        sb.append("    if (enter_frame(p, &f)) {\n");
        sb.append("        do {\n");
        sb.append("            maxpos = p->pos;\n");
        sb.append("            max = ").append(resultName).append(";\n");
        sb.append("            insert_memo(p, &f, max);\n");
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

    private static void addConjunctionBody(UnitRule unit, String hashStr, StringBuilder sb) {
        List<String> fieldNames = new ArrayList<>();

        int j = 0;
        for (UnitField field : unit.fields()) {
            if (!isImportantField(field)) continue;

            var name = field.fieldName().snakeCaseUnconflicted();
            fieldNames.add(name);

            if (unit.resultClause() != null) {
                var template = unit.resultClause().template();
                var varName = "%" + ((char) ('a' + j));
                if (!template.contains(varName)) {
                    j++;
                    continue;
                }
            }
            j++;
            var type = getParserFieldType(field);
            sb.append("    ").append(type).append(name).append(";\n");
        }

        var resultName = "res_" + hashStr;
        if (unit.isInline()) {
            sb.append("    return (\n");
        } else {
            sb.append("    ").append(resultName).append(" = enter_frame(p, &f) && (\n");
        }

        var fields = unit.fields();
        j = 0;
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        ");
            sb.append("(");
            var field = fields.get(i);
            if (isImportantField(field)) {
                var fieldName = field.fieldName().snakeCaseUnconflicted();

                if (unit.resultClause() != null) {
                    var template = unit.resultClause().template();
                    var varName = "%" + ((char) ('a' + j));
                    if (template.contains(varName)) {
                        sb.append(fieldName);
                        sb.append(" = ");
                    }
                } else {
                    sb.append(fieldName);
                    sb.append(" = ");
                }
                j++;
            }

            var fieldExpr = getParserFieldExpr(field);

            // Fix when frame structs are not available
            if (unit.isInline()) sb.append(fieldExpr.replace("f.f_pos", "pos"));
            else sb.append(fieldExpr);

            if (i == fields.size() - 1) {
                sb.append(")");
            } else {
                sb.append(") &&\n");
            }
        }

        sb.append("\n    ) ? ");
        String templatedResult;
        if (unit.resultClause() == null) {
            templatedResult = "node(p)";
        } else {
            var resultClause = unit.resultClause().template();
            for (int i = 0; i < fieldNames.size(); i++) {
                var name = fieldNames.get(i);
                var templateName = "%" + ((char) ('a' + i));
                resultClause = resultClause.replace(templateName, name);
            }
            templatedResult = resultClause;
        }
        sb.append(templatedResult);
        if (unit.isInline()) {
            if (templatedResult.length() > 20) {
                sb.append(" :\n        ");
            } else sb.append(" : ");
            if (unit.ruleName().returnTypeOr("void *").endsWith("*")) {
                sb.append("(p->pos = pos, NULL);\n");
            } else {
                sb.append("(p->pos = pos, 0);\n");
            }
        } else {
            sb.append(" : 0;\n");
        }
    }


    private static String getRawParserFieldType(UnitField field) {
        var kind = field.resultSource().kind();
        return switch (kind) {
            case TokenLiteral, TokenType -> "token_t *";
            case UnitRule -> field.resultSource().asRuleName().returnTypeOr("void *");
        };
    }

    private static String getParserFieldType(UnitField field) {
        String type;
        if (field.isSingular() || field.isPredicate()) {
            type = getRawParserFieldType(field);
        } else type = "ast_list_t *";
        return type;
    }

    private static void addDisjunctionBody(UnitRule unit, String hashStr, StringBuilder sb) {

        for (UnitField field : unit.fields()) {
            var template = field.resultClause().template().strip();
            // Direct fallthrough
            if (template.equals("%a")) continue;
            // Still need to AND the result, but don't need separate variable
            if (!template.contains("%a")) continue;

            var type = getParserFieldType(field);
            var name = field.fieldName().snakeCaseUnconflicted();
            sb.append("    ").append(type).append(name).append(";\n");
        }

        var resultType = unit.ruleName().returnTypeOr("void *");
        var resultName = "res_" + hashStr;
        String altName;
        if (unit.isInline()) {
            altName = "alt";
        } else {
            altName = "alt_" + hashStr;
        }

        sb.append("    ").append(resultType).append(altName).append(";\n");
        if (unit.isInline()) {
            sb.append("    return (");
        } else {
            sb.append("    ").append(resultName).append(" = enter_frame(p, &f) && (");
        }

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

        sb.append("\n    ) ? ").append(altName);

        if (unit.isInline()) {
            if (resultType.endsWith("*")) {
                sb.append(" : (p->pos = pos, NULL);\n");
            } else {
                sb.append(" : (p->pos = pos, 0);\n");
            }
        } else {
            sb.append(" : 0;\n");
        }
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
                    "    " + rawType + fname + " = " + resultExpr + ";\n" +
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
                    "    " + rawType + fname + " = " + resultExpr + ";\n" +
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
                "    " + rawType + fname + ";\n" +
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
        return "test_and_reset(p, f.f_pos, " + getResultExpr(field) + ")";
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
}
