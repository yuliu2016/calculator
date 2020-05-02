package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
 */
public final class CompOp extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_op", RuleType.Disjunction, true);

    public static CompOp of(ParseTreeNode node) {
        return new CompOp(node);
    }

    private CompOp(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice("isTokenLess", isTokenLess());
        addChoice("isTokenGreater", isTokenGreater());
        addChoice("isTokenEqual", isTokenEqual());
        addChoice("isTokenMoreEqual", isTokenMoreEqual());
        addChoice("isTokenLessEqual", isTokenLessEqual());
        addChoice("isTokenNequal", isTokenNequal());
        addChoice("isTokenIn", isTokenIn());
        addChoice("compOp8", compOp8());
        addChoice("isTokenIs", isTokenIs());
        addChoice("compOp10", compOp10());
    }

    public boolean isTokenLess() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public boolean isTokenGreater() {
        var element = getItem(1);
        return element.asBoolean();
    }

    public boolean isTokenEqual() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public boolean isTokenMoreEqual() {
        var element = getItem(3);
        return element.asBoolean();
    }

    public boolean isTokenLessEqual() {
        var element = getItem(4);
        return element.asBoolean();
    }

    public boolean isTokenNequal() {
        var element = getItem(5);
        return element.asBoolean();
    }

    public boolean isTokenIn() {
        var element = getItem(6);
        return element.asBoolean();
    }

    public CompOp8 compOp8() {
        var element = getItem(7);
        if (!element.isPresent()) return null;
        return CompOp8.of(element);
    }

    public boolean hasCompOp8() {
        return compOp8() != null;
    }

    public boolean isTokenIs() {
        var element = getItem(8);
        return element.asBoolean();
    }

    public CompOp10 compOp10() {
        var element = getItem(9);
        if (!element.isPresent()) return null;
        return CompOp10.of(element);
    }

    public boolean hasCompOp10() {
        return compOp10() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("<");
        result = result || parseTree.consumeTokenLiteral(">");
        result = result || parseTree.consumeTokenLiteral("==");
        result = result || parseTree.consumeTokenLiteral(">=");
        result = result || parseTree.consumeTokenLiteral("<=");
        result = result || parseTree.consumeTokenLiteral("!=");
        result = result || parseTree.consumeTokenLiteral("in");
        result = result || CompOp8.parse(parseTree, level + 1);
        result = result || parseTree.consumeTokenLiteral("is");
        result = result || CompOp10.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'not' 'in'
     */
    public static final class CompOp8 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("comp_op:8", RuleType.Conjunction, false);

        public static CompOp8 of(ParseTreeNode node) {
            return new CompOp8(node);
        }

        private CompOp8(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenNot", isTokenNot());
            addRequired("isTokenIn", isTokenIn());
        }

        public boolean isTokenNot() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenIn() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("not");
            result = result && parseTree.consumeTokenLiteral("in");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'is' 'not'
     */
    public static final class CompOp10 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("comp_op:10", RuleType.Conjunction, false);

        public static CompOp10 of(ParseTreeNode node) {
            return new CompOp10(node);
        }

        private CompOp10(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenIs", isTokenIs());
            addRequired("isTokenNot", isTokenNot());
        }

        public boolean isTokenIs() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenNot() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("is");
            result = result && parseTree.consumeTokenLiteral("not");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
