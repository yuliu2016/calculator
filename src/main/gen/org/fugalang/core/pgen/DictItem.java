package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
public final class DictItem extends DisjunctionRule {
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
        addChoice("dictItem1", dictItem1);
        addChoice("dictItem2", dictItem2);
    }

    public DictItem1 dictItem1() {
        return dictItem1;
    }

    public DictItem2 dictItem2() {
        return dictItem2;
    }

    // 'expr' ':' 'expr'
    public static final class DictItem1 extends ConjunctionRule {
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
    }

    // '**' 'bitwise_or'
    public static final class DictItem2 extends ConjunctionRule {
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
            addRequired("isTokenPower", isTokenPower);
            addRequired("bitwiseOr", bitwiseOr);
        }

        public boolean isTokenPower() {
            return isTokenPower;
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }
    }
}
