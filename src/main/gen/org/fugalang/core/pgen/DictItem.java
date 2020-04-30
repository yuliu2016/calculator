package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
public final class DictItem extends DisjunctionRule {
    public static final String RULE_NAME = "dict_item";

    private final DictItem1 dictItem1;
    private final DictItem2 dictItem2;

    public DictItem(
            DictItem1 dictItem1,
            DictItem2 dictItem2
    ) {
        this.dictItem1 = dictItem1;
        this.dictItem2 = dictItem2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("dictItem1", dictItem1);
        addChoice("dictItem2", dictItem2);
    }

    public DictItem1 dictItem1() {
        return dictItem1;
    }

    public DictItem2 dictItem2() {
        return dictItem2;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }

    // 'expr' ':' 'expr'
    public static final class DictItem1 extends ConjunctionRule {
        public static final String RULE_NAME = "dict_item:1";

        private final Expr expr;
        private final boolean isTokenColon;
        private final Expr expr1;

        public DictItem1(
                Expr expr,
                boolean isTokenColon,
                Expr expr1
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("expr", expr);
            addRequired("isTokenColon", isTokenColon);
            addRequired("expr1", expr1);
        }

        public Expr expr() {
            return expr;
        }

        public boolean isTokenColon() {
            return isTokenColon;
        }

        public Expr expr1() {
            return expr1;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '**' 'bitwise_or'
    public static final class DictItem2 extends ConjunctionRule {
        public static final String RULE_NAME = "dict_item:2";

        private final boolean isTokenPower;
        private final BitwiseOr bitwiseOr;

        public DictItem2(
                boolean isTokenPower,
                BitwiseOr bitwiseOr
        ) {
            this.isTokenPower = isTokenPower;
            this.bitwiseOr = bitwiseOr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenPower", isTokenPower);
            addRequired("bitwiseOr", bitwiseOr);
        }

        public boolean isTokenPower() {
            return isTokenPower;
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
