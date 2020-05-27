package org.fugalang.grammar.gen;

import org.fugalang.grammar.peg.AndRule;
import org.fugalang.grammar.peg.OrRule;
import org.fugalang.grammar.peg.RepeatRule;
import org.fugalang.grammar.peg.SubRule;
import org.fugalang.grammar.util.FirstAndMore;

import java.util.stream.Collectors;

public class PEGUtil {
    public static String constructString(OrRule orRule) {
        return constructString(orRule.andRule()) + orRule
                .orRule2s()
                .stream()
                .map(rule -> " | " + constructString(rule.andRule()))
                .collect(Collectors.joining());
    }

    public static String constructString(AndRule andRule) {
        return andRule
                .repeatRules()
                .stream()
                .map(PEGUtil::constructString)
                .collect(Collectors.joining(" "));
    }

    public static String constructString(RepeatRule repeatRule) {
        var modifier = repeatRule.hasTimesOrPlus() ?
                (repeatRule.timesOrPlus().isPlus() ? "+" : "*")
                : "";
        return constructString(repeatRule.subRule()) + modifier;
    }

    public static String constructString(SubRule subRule) {
        return subRule.hasGroup() ? "(" + constructString(subRule.group().orRule()) + ")" :
                subRule.hasOptional() ? "[" + constructString(subRule.optional().orRule()) + "]" :
                        subRule.hasName() ? subRule.name() : "'" + subRule.string() + "'";
    }

    public static Iterable<AndRule> allAndRules(OrRule orRule) {
        return FirstAndMore.of(orRule.andRule(),
                orRule.orRule2s(), OrRule.OrRule2::andRule);
    }

    public static SubRuleType getRuleType(SubRule subRule) {
        return subRule.hasGroup() ? SubRuleType.Group :
                subRule.hasOptional() ? SubRuleType.Optional :
                        SubRuleType.Token;
    }

    public static RepeatType getRepeatType(RepeatRule repeatRule) {
        return repeatRule.hasTimesOrPlus() ?
                repeatRule.timesOrPlus().isPlus() ?
                        RepeatType.OnceOrMore
                        : RepeatType.NoneOrMore
                : RepeatType.Once;
    }

    public static String getSubruleString(SubRule subRule) {
        return subRule.hasName() ? subRule.name() :
                subRule.hasString() ? subRule.string() : null;
    }

    public static boolean isSingle(RepeatRule repeatRule) {
        if (repeatRule.hasTimesOrPlus()) {
            return false;
        }
        var sub = repeatRule.subRule();
        return sub.hasString() || sub.hasName();
    }

}