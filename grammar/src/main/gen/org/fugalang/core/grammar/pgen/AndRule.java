package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * and_rule: 'repeat_rule' ('repeat_rule')*
 */
public final class AndRule extends NodeWrapper {

    public AndRule(ParseTreeNode node) {
        super(ParserRules.AND_RULE, node);
    }

    public RepeatRule repeatRule() {
        return get(0, RepeatRule.class);
    }

    public List<AndRule2> repeatRules() {
        return getList(1, AndRule2.class);
    }

    /**
     * 'repeat_rule'
     */
    public static final class AndRule2 extends NodeWrapper {

        public AndRule2(ParseTreeNode node) {
            super(ParserRules.AND_RULE_2, node);
        }

        public RepeatRule repeatRule() {
            return get(0, RepeatRule.class);
        }
    }
}
