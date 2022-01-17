package org.fugalang.grammar.util;

import org.fugalang.grammar.common.FieldType;
import org.fugalang.grammar.common.ResultClause;
import org.fugalang.grammar.common.RuleName;
import org.fugalang.grammar.common.TokenEntry;
import org.fugalang.grammar.peg.wrapper.*;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.fugalang.grammar.common.FieldType.*;

public class PEGUtil {

    public static Iterable<Sequence> allSequences(AltList altList) {
        return FirstAndMore.of(altList.sequence(),
                altList.alternatives(), Alternative::sequence);
    }

    public static FieldType getFieldType(Primary primary) {
        if (primary.hasDelimited()) return RequiredList;
        if (primary.hasBitAndItem()) return RequireTrue;
        if (primary.hasExclaimItem()) return RequireFalse;
        if (primary.hasItemTimes()) return OptionalList;
        if (primary.hasItemPlus()) return RequiredList;
        if (primary.hasItem()) return Required;
        throw new IllegalArgumentException();
    }

    public static Item getModifierItem(Primary primary) {
        if (primary.hasDelimited()) return primary.delimited().item();
        if (primary.hasBitAndItem()) return primary.bitAndItem().item();
        if (primary.hasExclaimItem()) return primary.exclaimItem().item();
        if (primary.hasItemTimes()) return primary.itemTimes().item();
        if (primary.hasItemPlus()) return primary.itemPlus().item();
        if (primary.hasItem()) return primary.item();
        throw new IllegalArgumentException();
    }

    public static String getItemString(Item item) {
        return item.hasName() ? item.name() :
                item.hasString() ? item.string() : null;
    }

    public static boolean isSingle(Primary primary) {
        if (primary.hasItem()) {
            var it = primary.item();
            return it.hasString() || it.hasName();
        }
        if (primary.hasBitAndItem()) {
            var it = primary.bitAndItem().item();
            return it.hasString() || it.hasName();
        }
        return false;
    }

    private static String getFirstName(AltList altList) {
        var primaries = altList.sequence().primaries();
        if (primaries.isEmpty()) return null;
        var sub = getModifierItem(primaries.get(0));
        return sub.hasName() ? sub.name() : null;
    }

    public static boolean checkLeftRecursive(Rule rule, Map<String, String> args) {
        var altList = rule.ruleSuite().altList();
        var name = rule.name();
        var expected = !altList.alternatives().isEmpty() && name.equals(getFirstName(altList));
        if (args.containsKey("left_recursive")) {
            return true;
        } else {
            if (expected) throw new IllegalStateException();
            return false;
        }
    }


    public static String getSmartName(RuleName ruleName, Sequence sequence, Map<String, TokenEntry> tokenMap) {
        var primaries = sequence.primaries();
        if (primaries.size() <= 3 &&
                primaries.stream().allMatch(PEGUtil::isSingle)) {

            StringJoiner joiner = new StringJoiner("_");
            for (var primary : primaries) {
                var itemString = PEGUtil.getItemString(PEGUtil.getModifierItem(primary));
                if (itemString == null) throw new IllegalStateException();
                if (StringUtil.isWord(itemString)) {
                    // make lowercase in case it's a token type
                    joiner.add(itemString.toLowerCase());
                } else {
                    joiner.add(tokenMap.get(itemString).snakeCase());
                }
            }
            return StringUtil.decap(joiner.toString());
        }
        return ruleName.symbolicName();
    }


    public static String getSmartName(RuleName ruleName, AltList altList, Map<String, TokenEntry> tokenMap) {
        var andList = altList.alternatives();
        if (andList.isEmpty()) {
            return getSmartName(ruleName, altList.sequence(), tokenMap);
        }
        if (andList.size() == 1) {
            return getSmartName(ruleName, altList.sequence(), tokenMap) +
                    "_or_" + getSmartName(ruleName, andList.get(0).sequence(), tokenMap);
        }
        return ruleName.symbolicName();
    }

    public static Map<String, String> extractRuleArgs(Rule rule) {
        Map<String, String> args;
        if (rule.hasRuleArgs()) {
            args = new LinkedHashMap<>();
            for (RuleArg ruleArg : rule.ruleArgs().ruleArgs()) {
                args.put(ruleArg.name(), ruleArg.hasAssignName() ? ruleArg.assignName().name() : null);
            }
        } else {
            args = Collections.emptyMap();
        }
        return args;
    }

    public static TokenEntry getDelimiter(Primary primary, Map<String, TokenEntry> tokenMap) {
        TokenEntry delimiter;
        if (primary.hasDelimited()) {
            delimiter = tokenMap.get(primary.delimited().string());
            if (!delimiter.isLiteral()) throw new RuntimeException("Delimiter must be literal");
        } else {
            delimiter = null;
        }
        return delimiter;
    }

    private static String exprNameJoin(ExprName name) {
        return String.join(".", name.names());
    }

    private static String exprArgToString(ExprArg arg) {
        if (arg.hasExprName()) {
            return exprNameJoin(arg.exprName());
        } else if (arg.hasNumber()) {
            return arg.number();
        } else if (arg.hasModulusName()) {
            return "%" + arg.modulusName().name();
        } else if (arg.hasExprCall()) {
            return exprCallToString(arg.exprCall());
        } else throw new IllegalStateException();
    }

    private static String exprCallToString(ExprCall call) {
        return exprNameJoin(call.exprName()) + "(" +
                call.exprArgs().stream()
                        .map(PEGUtil::exprArgToString)
                        .collect(Collectors.joining(", ")) + ")";
    }

    public static ResultClause getResultClause(Sequence sequence) {
        if (!sequence.hasResultExpr()) return null;
        Expression expr = sequence.resultExpr().expression();
        String template;
        if (expr.hasExprCall()) {
            template = exprCallToString(expr.exprCall());
        } else if (expr.hasName()) {
            template = "%" + expr.name();
        } else if (expr.hasString()) {
            template = expr.string();
        } else throw new IllegalStateException();
        return new ResultClause(template);
    }

    public static ResultClause resultClauseOrElse(
            Sequence sequence, String elseTemplate) {
        if (sequence.hasResultExpr()) {
            return getResultClause(sequence);
        }
        return new ResultClause(elseTemplate);
    }

    public static RuleName getRuleName(Rule rule) {
        var ruleName = rule.name();
        var returnType = rule.hasReturnType() ? rule.returnType().name() : null;
        return RuleName.of(ruleName, returnType);
    }
}
