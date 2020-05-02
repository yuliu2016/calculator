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

    private List<BitwiseOr2> bitwiseOr2List;

    @Override
    protected void buildRule() {
        addRequired("bitwiseXor", bitwiseXor());
        addRequired("bitwiseOr2List", bitwiseOr2List());
    }

    public BitwiseXor bitwiseXor() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return BitwiseXor.of(element);
    }

    public List<BitwiseOr2> bitwiseOr2List() {
        return bitwiseOr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseXor.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseOr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
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

        @Override
        protected void buildRule() {
            addRequired("isTokenBitOr", isTokenBitOr());
            addRequired("bitwiseXor", bitwiseXor());
        }

        public boolean isTokenBitOr() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public BitwiseXor bitwiseXor() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return BitwiseXor.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("|");
            result = result && BitwiseXor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
