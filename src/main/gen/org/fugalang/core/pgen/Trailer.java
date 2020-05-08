package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * trailer: '.' 'NAME' | 'parameters' | 'subscript'
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
        addChoice(parametersOrNull());
        addChoice(subscriptOrNull());
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

    public Parameters parameters() {
        var element = getItem(1);
        element.failIfAbsent(Parameters.RULE);
        return Parameters.of(element);
    }

    public Parameters parametersOrNull() {
        var element = getItem(1);
        if (!element.isPresent(Parameters.RULE)) {
            return null;
        }
        return Parameters.of(element);
    }

    public boolean hasParameters() {
        var element = getItem(1);
        return element.isPresent(Parameters.RULE);
    }

    public Subscript subscript() {
        var element = getItem(2);
        element.failIfAbsent(Subscript.RULE);
        return Subscript.of(element);
    }

    public Subscript subscriptOrNull() {
        var element = getItem(2);
        if (!element.isPresent(Subscript.RULE)) {
            return null;
        }
        return Subscript.of(element);
    }

    public boolean hasSubscript() {
        var element = getItem(2);
        return element.isPresent(Subscript.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Trailer1.parse(parseTree, level + 1);
        result = result || Parameters.parse(parseTree, level + 1);
        result = result || Subscript.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '.' 'NAME'
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
