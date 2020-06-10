package org.fugalang.grammar.gen;

import org.fugalang.grammar.peg.wrapper.AltList;
import org.fugalang.grammar.peg.wrapper.Item;
import org.fugalang.grammar.peg.wrapper.Primary;
import org.fugalang.grammar.peg.wrapper.Sequence;
import org.fugalang.grammar.util.FirstAndMore;

public class PEGUtil {

    public static Iterable<Sequence> allSequences(AltList altList) {
        return FirstAndMore.of(altList.sequence(),
                altList.altList2s(), AltList.AltList2::sequence);
    }

    public static SubRuleType getRuleType(Item item) {
        return item.hasGroup() ? SubRuleType.Group :
                item.hasOptional() ? SubRuleType.Optional :
                        SubRuleType.Token;
    }

    public static RepeatType getRepeatType(Primary primary) {
        return primary.hasDelimited() ? RepeatType.OnceOrMore :
                primary.hasItemTimes() ? RepeatType.NoneOrMore :
                        primary.hasItemPlus() ? RepeatType.OnceOrMore : RepeatType.Once;
    }

    public static Item getRepeatItem(Primary primary) {
        return primary.hasDelimited() ? primary.delimited().item() :
                primary.hasItemTimes() ? primary.itemTimes().item() :
                        primary.hasItemPlus() ? primary.itemPlus().item() : primary.item();
    }

    public static String getItemString(Item item) {
        return item.hasName() ? item.name() :
                item.hasString() ? item.string() : null;
    }

    public static boolean isSingle(Primary primary) {
        if (!primary.hasItem()) {
            return false;
        }
        var item = primary.item();
        return item.hasString() || item.hasName();
    }
}
