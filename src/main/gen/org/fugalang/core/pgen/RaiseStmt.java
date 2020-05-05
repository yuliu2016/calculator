package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * raise_stmt: 'raise' ['expr' ['from' 'expr']]
 */
public final class RaiseStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("raise_stmt", RuleType.Conjunction, true);

    public static RaiseStmt of(ParseTreeNode node) {
        return new RaiseStmt(node);
    }

    private RaiseStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenRaise(), "raise");
        addOptional(raiseStmt2());
    }

    public boolean isTokenRaise() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public RaiseStmt2 raiseStmt2() {
        var element = getItem(1);
        if (!element.isPresent(RaiseStmt2.RULE)) {
            return null;
        }
        return RaiseStmt2.of(element);
    }

    public boolean hasRaiseStmt2() {
        var element = getItem(1);
        return element.isPresent(RaiseStmt2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("raise");
        if (result) RaiseStmt2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'expr' ['from' 'expr']
     */
    public static final class RaiseStmt2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("raise_stmt:2", RuleType.Conjunction, false);

        public static RaiseStmt2 of(ParseTreeNode node) {
            return new RaiseStmt2(node);
        }

        private RaiseStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(expr());
            addOptional(raiseStmt22());
        }

        public Expr expr() {
            var element = getItem(0);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public RaiseStmt22 raiseStmt22() {
            var element = getItem(1);
            if (!element.isPresent(RaiseStmt22.RULE)) {
                return null;
            }
            return RaiseStmt22.of(element);
        }

        public boolean hasRaiseStmt22() {
            var element = getItem(1);
            return element.isPresent(RaiseStmt22.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            if (result) RaiseStmt22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'from' 'expr'
     */
    public static final class RaiseStmt22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("raise_stmt:2:2", RuleType.Conjunction, false);

        public static RaiseStmt22 of(ParseTreeNode node) {
            return new RaiseStmt22(node);
        }

        private RaiseStmt22(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenFrom(), "from");
            addRequired(expr());
        }

        public boolean isTokenFrom() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("from");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
