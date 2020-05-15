package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
 */
public final class BitwiseXor extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("bitwise_xor", RuleType.Conjunction, true);

    public static BitwiseXor of(ParseTreeNode node) {
        return new BitwiseXor(node);
    }

    private BitwiseXor(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseAnd bitwiseAnd() {
        return BitwiseAnd.of(getItem(0));
    }

    public List<BitwiseXor2> bitwiseXor2List() {
        return getList(1, BitwiseXor2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = BitwiseAnd.parse(t, l + 1);
        if (r) parseBitwiseXor2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseBitwiseXor2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!BitwiseXor2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '^' 'bitwise_and'
     */
    public static final class BitwiseXor2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("bitwise_xor:2", RuleType.Conjunction, false);

        public static BitwiseXor2 of(ParseTreeNode node) {
            return new BitwiseXor2(node);
        }

        private BitwiseXor2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseAnd bitwiseAnd() {
            return BitwiseAnd.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("^");
            r = r && BitwiseAnd.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
