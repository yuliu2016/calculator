package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else_suite'] ['finally_suite'] | 'finally_suite')
 */
public final class TryStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("try_stmt", RuleType.Conjunction);

    public static TryStmt of(ParseTreeNode node) {
        return new TryStmt(node);
    }

    private TryStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return Suite.of(getItem(1));
    }

    public TryStmt3 tryStmt3() {
        return TryStmt3.of(getItem(2));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("try");
        r = r && Suite.parse(t, lv + 1);
        r = r && TryStmt3.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ('except_clause' 'suite')+ ['else_suite'] ['finally_suite'] | 'finally_suite'
     */
    public static final class TryStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("try_stmt:3", RuleType.Disjunction);

        public static TryStmt3 of(ParseTreeNode node) {
            return new TryStmt3(node);
        }

        private TryStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public TryStmt31 tryStmt31() {
            return TryStmt31.of(getItem(0));
        }

        public boolean hasTryStmt31() {
            return hasItemOfRule(0, TryStmt31.RULE);
        }

        public FinallySuite finallySuite() {
            return FinallySuite.of(getItem(1));
        }

        public boolean hasFinallySuite() {
            return hasItemOfRule(1, FinallySuite.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = TryStmt31.parse(t, lv + 1);
            r = r || FinallySuite.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * ('except_clause' 'suite')+ ['else_suite'] ['finally_suite']
     */
    public static final class TryStmt31 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("try_stmt:3:1", RuleType.Conjunction);

        public static TryStmt31 of(ParseTreeNode node) {
            return new TryStmt31(node);
        }

        private TryStmt31(ParseTreeNode node) {
            super(RULE, node);
        }

        public List<TryStmt311> tryStmt311List() {
            return getList(0, TryStmt311::of);
        }

        public ElseSuite elseSuite() {
            return ElseSuite.of(getItem(1));
        }

        public boolean hasElseSuite() {
            return hasItemOfRule(1, ElseSuite.RULE);
        }

        public FinallySuite finallySuite() {
            return FinallySuite.of(getItem(2));
        }

        public boolean hasFinallySuite() {
            return hasItemOfRule(2, FinallySuite.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = parseTryStmt311List(t, lv);
            if (r) ElseSuite.parse(t, lv + 1);
            if (r) FinallySuite.parse(t, lv + 1);
            t.exit(r);
            return r;
        }

        private static boolean parseTryStmt311List(ParseTree t, int lv) {
            t.enterCollection();
            var r = TryStmt311.parse(t, lv + 1);
            if (r) while (true) {
                var p = t.position();
                if (!TryStmt311.parse(t, lv + 1)) break;
                if (t.guardLoopExit(p)) break;
            }
            t.exitCollection();
            return r;
        }
    }

    /**
     * 'except_clause' 'suite'
     */
    public static final class TryStmt311 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("try_stmt:3:1:1", RuleType.Conjunction);

        public static TryStmt311 of(ParseTreeNode node) {
            return new TryStmt311(node);
        }

        private TryStmt311(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExceptClause exceptClause() {
            return ExceptClause.of(getItem(0));
        }

        public Suite suite() {
            return Suite.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = ExceptClause.parse(t, lv + 1);
            r = r && Suite.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
