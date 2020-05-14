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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseAnd.parse(parseTree, level + 1);
        if (result) parseBitwiseXor2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseBitwiseXor2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseXor2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("^");
            result = result && BitwiseAnd.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
