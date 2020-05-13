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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseXor.parse(parseTree, level + 1);
        if (result) parseBitwiseOr2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseBitwiseOr2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseOr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
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

        public boolean isTokenBitOr() {
            return true;
        }

        public BitwiseXor bitwiseXor() {
            return BitwiseXor.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("|");
            result = result && BitwiseXor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
