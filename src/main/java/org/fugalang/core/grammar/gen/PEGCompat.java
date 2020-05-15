package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.AndRule;
import org.fugalang.core.grammar.pgen.OrRule;
import org.fugalang.core.grammar.pgen.RepeatRule;
import org.fugalang.core.grammar.pgen.SubRule;
import org.fugalang.core.grammar.util.FirstAndMore;

import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PEGCompat {
    public static String constructString(OrRule orRule) {
        return constructString(orRule.andRule()) + orRule
                .orRule2List()
                .stream()
                .map(rule -> " | " + constructString(rule.andRule()))
                .collect(Collectors.joining());
    }

    public static String constructString(AndRule andRule) {
        return constructString(andRule.repeatRule()) + andRule
                .andRule2List()
                .stream()
                .map(rule -> " " + constructString(rule.repeatRule()))
                .collect(Collectors.joining());
    }

    public static String constructString(RepeatRule repeatRule) {
        var modifier = repeatRule.hasRepeatRule2() ?
                (repeatRule.repeatRule2().isTokenPlus() ? "+" : "*")
                : "";
        return constructString(repeatRule.subRule()) + modifier;
    }

    public static String constructString(SubRule subRule) {
        return subRule.hasSubRule1() ? "(" + constructString(subRule.subRule1().orRule()) + ")" :
                subRule.hasSubRule2() ? "[" + constructString(subRule.subRule2().orRule()) + "]" :
                        "'" + getSubruleString(subRule) + "'";
    }

    public static Iterable<AndRule> allAndRules(OrRule orRule) {
        return FirstAndMore.of(
                orRule.andRule(),
                orRule.orRule2List()
                        .stream()
                        .map(OrRule.OrRule2::andRule)
                        .iterator()
        );
    }

    public static Iterable<RepeatRule> allRepeatRules(AndRule andRule) {
        return FirstAndMore.of(
                andRule.repeatRule(),
                andRule.andRule2List()
                        .stream()
                        .map(AndRule.AndRule2::repeatRule)
                        .iterator()
        );
    }

    public static SubRuleType getRuleType(SubRule subRule) {
        return subRule.hasSubRule1() ? SubRuleType.Group :
                subRule.hasSubRule2() ? SubRuleType.Optional :
                        SubRuleType.Token;
    }

    public static RepeatType getRepeatType(RepeatRule repeatRule) {
        return repeatRule.hasRepeatRule2() ?
                repeatRule.repeatRule2().isTokenPlus() ?
                        RepeatType.OnceOrMore
                        : RepeatType.NoneOrMore
                : RepeatType.Once;
    }

    public static String getSubruleString(SubRule subRule) {
//        return subRule.token();
        return subRule.hasName() ? subRule.name() :
                subRule.hasString() ? subRule.string() : null;
    }
}
