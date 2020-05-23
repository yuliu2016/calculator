package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * repeat_rule: 'sub_rule' ['*' | '+']
 */
public final class RepeatRule extends NodeWrapper {

    public RepeatRule(ParseTreeNode node) {
        super(ParserRules.REPEAT_RULE, node);
    }

    public SubRule subRule() {
        return get(0, SubRule.class);
    }

    public RepeatRule2 timesOrPlus() {
        return get(1, RepeatRule2.class);
    }

    public boolean hasTimesOrPlus() {
        return has(1);
    }

    /**
     * '*' | '+'
     */
    public static final class RepeatRule2 extends NodeWrapper {

        public RepeatRule2(ParseTreeNode node) {
            super(ParserRules.REPEAT_RULE_2, node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public boolean isPlus() {
            return is(1);
        }
    }
}
