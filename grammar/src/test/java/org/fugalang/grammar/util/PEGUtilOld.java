package org.fugalang.grammar.util;

import org.fugalang.grammar.common.Modifier;
import org.fugalang.grammar.common.SubRuleType;
import org.fugalang.grammar.peg.wrapper.Item;
import org.fugalang.grammar.peg.wrapper.Primary;

import static org.fugalang.grammar.common.Modifier.*;
import static org.fugalang.grammar.common.Modifier.Once;

@Deprecated
public class PEGUtilOld {
    public static Modifier getModifier(Primary primary) {
        if (primary.hasDelimited()) return OnceOrMore;
        if (primary.hasBitAndItem()) return TestTrue;
        if (primary.hasExclaimItem()) return TestFalse;
        if (primary.hasItemTimes()) return NoneOrMore;
        if (primary.hasItemPlus()) return OnceOrMore;
        if (primary.hasItem()) return Once;
        throw new IllegalArgumentException();
    }

    public static SubRuleType getRuleType(Item item) {
        return item.hasGroup() ? SubRuleType.Group :
                item.hasOptional() ? SubRuleType.Optional :
                        SubRuleType.Token;
    }
}
