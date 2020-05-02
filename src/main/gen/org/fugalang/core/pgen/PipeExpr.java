package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * pipe_expr: 'atom_expr' ('->' 'atom_expr')*
 */
public final class PipeExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pipe_expr", RuleType.Conjunction, true);

    public static PipeExpr of(ParseTreeNode node) {
        return new PipeExpr(node);
    }

    private PipeExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<PipeExpr2> pipeExpr2List;

    @Override
    protected void buildRule() {
        addRequired("atomExpr", atomExpr());
        addRequired("pipeExpr2List", pipeExpr2List());
    }

    public AtomExpr atomExpr() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return AtomExpr.of(element);
    }

    public List<PipeExpr2> pipeExpr2List() {
        return pipeExpr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = AtomExpr.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!PipeExpr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '->' 'atom_expr'
     */
    public static final class PipeExpr2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("pipe_expr:2", RuleType.Conjunction, false);

        public static PipeExpr2 of(ParseTreeNode node) {
            return new PipeExpr2(node);
        }

        private PipeExpr2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenPipe", isTokenPipe());
            addRequired("atomExpr", atomExpr());
        }

        public boolean isTokenPipe() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public AtomExpr atomExpr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return AtomExpr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("->");
            result = result && AtomExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
