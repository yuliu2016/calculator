package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

public class CTransformOld {

    @Deprecated
    private static String getLoopExpr(UnitField field) {
        var rs = field.resultSource();
        if (rs.kind() == SourceKind.UnitRule) {
            // shortcut
            var func = ((RuleName) rs.value()).symbolicName();
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
        } else if (rs.kind() == SourceKind.TokenType || rs.kind() == SourceKind.TokenLiteral) {
            var te = (TokenEntry) rs.value();
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
    public static String getDummyCompiler(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();

        for (NamedRule namedRule : ruleSet.namedRules()) {
            addDummyDeclaration(sb, namedRule.getRoot());
            for (UnitRule component : namedRule.getComponents()) {
                addDummyDeclaration(sb, component);
            }
        }

        for (NamedRule namedRule : ruleSet.namedRules()) {
            sb.append("\n");
            sb.append(StringUtil.inlinedoc(namedRule.getRoot().grammarString()));
            addDummyFunction(sb, namedRule.getRoot());
            for (UnitRule component : namedRule.getComponents()) {
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
            if (field.resultSource().kind() == SourceKind.UnitRule) {
                var rn = (RuleName) field.resultSource().value();
                sb.append("    if (R_CHECK(n, R_").append(rn.symbolicName().toUpperCase())
                        .append(")) {\n")
                        .append("        dummy_").append(rn.symbolicName())
                        .append("(o);\n        return;\n")
                        .append("    }\n");
            } else {
                var te = (TokenEntry) field.resultSource().value();
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
                    field.resultSource().kind() == SourceKind.UnitRule) {
                var rn = (RuleName) field.resultSource().value();
                sb.append("    dummy_").append(rn.symbolicName())
                        .append("(").append(field.properFieldName()).append(");\n");
            }
        }
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() || (field.resultSource().kind()
                == SourceKind.TokenLiteral && field.isRequired()));
    }

    @Deprecated
    public static String getASTGen(RuleSet ruleSet) {
        StringBuilder sb = new StringBuilder();

        sb.append("#define FVAR(name, node, i) FAstNode *name = (node)->ast_v.fields[i]\n");
        sb.append("#define TVAR(name, node, i) FToken *name = (node)->ast_v.fields[i]->ast_v.token\n");

        for (NamedRule namedRule : ruleSet.namedRules()) {
            addStructFields(namedRule.getRoot(), sb);
            for (UnitRule component : namedRule.getComponents()) {
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
                if (field.resultSource().kind() == SourceKind.UnitRule) {
                    sb.append("    FVAR(");
                } else {
                    sb.append("    TVAR(");
                }

                sb.append(field.properFieldName()).append(", n, ").append(i).append(")");
                sb.append("; \\\n");
                i++;
            }
        }
    }
}
