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

    @Override
    protected void buildRule() {
        addChoice("dictItem1", dictItem1());
        addChoice("dictItem2", dictItem2());
    }

    public DictItem1 dictItem1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return DictItem1.of(element);
    }

    public boolean hasDictItem1() {
        return dictItem1() != null;
    }

    public DictItem2 dictItem2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return DictItem2.of(element);
    }

    public boolean hasDictItem2() {
        return dictItem2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired("expr", expr());
            addRequired("isTokenColon", isTokenColon());
            addRequired("expr1", expr1());
        }

        public Expr expr() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public boolean isTokenColon() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public Expr expr1() {
            var element = getItem(2);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(":");
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

        @Override
        protected void buildRule() {
            addRequired("isTokenPower", isTokenPower());
            addRequired("bitwiseOr", bitwiseOr());
        }

        public boolean isTokenPower() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public BitwiseOr bitwiseOr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return BitwiseOr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("**");
            result = result && BitwiseOr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
