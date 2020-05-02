package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * pipe_expr: 'atom_expr' ('->' 'atom_expr')*
 */
public final class PipeExpr extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("pipe_expr", RuleType.Conjunction, true);

    private final AtomExpr atomExpr;
    private final List<PipeExpr2> pipeExpr2List;

    public PipeExpr(
            AtomExpr atomExpr,
            List<PipeExpr2> pipeExpr2List
    ) {
        this.atomExpr = atomExpr;
        this.pipeExpr2List = pipeExpr2List;
    }

    @Override
    protected void buildRule() {
        addRequired("atomExpr", atomExpr());
        addRequired("pipeExpr2List", pipeExpr2List());
    }

    public AtomExpr atomExpr() {
        return atomExpr;
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
    public static final class PipeExpr2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("pipe_expr:2", RuleType.Conjunction, false);

        private final boolean isTokenPipe;
        private final AtomExpr atomExpr;

        public PipeExpr2(
                boolean isTokenPipe,
                AtomExpr atomExpr
        ) {
            this.isTokenPipe = isTokenPipe;
            this.atomExpr = atomExpr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenPipe", isTokenPipe());
            addRequired("atomExpr", atomExpr());
        }

        public boolean isTokenPipe() {
            return isTokenPipe;
        }

        public AtomExpr atomExpr() {
            return atomExpr;
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
