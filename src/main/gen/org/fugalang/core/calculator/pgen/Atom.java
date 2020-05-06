package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * atom: '(' 'sum' ')' | 'NUMBER'
 */
public final class Atom extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("atom", RuleType.Disjunction, true);

    public static Atom of(ParseTreeNode node) {
        return new Atom(node);
    }

    private Atom(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(atom1OrNull());
        addChoice(numberOrNull());
    }

    public Atom1 atom1() {
        var element = getItem(0);
        element.failIfAbsent(Atom1.RULE);
        return Atom1.of(element);
    }

    public Atom1 atom1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(Atom1.RULE)) {
            return null;
        }
        return Atom1.of(element);
    }

    public boolean hasAtom1() {
        var element = getItem(0);
        return element.isPresent(Atom1.RULE);
    }

    public String number() {
        var element = getItem(1);
        element.failIfAbsent(TokenType.NUMBER);
        return element.asString();
    }

    public String numberOrNull() {
        var element = getItem(1);
        if (!element.isPresent(TokenType.NUMBER)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasNumber() {
        var element = getItem(1);
        return element.isPresent(TokenType.NUMBER);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Atom1.parse(parseTree, level + 1);
        result = result || parseTree.consumeToken(TokenType.NUMBER);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '(' 'sum' ')'
     */
    public static final class Atom1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("atom:1", RuleType.Conjunction, false);

        public static Atom1 of(ParseTreeNode node) {
            return new Atom1(node);
        }

        private Atom1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenLpar(), "(");
            addRequired(sum());
            addRequired(isTokenRpar(), ")");
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Sum sum() {
            var element = getItem(1);
            element.failIfAbsent(Sum.RULE);
            return Sum.of(element);
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
            result = result && Sum.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
