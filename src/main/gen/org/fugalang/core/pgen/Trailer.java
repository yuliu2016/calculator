package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * trailer: '(' ['arglist'] ')' | '[' 'subscriptlist' ']' | '.' 'NAME' | 'block_suite'
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
        addChoice("trailer1", trailer1());
        addChoice("trailer2", trailer2());
        addChoice("trailer3", trailer3());
        addChoice("blockSuite", blockSuite());
    }

    public Trailer1 trailer1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Trailer1.of(element);
    }

    public boolean hasTrailer1() {
        return trailer1() != null;
    }

    public Trailer2 trailer2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Trailer2.of(element);
    }

    public boolean hasTrailer2() {
        return trailer2() != null;
    }

    public Trailer3 trailer3() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return Trailer3.of(element);
    }

    public boolean hasTrailer3() {
        return trailer3() != null;
    }

    public BlockSuite blockSuite() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return BlockSuite.of(element);
    }

    public boolean hasBlockSuite() {
        return blockSuite() != null;
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
            addRequired("isTokenLpar", isTokenLpar());
            addOptional("arglist", arglist());
            addRequired("isTokenRpar", isTokenRpar());
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Arglist arglist() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Arglist.of(element);
        }

        public boolean hasArglist() {
            return arglist() != null;
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("(");
            Arglist.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '[' 'subscriptlist' ']'
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
            addRequired("isTokenLsqb", isTokenLsqb());
            addRequired("subscriptlist", subscriptlist());
            addRequired("isTokenRsqb", isTokenRsqb());
        }

        public boolean isTokenLsqb() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Subscriptlist subscriptlist() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Subscriptlist.of(element);
        }

        public boolean isTokenRsqb() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("[");
            result = result && Subscriptlist.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("]");

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
            addRequired("isTokenDot", isTokenDot());
            addRequired("name", name());
        }

        public boolean isTokenDot() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return (String) element.asObject();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(".");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
