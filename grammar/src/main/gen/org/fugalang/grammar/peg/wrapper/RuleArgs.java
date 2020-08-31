package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * rule_args:
 * *   | '(' ','.rule_arg+ ')'
 */
public final class RuleArgs extends NodeWrapper {

    public RuleArgs(ParseTreeNode node) {
        super(node);
    }

    public List<RuleArg> ruleArgs() {
        return getList(1, RuleArg::new);
    }
}
