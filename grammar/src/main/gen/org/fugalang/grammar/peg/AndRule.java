package org.fugalang.grammar.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * and_rule: repeat_rule+
 */
public final class AndRule extends NodeWrapper {

    public AndRule(ParseTreeNode node) {
        super(node);
    }

    public List<RepeatRule> repeatRules() {
        return getList(0, RepeatRule.class);
    }
}
