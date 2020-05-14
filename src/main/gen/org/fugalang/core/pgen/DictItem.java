package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
 */
public final class DictItem extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dict_item", RuleType.Disjunction, true);

    public static DictItem of(ParseTreeNode node) {
        return new DictItem(node);
    }

    private DictItem(ParseTreeNode node) {
        super(RULE, node);
    }

    public DictItem1 dictItem1() {
        return DictItem1.of(getItem(0));
    }

    public boolean hasDictItem1() {
        return hasItemOfRule(0, DictItem1.RULE);
    }

    public DictItem2 dictItem2() {
        return DictItem2.of(getItem(1));
    }

    public boolean hasDictItem2() {
        return hasItemOfRule(1, DictItem2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DictItem1.parse(parseTree, level + 1);
        result = result || DictItem2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'expr' ':' 'expr'
     */
    public static final class DictItem1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dict_item:1", RuleType.Conjunction, false);

        public static DictItem1 of(ParseTreeNode node) {
            return new DictItem1(node);
        }

        private DictItem1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(0));
        }

        public Expr expr1() {
            return Expr.of(getItem(2));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(":");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '**' 'bitwise_or'
     */
    public static final class DictItem2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dict_item:2", RuleType.Conjunction, false);

        public static DictItem2 of(ParseTreeNode node) {
            return new DictItem2(node);
        }

        private DictItem2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseOr bitwiseOr() {
            return BitwiseOr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("**");
            result = result && BitwiseOr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
