package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * rule_arg:
 * *   | NAME ['=' NAME]
 */
public final class RuleArg extends NodeWrapper {

    public RuleArg(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public RuleArg2 assignName() {
        return new RuleArg2(get(1));
    }

    public boolean hasAssignName() {
        return has(1);
    }

    /**
     * '=' NAME
     */
    public static final class RuleArg2 extends NodeWrapper {

        public RuleArg2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}
