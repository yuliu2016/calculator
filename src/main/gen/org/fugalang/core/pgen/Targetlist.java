package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * targetlist: 'target' (',' 'target')* [',']
 */
public final class Targetlist extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("targetlist", RuleType.Conjunction);

    public static Targetlist of(ParseTreeNode node) {
        return new Targetlist(node);
    }

    private Targetlist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Target target() {
        return get(0, Target::of);
    }

    public List<Targetlist2> targets() {
        return getList(1, Targetlist2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'target'
     */
    public static final class Targetlist2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("targetlist:2", RuleType.Conjunction);

        public static Targetlist2 of(ParseTreeNode node) {
            return new Targetlist2(node);
        }

        private Targetlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Target target() {
            return get(1, Target::of);
        }
    }
}
