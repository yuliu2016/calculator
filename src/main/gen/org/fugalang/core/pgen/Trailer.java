package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * trailer: '(' ['arglist'] ')' | '[' 'subscript' ']' | '.' 'NAME' | 'block_suite'
 */
public final class Trailer extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("trailer", RuleType.Disjunction, true);

    public static Trailer of(ParseTreeNode node) {
        return new Trailer(node);
    }

    private Trailer(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(trailer1OrNull());
        addChoice(trailer2OrNull());
        addChoice(trailer3OrNull());
        addChoice(blockSuiteOrNull());
    }

    public Trailer1 trailer1() {
        var element = getItem(0);
        element.failIfAbsent(Trailer1.RULE);
        return Trailer1.of(element);
    }

    public Trailer1 trailer1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(Trailer1.RULE)) {
            return null;
        }
        return Trailer1.of(element);
    }

    public boolean hasTrailer1() {
        var element = getItem(0);
        return element.isPresent(Trailer1.RULE);
    }

    public Trailer2 trailer2() {
        var element = getItem(1);
        element.failIfAbsent(Trailer2.RULE);
        return Trailer2.of(element);
    }

    public Trailer2 trailer2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(Trailer2.RULE)) {
            return null;
        }
        return Trailer2.of(element);
    }

    public boolean hasTrailer2() {
        var element = getItem(1);
        return element.isPresent(Trailer2.RULE);
    }

    public Trailer3 trailer3() {
        var element = getItem(2);
        element.failIfAbsent(Trailer3.RULE);
        return Trailer3.of(element);
    }

    public Trailer3 trailer3OrNull() {
        var element = getItem(2);
        if (!element.isPresent(Trailer3.RULE)) {
            return null;
        }
        return Trailer3.of(element);
    }

    public boolean hasTrailer3() {
        var element = getItem(2);
        return element.isPresent(Trailer3.RULE);
    }

    public BlockSuite blockSuite() {
        var element = getItem(3);
        element.failIfAbsent(BlockSuite.RULE);
        return BlockSuite.of(element);
    }

    public BlockSuite blockSuiteOrNull() {
        var element = getItem(3);
        if (!element.isPresent(BlockSuite.RULE)) {
            return null;
        }
        return BlockSuite.of(element);
    }

    public boolean hasBlockSuite() {
        var element = getItem(3);
        return element.isPresent(BlockSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Trailer1.parse(parseTree, level + 1);
        result = result || Trailer2.parse(parseTree, level + 1);
        result = result || Trailer3.parse(parseTree, level + 1);
        result = result || BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '(' ['arglist'] ')'
     */
    public static final class Trailer1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("trailer:1", RuleType.Conjunction, false);

        public static Trailer1 of(ParseTreeNode node) {
            return new Trailer1(node);
        }

        private Trailer1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenLpar(), "(");
            addOptional(arglistOrNull());
            addRequired(isTokenRpar(), ")");
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Arglist arglist() {
            var element = getItem(1);
            element.failIfAbsent(Arglist.RULE);
            return Arglist.of(element);
        }

        public Arglist arglistOrNull() {
            var element = getItem(1);
            if (!element.isPresent(Arglist.RULE)) {
                return null;
            }
            return Arglist.of(element);
        }

        public boolean hasArglist() {
            var element = getItem(1);
            return element.isPresent(Arglist.RULE);
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            if (result) Arglist.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '[' 'subscript' ']'
     */
    public static final class Trailer2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("trailer:2", RuleType.Conjunction, false);

        public static Trailer2 of(ParseTreeNode node) {
            return new Trailer2(node);
        }

        private Trailer2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenLsqb(), "[");
            addRequired(subscript());
            addRequired(isTokenRsqb(), "]");
        }

        public boolean isTokenLsqb() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Subscript subscript() {
            var element = getItem(1);
            element.failIfAbsent(Subscript.RULE);
            return Subscript.of(element);
        }

        public boolean isTokenRsqb() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("[");
            result = result && Subscript.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("]");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '.' 'NAME'
     */
    public static final class Trailer3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("trailer:3", RuleType.Conjunction, false);

        public static Trailer3 of(ParseTreeNode node) {
            return new Trailer3(node);
        }

        private Trailer3(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenDot(), ".");
            addRequired(name());
        }

        public boolean isTokenDot() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(".");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
