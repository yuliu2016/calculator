package org.fugalang.grammar.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * or_rule: and_rule ([NEWLINE] '|' and_rule)*
 */
public final class OrRule extends NodeWrapper {

    public OrRule(ParseTreeNode node) {
        super(node);
    }

    public AndRule andRule() {
        return get(0, AndRule.class);
    }

    public List<OrRule2> orRule2s() {
        return getList(1, OrRule2.class);
    }

    /**
     * [NEWLINE] '|' and_rule
     */
    public static final class OrRule2 extends NodeWrapper {

        public OrRule2(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0);
        }

        public AndRule andRule() {
            return get(2, AndRule.class);
        }
    }
}
