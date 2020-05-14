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

    public Atom1 atom1() {
        return Atom1.of(getItem(0));
    }

    public boolean hasAtom1() {
        return hasItemOfRule(0, Atom1.RULE);
    }

    public String number() {
        return getItemOfType(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return hasItemOfType(1, TokenType.NUMBER);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Atom1.parse(parseTree, level + 1);
        result = result || parseTree.consumeToken(TokenType.NUMBER);

        parseTree.exit(result);
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

        public Sum sum() {
            return Sum.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            result = result && Sum.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(result);
            return result;
        }
    }
}
