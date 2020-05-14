package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'atom' ['**' 'factor']
 */
public final class Power extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("power", RuleType.Conjunction, true);

    public static Power of(ParseTreeNode node) {
        return new Power(node);
    }

    private Power(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom atom() {
        return Atom.of(getItem(0));
    }

    public Power2 power2() {
        return Power2.of(getItem(1));
    }

    public boolean hasPower2() {
        return hasItemOfRule(1, Power2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Atom.parse(parseTree, level + 1);
        if (result) Power2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("power:2", RuleType.Conjunction, false);

        public static Power2 of(ParseTreeNode node) {
            return new Power2(node);
        }

        private Power2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Factor factor() {
            return Factor.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("**");
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
