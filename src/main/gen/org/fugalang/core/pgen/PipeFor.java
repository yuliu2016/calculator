package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * pipe_for: ['comp_for'] 'for' 'targetlist' ('if' 'expr')* ['parameters'] ['block_suite']
 */
public final class PipeFor extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pipe_for", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        CompFor.parse(t, l + 1);
        r = t.consumeToken("for");
        r = r && Targetlist.parse(t, l + 1);
        if (r) parsePipeFor4List(t, l);
        if (r) Parameters.parse(t, l + 1);
        if (r) BlockSuite.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    private static void parsePipeFor4List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!PipeFor4.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'if' 'expr'
     */
    public static final class PipeFor4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("pipe_for:4", RuleType.Conjunction, false);

        public static PipeFor4 of(ParseTreeNode node) {
            return new PipeFor4(node);
        }

        private PipeFor4(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("if");
            r = r && Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
