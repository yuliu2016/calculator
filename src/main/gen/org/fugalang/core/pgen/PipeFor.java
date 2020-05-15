package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * pipe_for: ['comp_for'] 'for' 'targetlist' ('if' 'expr')* ['parameters'] ['block_suite']
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

    public List<PipeFor4> pipeFor4List() {
        return getList(3, PipeFor4::of);
    }

    public Parameters parameters() {
        return Parameters.of(getItem(4));
    }

    public boolean hasParameters() {
        return hasItemOfRule(4, Parameters.RULE);
    }

    public BlockSuite blockSuite() {
        return BlockSuite.of(getItem(5));
    }

    public boolean hasBlockSuite() {
        return hasItemOfRule(5, BlockSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        CompFor.parse(t, lv + 1);
        r = t.consumeToken("for");
        r = r && Targetlist.parse(t, lv + 1);
        if (r) parsePipeFor4List(t, lv);
        if (r) Parameters.parse(t, lv + 1);
        if (r) BlockSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parsePipeFor4List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!PipeFor4.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'if' 'expr'
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

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("if");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
