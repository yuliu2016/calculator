package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
 */
public final class SimpleStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("simple_stmt", RuleType.Conjunction);

    public static SimpleStmt of(ParseTreeNode node) {
        return new SimpleStmt(node);
    }

    private SimpleStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public SmallStmt smallStmt() {
        return get(0, SmallStmt::of);
    }

    public List<SimpleStmt2> smallStmts() {
        return getList(1, SimpleStmt2::of);
    }

    public boolean isSemicolon() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = SmallStmt.parse(t, lv + 1);
        if (r) parseSmallStmts(t, lv);
        if (r) t.consume(";");
        t.exit(r);
        return r;
    }

    private static void parseSmallStmts(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!SimpleStmt2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ';' 'small_stmt'
     */
    public static final class SimpleStmt2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("simple_stmt:2", RuleType.Conjunction);

        public static SimpleStmt2 of(ParseTreeNode node) {
            return new SimpleStmt2(node);
        }

        private SimpleStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        public SmallStmt smallStmt() {
            return get(1, SmallStmt::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(";");
            r = r && SmallStmt.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
