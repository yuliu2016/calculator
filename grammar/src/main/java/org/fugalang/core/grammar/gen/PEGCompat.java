package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.AndRule;
import org.fugalang.core.grammar.pgen.OrRule;
import org.fugalang.core.grammar.pgen.RepeatRule;
import org.fugalang.core.grammar.pgen.SubRule;
import org.fugalang.core.grammar.util.FirstAndMore;

import java.util.stream.Collectors;

public class PEGCompat {
    public static String constructString(OrRule orRule) {
        return constructString(orRule.andRule()) + orRule
                .orRule2s()
                .stream()
                .map(rule -> " | " + constructString(rule.andRule()))
                .collect(Collectors.joining());
    }

    public static String constructString(AndRule andRule) {
        return constructString(andRule.repeatRule()) + andRule
                .repeatRules()
                .stream()
                .map(rule -> " " + constructString(rule.repeatRule()))
                .collect(Collectors.joining());
    }

    public static String constructString(RepeatRule repeatRule) {
        var modifier = repeatRule.hasTimesOrPlus() ?
                (repeatRule.timesOrPlus().isPlus() ? "+" : "*")
                : "";
        return constructString(repeatRule.subRule()) + modifier;
    }

    public static String constructString(SubRule subRule) {
        return subRule.hasOrRule() ? "(" + constructString(subRule.orRule().orRule()) + ")" :
                subRule.hasOrRule1() ? "[" + constructString(subRule.orRule1().orRule()) + "]" :
                        "'" + getSubruleString(subRule) + "'";
    }

    public static Iterable<AndRule> allAndRules(OrRule orRule) {
        return FirstAndMore.of(orRule.andRule(),
                orRule.orRule2s(), OrRule.OrRule2::andRule);
    }

    public static Iterable<RepeatRule> allRepeatRules(AndRule andRule) {
        return FirstAndMore.of(andRule.repeatRule(),
                andRule.repeatRules(), AndRule.AndRule2::repeatRule);
    }

    public static SubRuleType getRuleType(SubRule subRule) {
        return subRule.hasOrRule() ? SubRuleType.Group :
                subRule.hasOrRule1() ? SubRuleType.Optional :
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
}