package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
 */
public final class BitwiseOr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("bitwise_or", RuleType.Conjunction, true);

    public static BitwiseOr of(ParseTreeNode node) {
        return new BitwiseOr(node);
    }

    private BitwiseOr(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseXor bitwiseXor() {
        return BitwiseXor.of(getItem(0));
    }

    public List<BitwiseOr2> bitwiseOr2List() {
        return getList(1, BitwiseOr2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = BitwiseXor.parse(t, l + 1);
        if (r) parseBitwiseOr2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseBitwiseOr2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!BitwiseOr2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '|' 'bitwise_xor'
     */
    public static final class BitwiseOr2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("bitwise_or:2", RuleType.Conjunction, false);

        public static BitwiseOr2 of(ParseTreeNode node) {
            return new BitwiseOr2(node);
        }

        private BitwiseOr2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseXor bitwiseXor() {
            return BitwiseXor.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("|");
            r = r && BitwiseXor.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
