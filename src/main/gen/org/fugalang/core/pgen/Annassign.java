package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * annassign: ':' 'expr' ['=' 'exprlist_star']
 */
public final class Annassign extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("annassign", RuleType.Conjunction, true);

    public static Annassign of(ParseTreeNode node) {
        return new Annassign(node);
    }

    private Annassign(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public Annassign3 annassign3() {
        return Annassign3.of(getItem(2));
    }

    public boolean hasAnnassign3() {
        return hasItemOfRule(2, Annassign3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(":");
        result = result && Expr.parse(parseTree, level + 1);
        if (result) Annassign3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '=' 'exprlist_star'
     */
    public static final class Annassign3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("annassign:3", RuleType.Conjunction, false);

        public static Annassign3 of(ParseTreeNode node) {
            return new Annassign3(node);
        }

        private Annassign3(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprlistStar exprlistStar() {
            return ExprlistStar.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("=");
            result = result && ExprlistStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
