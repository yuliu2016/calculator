package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist: 'expr' (',' 'expr')* [',']
 */
public final class Exprlist extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("exprlist", RuleType.Conjunction, true);

    private final Expr expr;
    private final List<Exprlist2> exprlist2List;
    private final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            List<Exprlist2> exprlist2List,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2List = exprlist2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("expr", expr());
        addRequired("exprlist2List", exprlist2List());
        addRequired("isTokenComma", isTokenComma());
    }

    public Expr expr() {
        return expr;
    }

    public List<Exprlist2> exprlist2List() {
        return exprlist2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Exprlist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'expr'
     */
    public static final class Exprlist2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("exprlist:2", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final Expr expr;

        public Exprlist2(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("expr", expr());
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
