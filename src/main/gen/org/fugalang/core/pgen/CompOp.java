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
        addChoice(isTokenLess(), "<");
        addChoice(isTokenGreater(), ">");
        addChoice(isTokenEqual(), "==");
        addChoice(isTokenMoreEqual(), ">=");
        addChoice(isTokenLessEqual(), "<=");
        addChoice(isTokenNequal(), "!=");
        addChoice(isTokenIn(), "in");
        addChoice(compOp8());
        addChoice(isTokenIs(), "is");
        addChoice(compOp10());
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
        if (!element.isPresent(CompOp8.RULE)) {
            return null;
        }
        return CompOp8.of(element);
    }

    public boolean hasCompOp8() {
        var element = getItem(7);
        return element.isPresent(CompOp8.RULE);
    }

    public boolean isTokenIs() {
        var element = getItem(8);
        return element.asBoolean();
    }

    public CompOp10 compOp10() {
        var element = getItem(9);
        if (!element.isPresent(CompOp10.RULE)) {
            return null;
        }
        return CompOp10.of(element);
    }

    public boolean hasCompOp10() {
        var element = getItem(9);
        return element.isPresent(CompOp10.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("<");
        result = result || parseTree.consumeToken(">");
        result = result || parseTree.consumeToken("==");
        result = result || parseTree.consumeToken(">=");
        result = result || parseTree.consumeToken("<=");
        result = result || parseTree.consumeToken("!=");
        result = result || parseTree.consumeToken("in");
        result = result || CompOp8.parse(parseTree, level + 1);
        result = result || parseTree.consumeToken("is");
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
            addRequired(isTokenNot(), "not");
            addRequired(isTokenIn(), "in");
        }

        public boolean isTokenNot() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public boolean isTokenIn() {
            var element = getItem(1);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("not");
            result = result && parseTree.consumeToken("in");

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
            addRequired(isTokenIs(), "is");
            addRequired(isTokenNot(), "not");
        }

        public boolean isTokenIs() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public boolean isTokenNot() {
            var element = getItem(1);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("is");
            result = result && parseTree.consumeToken("not");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
