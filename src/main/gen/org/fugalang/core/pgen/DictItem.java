package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// dict_item: ('expr' ':' 'expr' | '**' 'bitwise_or')
public final class DictItem extends ConjunctionRule {
    private final DictItemGroup dictItemGroup;

    public DictItem(
            DictItemGroup dictItemGroup
    ) {
        this.dictItemGroup = dictItemGroup;

        addRequired("dictItemGroup", dictItemGroup);
    }

    public DictItemGroup dictItemGroup() {
        return dictItemGroup;
    }

    // 'expr' ':' 'expr' | '**' 'bitwise_or'
    public static final class DictItemGroup extends DisjunctionRule {
        private final DictItemGroup1 dictItemGroup1;
        private final DictItemGroup2 dictItemGroup2;

        public DictItemGroup(
                DictItemGroup1 dictItemGroup1,
                DictItemGroup2 dictItemGroup2
        ) {
            this.dictItemGroup1 = dictItemGroup1;
            this.dictItemGroup2 = dictItemGroup2;

            addChoice("dictItemGroup1", dictItemGroup1);
            addChoice("dictItemGroup2", dictItemGroup2);
        }

        public DictItemGroup1 dictItemGroup1() {
            return dictItemGroup1;
        }

        public DictItemGroup2 dictItemGroup2() {
            return dictItemGroup2;
        }
    }

    // 'expr' ':' 'expr'
    public static final class DictItemGroup1 extends ConjunctionRule {
        private final Expr expr;
        private final boolean isTokenColon;
        private final Expr expr1;

        public DictItemGroup1(
                Expr expr,
                boolean isTokenColon,
                Expr expr1
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;

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
    public static final class DictItemGroup2 extends ConjunctionRule {
        private final boolean isTokenPower;
        private final BitwiseOr bitwiseOr;

        public DictItemGroup2(
                boolean isTokenPower,
                BitwiseOr bitwiseOr
        ) {
            this.isTokenPower = isTokenPower;
            this.bitwiseOr = bitwiseOr;

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
