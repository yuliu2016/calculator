package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * rule:
 * *   | NAME [return_type] [rule_args] rule_suite
 */
public final class Rule extends NodeWrapper {

    public Rule(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public ReturnType returnType() {
        return new ReturnType(get(1));
    }

    public boolean hasReturnType() {
        return has(1);
    }

    public RuleArgs ruleArgs() {
        return new RuleArgs(get(2));
    }

    public boolean hasRuleArgs() {
        return has(2);
    }

    public RuleSuite ruleSuite() {
        return new RuleSuite(get(3));
    }
}
