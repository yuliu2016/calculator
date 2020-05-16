package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
 */
public final class BitwiseXor extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("bitwise_xor", RuleType.Conjunction);

    public static BitwiseXor of(ParseTreeNode node) {
        return new BitwiseXor(node);
    }

    private BitwiseXor(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseAnd bitwiseAnd() {
        return get(0, BitwiseAnd::of);
    }

    public List<BitwiseXor2> bitwiseAnds() {
        return getList(1, BitwiseXor2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = BitwiseAnd.parse(t, lv + 1);
        if (r) parseBitwiseAnds(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseBitwiseAnds(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!BitwiseXor2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '^' 'bitwise_and'
     */
    public static final class BitwiseXor2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("bitwise_xor:2", RuleType.Conjunction);

        public static BitwiseXor2 of(ParseTreeNode node) {
            return new BitwiseXor2(node);
        }

        private BitwiseXor2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseAnd bitwiseAnd() {
            return get(1, BitwiseAnd::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("^");
            r = r && BitwiseAnd.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
