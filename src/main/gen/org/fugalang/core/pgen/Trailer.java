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

    public Trailer1 trailer1() {
        return Trailer1.of(getItem(0));
    }

    public boolean hasTrailer1() {
        return hasItemOfRule(0, Trailer1.RULE);
    }

    public Parameters parameters() {
        return Parameters.of(getItem(1));
    }

    public boolean hasParameters() {
        return hasItemOfRule(1, Parameters.RULE);
    }

    public Subscript subscript() {
        return Subscript.of(getItem(2));
    }

    public boolean hasSubscript() {
        return hasItemOfRule(2, Subscript.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public boolean isTokenDot() {
            return true;
        }

        public String name() {
            return getItemOfType(1,TokenType.NAME);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(".");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
