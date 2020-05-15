package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("with_stmt", RuleType.Conjunction);

    public static WithStmt of(ParseTreeNode node) {
        return new WithStmt(node);
    }

    private WithStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public WithItem withItem() {
        return WithItem.of(get(1));
    }

    public List<WithStmt3> withStmt3List() {
        return getList(2, WithStmt3::of);
    }

    public Suite suite() {
        return Suite.of(get(3));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("with");
        r = r && WithItem.parse(t, lv + 1);
        if (r) parseWithStmt3List(t, lv);
        r = r && Suite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parseWithStmt3List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!WithStmt3.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'with_item'
     */
    public static final class WithStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("with_stmt:3", RuleType.Conjunction);

        public static WithStmt3 of(ParseTreeNode node) {
            return new WithStmt3(node);
        }

        private WithStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public WithItem withItem() {
            return WithItem.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && WithItem.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
