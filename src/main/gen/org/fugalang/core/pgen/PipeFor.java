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

    public boolean isTokenFor() {
        return true;
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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        CompFor.parse(parseTree, level + 1);
        result = parseTree.consumeToken("for");
        result = result && Targetlist.parse(parseTree, level + 1);
        if (result) parsePipeFor4List(parseTree, level + 1);
        if (result) Parameters.parse(parseTree, level + 1);
        if (result) BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parsePipeFor4List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!PipeFor4.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
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

        public boolean isTokenIf() {
            return true;
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("if");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
