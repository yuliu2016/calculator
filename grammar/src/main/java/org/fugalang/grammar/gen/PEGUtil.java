package org.fugalang.grammar.gen;

import org.fugalang.grammar.peg.AndRule;
import org.fugalang.grammar.peg.Item;
import org.fugalang.grammar.peg.OrRule;
import org.fugalang.grammar.peg.Repeat;
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
                .repeats()
                .stream()
                .map(PEGUtil::constructString)
                .collect(Collectors.joining(" "));
    }

    public static String constructString(Repeat repeat) {
        var modifier = repeat.hasTimesOrPlus() ?
                (repeat.timesOrPlus().isPlus() ? "+" : "*")
                : "";
        return constructString(repeat.item()) + modifier;
    }

    public static String constructString(Item item) {
        return item.hasGroup() ? "(" + constructString(item.group().orRule()) + ")" :
                item.hasOptional() ? "[" + constructString(item.optional().orRule()) + "]" :
                        item.hasName() ? item.name() : "'" + item.string() + "'";
    }

    public static Iterable<AndRule> allAndRules(OrRule orRule) {
        return FirstAndMore.of(orRule.andRule(),
                orRule.orRule2s(), OrRule.OrRule2::andRule);
    }

    public static SubRuleType getRuleType(Item item) {
        return item.hasGroup() ? SubRuleType.Group :
                item.hasOptional() ? SubRuleType.Optional :
                        SubRuleType.Token;
    }

    public static RepeatType getRepeatType(Repeat repeat) {
        return repeat.hasTimesOrPlus() ?
                repeat.timesOrPlus().isPlus() ?
                        RepeatType.OnceOrMore
                        : RepeatType.NoneOrMore
                : RepeatType.Once;
    }

    public static String getItemString(Item item) {
        return item.hasName() ? item.name() :
                item.hasString() ? item.string() : null;
    }

    public static boolean isSingle(Repeat repeat) {
        if (repeat.hasTimesOrPlus()) {
            return false;
        }
        var sub = repeat.item();
        return sub.hasString() || sub.hasName();
    }

}
