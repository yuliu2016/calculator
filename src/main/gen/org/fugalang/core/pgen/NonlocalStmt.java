package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * nonlocal_stmt: 'nonlocal' 'NAME' (',' 'NAME')*
 */
public final class NonlocalStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("nonlocal_stmt", RuleType.Conjunction);

    public static NonlocalStmt of(ParseTreeNode node) {
        return new NonlocalStmt(node);
    }

    private NonlocalStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public List<NonlocalStmt3> names() {
        return getList(2, NonlocalStmt3::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("nonlocal");
        r = r && t.consume(TokenType.NAME);
        if (r) parseNames(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseNames(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!NonlocalStmt3.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'NAME'
     */
    public static final class NonlocalStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("nonlocal_stmt:3", RuleType.Conjunction);

        public static NonlocalStmt3 of(ParseTreeNode node) {
            return new NonlocalStmt3(node);
        }

        private NonlocalStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && t.consume(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}
