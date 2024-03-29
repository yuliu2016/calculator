package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

public class CTransformOld {

    @SuppressWarnings("unused")
    @Deprecated
    private static String getLoopExpr(UnitField field) {
        var rs = field.resultSource();
        if (rs.isUnitRule()) {
            // shortcut
            var func = rs.asRuleName().symbolicName();
            if (field.fieldType() == FieldType.RequiredList) {
                if (field.delimiter() == null) {
                    return "sequence(p, " + func + ", 0)";
                } else {
                    return "delimited(p, " + field.delimiter().index() +
                            ", \"" + field.delimiter().literalValue() +
                            "\", " + func + ")";
                }
            } else if (field.fieldType() == FieldType.OptionalList) {
                return "sequence(p, " + func + ", 1)";
            }
            throw new IllegalStateException();
        } else if (rs.isTokenType() || rs.isTokenLiteral()) {
            var te = rs.asTokenEntry();
            if (field.fieldType() == FieldType.RequiredList) {
                if (field.delimiter() == null) {
                    return "t_sequence(p, " + te.index() + ", \"" + te.literalValue() + "\", 0)";
                } else {
                    var delim = field.delimiter();
                    return "t_delimited(p, " + delim.index() +
                            ", \"" + delim.literalValue() +
                            "\", " + te.index() + ", \"" + te.literalValue() + "\")";
                }
            } else if (field.fieldType() == FieldType.OptionalList) {
                return "t_sequence(p, " + te.index() + ", \"" + te.literalValue() + "\", 1)";
            }
            throw new IllegalArgumentException("optional list with token not supported");
        }
        throw new IllegalArgumentException();
    }

    @Deprecated
    public static String getDummyCompiler(GrammarSpec spec) {
        StringBuilder sb = new StringBuilder();

        for (NamedRule namedRule : spec.namedRules()) {
            addDummyDeclaration(sb, namedRule.root());
            for (UnitRule component : namedRule.components()) {
                addDummyDeclaration(sb, component);
            }
        }

        for (NamedRule namedRule : spec.namedRules()) {
            sb.append("\n");
            sb.append(StringUtil.inlinedoc(namedRule.root().grammarString()));
            addDummyFunction(sb, namedRule.root());
            for (UnitRule component : namedRule.components()) {
                addDummyFunction(sb, component);
            }
        }

        return sb.toString();
    }

    private static void addDummyDeclaration(StringBuilder sb, UnitRule unit) {
        sb.append("void dummy_").append(unit.ruleName().symbolicName())
                .append("(FAstNode *n);\n");
    }

    private static void addDummyFunction(StringBuilder sb, UnitRule unit) {

        sb.append("\nvoid dummy_").append(unit.ruleName().symbolicName())
                .append("(FAstNode *n) {\n");
        if (unit.leftRecursive() || unit.ruleType() == RuleType.Disjunction) {
            addDummyDisjunct(sb, unit);
        } else {
            addDummyUnpack(sb, unit);
        }
        sb.append("}\n");
    }

    private static void addDummyDisjunct(StringBuilder sb, UnitRule unit) {
        sb.append("    FAstNode *o = n->ast_v.fields[0];\n");

        for (UnitField field : unit.fields()) {
            if (field.resultSource().isUnitRule()) {
                var rn = field.resultSource().asRuleName();
                sb.append("    if (R_CHECK(n, R_").append(rn.symbolicName().toUpperCase())
                        .append(")) {\n")
                        .append("        dummy_").append(rn.symbolicName())
                        .append("(o);\n        return;\n")
                        .append("    }\n");
            } else {
                var te = field.resultSource().asTokenEntry();
                sb.append("    if (T_CHECK(n, ")
                        .append("T_").append(te.snakeCase().toUpperCase())
                        .append(")) {\n        return;\n    }\n");
            }
        }
    }

    private static void addDummyUnpack(StringBuilder sb, UnitRule unit) {
        if (unit.fields().stream().noneMatch(CTransformOld::isImportantField)) {
            return;
        }
        sb.append("    UNPACK_").append(unit.ruleName().symbolicName().toUpperCase())
                .append("(n)\n");
        for (UnitField field : unit.fields()) {
            if (isImportantField(field) &&
                    field.resultSource().isUnitRule()) {
                var rn = field.resultSource().asRuleName();
                sb.append("    dummy_").append(rn.symbolicName())
                        .append("(").append(field.fieldName().fullSnakeCase()).append(");\n");
            }
        }
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() ||
                (field.resultSource().isTokenLiteral() && field.isRequired()));
    }

    @Deprecated
    public static String getASTGen(GrammarSpec spec) {
        StringBuilder sb = new StringBuilder();

        sb.append("#define FVAR(name, node, i) FAstNode *name = (node)->ast_v.fields[i]\n");
        sb.append("#define TVAR(name, node, i) FToken *name = (node)->ast_v.fields[i]->ast_v.token\n");

        for (NamedRule namedRule : spec.namedRules()) {
            addStructFields(namedRule.root(), sb);
            for (UnitRule component : namedRule.components()) {
                addStructFields(component, sb);
            }
        }
        return sb.toString();
    }

    private static void addStructFields(UnitRule unit, StringBuilder sb) {
        var upName = unit.ruleName().symbolicName().toUpperCase();

        sb.append("\n#define R_").append(upName)
                .append(" ").append(unit.ruleIndex()).append("\n");

        if (unit.leftRecursive() || unit.ruleType() == RuleType.Disjunction) {
            return;
        }

        if (unit.fields().stream().noneMatch(CTransformOld::isImportantField)) {
            return;
        }

        sb.append("#define UNPACK_").append(upName).append("(n) \\\n");
        int i = 0;
        for (UnitField field : unit.fields()) {
            if (isImportantField(field)) {
                if (field.resultSource().isUnitRule()) {
                    sb.append("    FVAR(");
                } else {
                    sb.append("    TVAR(");
                }

                sb.append(field.fieldName().fullSnakeCase()).append(", n, ").append(i).append(")");
                sb.append("; \\\n");
                i++;
            }
        }
    }
}
