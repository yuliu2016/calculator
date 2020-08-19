package org.fugalang.grammar.common;

import org.fugalang.grammar.peg.wrapper.AltList;
import org.fugalang.grammar.peg.wrapper.Item;
import org.fugalang.grammar.peg.wrapper.Primary;
import org.fugalang.grammar.peg.wrapper.Sequence;
import org.fugalang.grammar.util.FirstAndMore;

import static org.fugalang.grammar.common.Modifier.*;

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

    public static Modifier getModifier(Primary primary) {
        if (primary.hasDelimited()) return OnceOrMore;
        if (primary.hasBitAndItem()) return TestTrue;
        if (primary.hasNotItem()) return TestFalse;
        if (primary.hasItemTimes()) return NoneOrMore;
        if (primary.hasItemPlus()) return OnceOrMore;
        if (primary.hasItem()) return Once;
        throw new IllegalArgumentException();
    }

    public static Item getModifierItem(Primary primary) {
        if (primary.hasDelimited()) return primary.delimited().item();
        if (primary.hasBitAndItem()) return primary.bitAndItem().item();
        if (primary.hasNotItem()) return primary.notItem().item();
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
        var primaries = altList.sequence().primarys();
        if (primaries.isEmpty()) return null;
        var sub = getModifierItem(primaries.get(0));
        return sub.hasName() ? sub.name() : null;
    }

    public static boolean isLeftRecursive(String name, AltList altList) {
        return !altList.altList2s().isEmpty() && name.equals(getFirstName(altList));
    }
}