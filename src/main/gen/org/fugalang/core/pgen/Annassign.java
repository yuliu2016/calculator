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

    @Override
    protected void buildRule() {
        addRequired(isTokenColon(), ":");
        addRequired(expr());
        addOptional(annassign3OrNull());
    }

    public boolean isTokenColon() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Expr expr() {
        var element = getItem(1);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public Annassign3 annassign3() {
        var element = getItem(2);
        element.failIfAbsent(Annassign3.RULE);
        return Annassign3.of(element);
    }

    public Annassign3 annassign3OrNull() {
        var element = getItem(2);
        if (!element.isPresent(Annassign3.RULE)) {
            return null;
        }
        return Annassign3.of(element);
    }

    public boolean hasAnnassign3() {
        var element = getItem(2);
        return element.isPresent(Annassign3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenAssign(), "=");
            addRequired(exprlistStar());
        }

        public boolean isTokenAssign() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ExprlistStar exprlistStar() {
            var element = getItem(1);
            element.failIfAbsent(ExprlistStar.RULE);
            return ExprlistStar.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("=");
            result = result && ExprlistStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
