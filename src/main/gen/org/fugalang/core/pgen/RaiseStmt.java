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

    public RaiseStmt2 raiseStmt2() {
        return RaiseStmt2.of(getItem(1));
    }

    public boolean hasRaiseStmt2() {
        return hasItemOfRule(1, RaiseStmt2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("raise");
        if (result) RaiseStmt2.parse(parseTree, level + 1);

        parseTree.exit(result);
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

        public Expr expr() {
            return Expr.of(getItem(0));
        }

        public RaiseStmt22 raiseStmt22() {
            return RaiseStmt22.of(getItem(1));
        }

        public boolean hasRaiseStmt22() {
            return hasItemOfRule(1, RaiseStmt22.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            if (result) RaiseStmt22.parse(parseTree, level + 1);

            parseTree.exit(result);
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

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("from");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
