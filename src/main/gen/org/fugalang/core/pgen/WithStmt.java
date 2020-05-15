package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("with_stmt", RuleType.Conjunction, true);

    public static WithStmt of(ParseTreeNode node) {
        return new WithStmt(node);
    }

    private WithStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public WithItem withItem() {
        return WithItem.of(getItem(1));
    }

    public List<WithStmt3> withStmt3List() {
        return getList(2, WithStmt3::of);
    }

    public Suite suite() {
        return Suite.of(getItem(3));
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("with");
        r = r && WithItem.parse(t, l + 1);
        if (r) parseWithStmt3List(t, l);
        r = r && Suite.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    private static void parseWithStmt3List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!WithStmt3.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'with_item'
     */
    public static final class WithStmt3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("with_stmt:3", RuleType.Conjunction, false);

        public static WithStmt3 of(ParseTreeNode node) {
            return new WithStmt3(node);
        }

        private WithStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public WithItem withItem() {
            return WithItem.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && WithItem.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
