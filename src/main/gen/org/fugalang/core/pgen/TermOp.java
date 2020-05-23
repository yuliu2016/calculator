package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * term_op: '*' | '@' | '/' | '%' | '//'
 */
public final class TermOp extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("term_op", RuleType.Disjunction);

    public static TermOp of(ParseTreeNode node) {
        return new TermOp(node);
    }

    private TermOp(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isTimes() {
        return is(0);
    }

    public boolean isMatrixTimes() {
        return is(1);
    }

    public boolean isDiv() {
        return is(2);
    }

    public boolean isModulus() {
        return is(3);
    }

    public boolean isFloorDiv() {
        return is(4);
    }
}
