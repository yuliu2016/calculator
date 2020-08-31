package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * rule:
 * *   | NAME [rule_args] rule_suite
 */
public final class Rule extends NodeWrapper {

    public Rule(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public RuleArgs ruleArgs() {
        return new RuleArgs(get(1));
    }

    public boolean hasRuleArgs() {
        return has(1);
    }

    public RuleSuite ruleSuite() {
        return new RuleSuite(get(2));
    }
}
