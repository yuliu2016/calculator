package org.fugalang.grammar.transform;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.common.*;
import org.fugalang.grammar.util.StringUtil;

public class CTransformOld {

    @Deprecated
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
        sb.append("void dummy_").append(unit.getRuleName().symbolicName())
                .append("(FAstNode *n);\n");
    }

    private static void addDummyFunction(StringBuilder sb, UnitRule unit) {

        sb.append("\nvoid dummy_").append(unit.getRuleName().symbolicName())
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
            if (field.getResultSource().kind() == SourceKind.UnitRule) {
                var rn = (RuleName) field.getResultSource().value();
                sb.append("    if (R_CHECK(n, R_").append(rn.symbolicName().toUpperCase())
                        .append(")) {\n")
                        .append("        dummy_").append(rn.symbolicName())
                        .append("(o);\n        return;\n")
                        .append("    }\n");
            } else {
                var te = (TokenEntry) field.getResultSource().value();
                sb.append("    if (T_CHECK(n, ")
                        .append("T_").append(te.snakeCase().toUpperCase())
                        .append(")) {\n        return;\n    }\n");
            }
        }
    }

    private static void addDummyUnpack(StringBuilder sb, UnitRule unit) {
        if (unit.getFields().stream().noneMatch(CTransformOld::isImportantField)) {
            return;
        }
        sb.append("    UNPACK_").append(unit.getRuleName().symbolicName().toUpperCase())
                .append("(n)\n");
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field) &&
                    field.getResultSource().kind() == SourceKind.UnitRule) {
                var rn = (RuleName) field.getResultSource().value();
                sb.append("    dummy_").append(rn.symbolicName())
                        .append("(").append(field.getProperFieldName()).append(");\n");
            }
        }
    }

    private static boolean isImportantField(UnitField field) {
        return !(field.isPredicate() || (field.getResultSource().kind()
                == SourceKind.TokenLiteral && field.isRequired()));
    }

    @Deprecated
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
        var upName = unit.getRuleName().symbolicName().toUpperCase();

        sb.append("\n#define R_").append(upName)
                .append(" ").append(unit.getRuleIndex()).append("\n");

        if (unit.isLeftRecursive() || unit.getRuleType() == RuleType.Disjunction) {
            return;
        }

        if (unit.getFields().stream().noneMatch(CTransformOld::isImportantField)) {
            return;
        }

        sb.append("#define UNPACK_").append(upName).append("(n) \\\n");
        int i = 0;
        for (UnitField field : unit.getFields()) {
            if (isImportantField(field)) {
                if (field.getResultSource().kind() == SourceKind.UnitRule) {
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
}
