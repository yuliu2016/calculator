package org.fugalang.grammar.gen;

import org.fugalang.grammar.peg.wrapper.AndRule;
import org.fugalang.grammar.peg.wrapper.Item;
import org.fugalang.grammar.peg.wrapper.OrRule;
import org.fugalang.grammar.peg.wrapper.Repeat;
import org.fugalang.grammar.util.FirstAndMore;

public class PEGUtil {

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
        return repeat.hasDelimited() ? RepeatType.OnceOrMore :
                repeat.hasItemTimes() ? RepeatType.NoneOrMore :
                        repeat.hasItemPlus() ? RepeatType.OnceOrMore : RepeatType.Once;
    }

    public static Item getRepeatItem(Repeat repeat) {
        return repeat.hasDelimited() ? repeat.delimited().item() :
                repeat.hasItemTimes() ? repeat.itemTimes().item() :
                        repeat.hasItemPlus() ? repeat.itemPlus().item() : repeat.item();
    }

    public static String getItemString(Item item) {
        return item.hasName() ? item.name() :
                item.hasString() ? item.string() : null;
    }

    public static boolean isSingle(Repeat repeat) {
        if (!repeat.hasItem()) {
            return false;
        }
        var sub = repeat.item();
        return sub.hasString() || sub.hasName();
    }
}
