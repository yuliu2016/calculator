package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * sub_rule: '(' 'or_rule' ')' | '[' 'or_rule' ']' | 'NAME' | 'STRING'
 */
public final class SubRule extends NodeWrapper {

    public SubRule(ParseTreeNode node) {
        super(ParserRules.SUB_RULE, node);
    }

    public SubRule1 orRule() {
        return get(0, SubRule1.class);
    }

    public boolean hasOrRule() {
        return has(0);
    }

    public SubRule2 orRule1() {
        return get(1, SubRule2.class);
    }

    public boolean hasOrRule1() {
        return has(1);
    }

    public String name() {
        return get(2, TokenType.NAME);
    }

    public boolean hasName() {
        return has(2);
    }

    public String string() {
        return get(3, TokenType.STRING);
    }

    public boolean hasString() {
        return has(3);
    }

    /**
     * '(' 'or_rule' ')'
     */
    public static final class SubRule1 extends NodeWrapper {

        public SubRule1(ParseTreeNode node) {
            super(ParserRules.SUB_RULE_1, node);
        }

        public OrRule orRule() {
            return get(1, OrRule.class);
        }
    }

    /**
     * '[' 'or_rule' ']'
     */
    public static final class SubRule2 extends NodeWrapper {

        public SubRule2(ParseTreeNode node) {
            super(ParserRules.SUB_RULE_2, node);
        }

        public OrRule orRule() {
            return get(1, OrRule.class);
        }
    }
}
