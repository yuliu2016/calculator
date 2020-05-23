package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * single_rule: 'NAME' ':' ['NEWLINE' '|'] 'or_rule' 'NEWLINE'
 */
public final class SingleRule extends NodeWrapper {

    public SingleRule(ParseTreeNode node) {
        super(ParserRules.SINGLE_RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public SingleRule3 newline() {
        return get(2, SingleRule3.class);
    }

    public boolean hasNewline() {
        return has(2);
    }

    public OrRule orRule() {
        return get(3, OrRule.class);
    }

    public String newline1() {
        return get(4, TokenType.NEWLINE);
    }

    /**
     * 'NEWLINE' '|'
     */
    public static final class SingleRule3 extends NodeWrapper {

        public SingleRule3(ParseTreeNode node) {
            super(ParserRules.SINGLE_RULE_3, node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }
    }
}
