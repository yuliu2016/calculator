package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * arglist: 'argument' (',' 'argument')* [',']
 */
public final class Arglist extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("arglist", RuleType.Conjunction);

    public static Arglist of(ParseTreeNode node) {
        return new Arglist(node);
    }

    private Arglist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Argument argument() {
        return get(0, Argument::of);
    }

    public List<Arglist2> arguments() {
        return getList(1, Arglist2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'argument'
     */
    public static final class Arglist2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("arglist:2", RuleType.Conjunction);

        public static Arglist2 of(ParseTreeNode node) {
            return new Arglist2(node);
        }

        private Arglist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Argument argument() {
            return get(1, Argument::of);
        }
    }
}
