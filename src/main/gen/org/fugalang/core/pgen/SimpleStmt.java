package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
 */
public final class SimpleStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_stmt", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = SmallStmt.parse(t, l + 1);
        if (r) parseSimpleStmt2List(t, l);
        if (r) t.consumeToken(";");
        t.exit(r);
        return r;
    }

    private static void parseSimpleStmt2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!SimpleStmt2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ';' 'small_stmt'
     */
    public static final class SimpleStmt2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_stmt:2", RuleType.Conjunction, false);

        public static SimpleStmt2 of(ParseTreeNode node) {
            return new SimpleStmt2(node);
        }

        private SimpleStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        public SmallStmt smallStmt() {
            return SmallStmt.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(";");
            r = r && SmallStmt.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
