package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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
        addRequired(bitwiseXor());
        addRequired(bitwiseOr2List());
    }

    public BitwiseXor bitwiseXor() {
        var element = getItem(0);
        element.failIfAbsent(BitwiseXor.RULE);
        return BitwiseXor.of(element);
    }

    public List<BitwiseOr2> bitwiseOr2List() {
        if (bitwiseOr2List != null) {
            return bitwiseOr2List;
        }
        List<BitwiseOr2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(BitwiseOr2.of(node));
        }
        bitwiseOr2List = result == null ? Collections.emptyList() : result;
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
            addRequired(isTokenBitOr(), "|");
            addRequired(bitwiseXor());
        }

        public boolean isTokenBitOr() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public BitwiseXor bitwiseXor() {
            var element = getItem(1);
            element.failIfAbsent(BitwiseXor.RULE);
            return BitwiseXor.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("|");
            result = result && BitwiseXor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
