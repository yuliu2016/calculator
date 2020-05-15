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
        return SmallStmt.of(getItem(0));
    }

    public List<SimpleStmt2> simpleStmt2List() {
        return getList(1, SimpleStmt2::of);
    }

    public boolean isTokenSemicolon() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = SmallStmt.parse(t, lv + 1);
        if (r) parseSimpleStmt2List(t, lv);
        if (r) t.consumeToken(";");
        t.exit(r);
        return r;
    }

    private static void parseSimpleStmt2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!SimpleStmt2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
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
            return SmallStmt.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken(";");
            r = r && SmallStmt.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
