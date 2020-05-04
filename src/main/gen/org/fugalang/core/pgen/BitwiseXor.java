package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<BitwiseXor2> bitwiseXor2List;

    @Override
    protected void buildRule() {
        addRequired(bitwiseAnd());
        addRequired(bitwiseXor2List());
    }

    public BitwiseAnd bitwiseAnd() {
        var element = getItem(0);
        element.failIfAbsent(BitwiseAnd.RULE);
        return BitwiseAnd.of(element);
    }

    public List<BitwiseXor2> bitwiseXor2List() {
        if (bitwiseXor2List != null) {
            return bitwiseXor2List;
        }
        List<BitwiseXor2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(BitwiseXor2.of(node));
        }
        bitwiseXor2List = result == null ? Collections.emptyList() : result;
        return bitwiseXor2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseAnd.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!BitwiseXor2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
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

        @Override
        protected void buildRule() {
            addRequired(isTokenBitXor(), "^");
            addRequired(bitwiseAnd());
        }

        public boolean isTokenBitXor() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public BitwiseAnd bitwiseAnd() {
            var element = getItem(1);
            element.failIfAbsent(BitwiseAnd.RULE);
            return BitwiseAnd.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("^");
            result = result && BitwiseAnd.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
