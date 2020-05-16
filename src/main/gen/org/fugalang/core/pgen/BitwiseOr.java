package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
 */
public final class BitwiseOr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("bitwise_or", RuleType.Conjunction);

    public static BitwiseOr of(ParseTreeNode node) {
        return new BitwiseOr(node);
    }

    private BitwiseOr(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseXor bitwiseXor() {
        return get(0, BitwiseXor::of);
    }

    public List<BitwiseOr2> bitwiseXors() {
        return getList(1, BitwiseOr2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = BitwiseXor.parse(t, lv + 1);
        if (r) parseBitwiseXors(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseBitwiseXors(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!BitwiseOr2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '|' 'bitwise_xor'
     */
    public static final class BitwiseOr2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("bitwise_or:2", RuleType.Conjunction);

        public static BitwiseOr2 of(ParseTreeNode node) {
            return new BitwiseOr2(node);
        }

        private BitwiseOr2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseXor bitwiseXor() {
            return get(1, BitwiseXor::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("|");
            r = r && BitwiseXor.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
