package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pipe_for: ['comp_for'] 'for' 'targetlist' ['if' 'named_expr'] ['parameters' | 'block_suite']
 */
public final class PipeFor extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("pipe_for", RuleType.Conjunction);

    public static PipeFor of(ParseTreeNode node) {
        return new PipeFor(node);
    }

    private PipeFor(ParseTreeNode node) {
        super(RULE, node);
    }

    public CompFor compFor() {
        return CompFor.of(getItem(0));
    }

    public boolean hasCompFor() {
        return hasItemOfRule(0, CompFor.RULE);
    }

    public Targetlist targetlist() {
        return Targetlist.of(getItem(2));
    }

    public PipeFor4 pipeFor4() {
        return PipeFor4.of(getItem(3));
    }

    public boolean hasPipeFor4() {
        return hasItemOfRule(3, PipeFor4.RULE);
    }

    public PipeFor5 pipeFor5() {
        return PipeFor5.of(getItem(4));
    }

    public boolean hasPipeFor5() {
        return hasItemOfRule(4, PipeFor5.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        CompFor.parse(t, lv + 1);
        r = t.consumeToken("for");
        r = r && Targetlist.parse(t, lv + 1);
        if (r) PipeFor4.parse(t, lv + 1);
        if (r) PipeFor5.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'if' 'named_expr'
     */
    public static final class PipeFor4 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("pipe_for:4", RuleType.Conjunction);

        public static PipeFor4 of(ParseTreeNode node) {
            return new PipeFor4(node);
        }

        private PipeFor4(ParseTreeNode node) {
            super(RULE, node);
        }

        public NamedExpr namedExpr() {
            return NamedExpr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("if");
            r = r && NamedExpr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * 'parameters' | 'block_suite'
     */
    public static final class PipeFor5 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("pipe_for:5", RuleType.Disjunction);

        public static PipeFor5 of(ParseTreeNode node) {
            return new PipeFor5(node);
        }

        private PipeFor5(ParseTreeNode node) {
            super(RULE, node);
        }

        public Parameters parameters() {
            return Parameters.of(getItem(0));
        }

        public boolean hasParameters() {
            return hasItemOfRule(0, Parameters.RULE);
        }

        public BlockSuite blockSuite() {
            return BlockSuite.of(getItem(1));
        }

        public boolean hasBlockSuite() {
            return hasItemOfRule(1, BlockSuite.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Parameters.parse(t, lv + 1);
            r = r || BlockSuite.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
