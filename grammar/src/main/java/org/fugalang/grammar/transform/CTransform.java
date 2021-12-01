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
        sb.append("(FParser *);\n");
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

        List<String> flags = new ArrayList<>();

        if (args.containsKey("memo") && !unit.leftRecursive()) {
            flags.add("F_MEMO");
        }
        if (unit.leftRecursive()) {
            flags.add("F_LR");
        }
        var ws = args.get("allow_whitespace");
        if ("true".equals(ws)) {
            flags.add("F_ALLOW_SPACES");
        } else if ("false".equals(ws)) {
            flags.add("F_DISALLOW_SPACES");
        }
        var flagsStr = flags.isEmpty() ? "0" : String.join(" | ", flags);

        sb.append("\nstatic void *")
                .append(rn.symbolicName());
        sb.append("(FParser *p) {\n");
        sb.append("    frame_t f = {")
                .append(unit.ruleIndex())
                .append(", p->pos, FUNC, 0, ")
                .append(flagsStr)
                .append("};\n");

        if (unit.leftRecursive()) {
            addLeftRecursiveUnitRuleBody(unit, sb);
        } else {

            switch (unit.ruleType()) {
                case Disjunction -> addDisjunctionBody(unit, sb);
                case Conjunction -> addConjunctionBody(unit, sb);
            }
        }

        sb.append("    return exit(p, &f, r);\n");
        sb.append("}\n");

        for (UnitField field : unit.fields()) {
            getLoopParser(field, sb);
        }
    }

    private static void addLeftRecursiveUnitRuleBody(UnitRule unit, StringBuilder sb) {
        sb.append("    void *a = 0, *r = 0, *m = 0;\n");
        sb.append("    if (!enter(p, &f)) goto exit;\n");

        sb.append("    size_t i = f.f_pos;\n");
        sb.append("    while(memoize(p, &f, m, i), 1) {\n");
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
                        if (p->pos <= i) break;
                        m = a, i = p->pos;
                    }
                    p->pos = i;
                    r = m ? node_1(p, &f, m) : 0;
                exit:
                """);
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() || (field.getResultSource().kind()
                == SourceKind.TokenLiteral && field.isRequired()));
    }

    private static void addConjunctionBody(UnitRule unit, StringBuilder sb) {

        int importantCount = 0;

        int impCount = unit.fields().stream()
                .filter(CTransform::isImportantField)
                .toList().size();
        sb.append("    void ");
        switch (impCount) {
            case 0 -> sb.append("*r;\n");
            case 1 -> sb.append("*a, *r;\n");
            case 2 -> sb.append("*a, *b, *r;\n");
            case 3 -> sb.append("*a, *b, *c, *r;\n");
            case 4 -> sb.append("*a, *b, *c, *d, *r;\n");
        }

        sb.append("    r = enter(p, &f) && (\n");

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

    private static void addDisjunctionBody(UnitRule unit, StringBuilder sb) {
        sb.append("    void *a, *r;\n");
        sb.append("    r = enter(p, &f) && (\n");
        var fields = unit.fields();
        for (int i = 0; i < fields.size(); i++) {
            sb.append("        ");
            sb.append("(a = ");
            var result = getParserFieldExpr(fields.get(i),
                    unit.ruleType(), i == fields.size() - 1);
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

    private static void getLoopParser(UnitField field, StringBuilder sb) {

    }

    private static String getLoopExpr(UnitField field) {
        var rs = field.getResultSource();
        if (rs.kind() == SourceKind.UnitRule) {
            // shortcut
            var func = ((RuleName) rs.value()).symbolicName();
            if (field.getFieldType() == FieldType.RequiredList) {
                if (field.getDelimiter() == null) {
                    return "sequence(p, " + func + ", 0)";
                } else {
                    return "delimited(p, " + field.getDelimiter().index() +
                            ", \"" + field.getDelimiter().literalValue() +
                            "\", " + func + ")";
                }
            } else if (field.getFieldType() == FieldType.OptionalList) {
                return "sequence(p, " + func + ", 1)";
            }
            throw new IllegalStateException();
        } else if (rs.kind() == SourceKind.TokenType || rs.kind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.value();
            if (field.getFieldType() == FieldType.RequiredList) {
                if (field.getDelimiter() == null) {
                    return "t_sequence(p, " + te.index() + ", \"" + te.literalValue() + "\", 0)";
                } else {
                    var delim = field.getDelimiter();
                    return "t_delimited(p, " + delim.index() +
                            ", \"" + delim.literalValue() +
                            "\", " + te.index() + ", \"" + te.literalValue() + "\")";
                }
            } else if (field.getFieldType() == FieldType.OptionalList) {
                return "t_sequence(p, " + te.index() + ", \"" + te.literalValue() + "\", 1)";
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
