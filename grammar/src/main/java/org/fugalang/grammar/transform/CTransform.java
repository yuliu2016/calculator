package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        sb.append("static FAstNode *")
                .append(rn.getRuleNameSymbolic());
        sb.append("(FParser *);\n");
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

        List<String> flags = new ArrayList<>();

        if (args.containsKey("memo") && !unit.isLeftRecursive()) {
            flags.add("F_MEMO");
        }
        if (unit.isLeftRecursive()) {
            flags.add("F_LR");
        }
        var ws = args.get("allow_whitespace");
        if ("true".equals(ws)) {
            flags.add("F_ALLOW_SPACES");
        } else if ("false".equals(ws)) {
            flags.add("F_DISALLOW_SPACES");
        }
        var flagsStr = flags.isEmpty() ? "0" : String.join(" | ", flags);

        sb.append("\nstatic FAstNode *")
                .append(rn.getRuleNameSymbolic());
        sb.append("(FParser *p) {\n");
        sb.append("    frame_t f = {")
                .append(unit.getRuleIndex())
                .append(", p->pos, FUNC, 0, ")
                .append(flagsStr)
                .append("};\n");

        if (unit.isLeftRecursive()) {
            addLeftRecursiveUnitRuleBody(unit, sb);
        } else {

            switch (unit.getRuleType()) {
                case Disjunction -> addDisjunctionBody(unit, sb);
                case Conjunction -> addConjunctionBody(unit, sb);
            }
        }

        sb.append("    return exit(p, &f, r);\n");
        sb.append("}\n");
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
        sb.append("    FAstNode *a = 0, *r = 0, *m = 0;\n");
        sb.append("    if (!enter(p, &f)) goto exit;\n");

        sb.append("    size_t i = f.f_pos;\n");
        sb.append("    while(1) {\n");
        sb.append("        memoize(p, &f, m, i);\n");
        sb.append("        p->pos = f.f_pos;\n");

        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        (a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append(";\n");
        sb.append("""
                        if (p->pos <= i) break;
                        m = a, i = p->pos;
                    }
                    p->pos = i;
                    r = m ? node_1(p, &f, m) : 0;
                exit:
                """);
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() || (field.getResultSource().getKind()
                == SourceKind.TokenLiteral && field.isRequired()));
    }

    private static void addConjunctionBody(UnitRule unit, StringBuilder sb) {

        int importantCount = 0;

        int impCount = unit.getFields().stream()
                .filter(CTransform::isImportantField)
                .toList().size();
        sb.append("    FAstNode ");
        switch (impCount) {
            case 0 -> sb.append("*r;\n");
            case 1 -> sb.append("*a, *r;\n");
            case 2 -> sb.append("*a, *b, *r;\n");
            case 3 -> sb.append("*a, *b, *c, *r;\n");
            case 4 -> sb.append("*a, *b, *c, *d, *r;\n");
        }

        sb.append("    r = enter(p, &f) && (\n");

        var fields = unit.getFields();
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
                    unit.getRuleType(), i == fields.size() - 1);
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

    private static void addDisjunctionBody(UnitRule unit, StringBuilder sb) {
        sb.append("    FAstNode *a, *r;\n");
        sb.append("    r = enter(p, &f) && (\n");
        var fields = unit.getFields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        ");
            sb.append("(a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.getRuleType(), i == fields.size() - 1);
            sb.append(result);
        }
        sb.append("\n    ) ? node_1(p, &f, a) : 0;\n");
    }

    private static String getParserFieldExpr(
            UnitField field, RuleType ruleType, boolean isLast) {
        return switch (field.getFieldType()) {
            case RequireTrue -> getRequiredExprPart(getTestExpr(field), ruleType, isLast);
            case RequireFalse -> getRequiredExprPart("!" + getTestExpr(field), ruleType, isLast);
            case Required -> getRequiredExprPart(getResultExpr(field), ruleType, isLast);
            case Optional -> getOptionalExprPart(getResultExpr(field), ruleType, isLast);
            case RequiredList, OptionalList -> getRequiredExprPart(getLoopExpr(field), ruleType, isLast);
        };
    }

    private static String getLoopExpr(UnitField field) {
        var rs = field.getResultSource();
        if (rs.getKind() == SourceKind.UnitRule) {
            // shortcut
            var func = ((RuleName) rs.getValue()).getRuleNameSymbolic();
            if (field.getFieldType() == FieldType.RequiredList) {
                if (field.getDelimiter() == null) {
                    return "sequence(p, " + func + ", 0)";
                } else {
                    return "delimited(p, " + field.getDelimiter().getIndex() +
                            ", \"" + field.getDelimiter().getLiteralValue() +
                            "\", " + func + ")";
                }
            } else if (field.getFieldType() == FieldType.OptionalList) {
                return "sequence(p, " + func + ", 1)";
            }
            throw new IllegalStateException();
        } else if (rs.getKind() == SourceKind.TokenType || rs.getKind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.getValue();
            if (field.getFieldType() == FieldType.RequiredList) {
                if (field.getDelimiter() == null) {
                    return "t_sequence(p, " + te.getIndex() + ", \"" + te.getLiteralValue() + "\", 0)";
                } else {
                    var delim = field.getDelimiter();
                    return "t_delimited(p, " + delim.getIndex() +
                            ", \"" + delim.getLiteralValue() +
                            "\", " + te.getIndex() + ", \"" + te.getLiteralValue() + "\")";
                }
            } else if (field.getFieldType() == FieldType.OptionalList) {
                return "t_sequence(p, " + te.getIndex() + ", \"" + te.getLiteralValue() + "\", 1)";
            }
            throw new IllegalArgumentException("optional list with token not supported");
        }
        throw new IllegalArgumentException();
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
        var rs = field.getResultSource();
        if (rs.getKind() == SourceKind.UnitRule) {
            return ((RuleName) rs.getValue()).getRuleNameSymbolic() + "(p)";
        } else if (rs.getKind() == SourceKind.TokenType || rs.getKind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.getValue();
            return "consume(p, " + te.getIndex() + ", \"" + te.getLiteralValue() + "\")";
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
