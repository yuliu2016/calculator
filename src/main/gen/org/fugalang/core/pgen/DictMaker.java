package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// dict_maker: ('expr' ':' 'expr' | '**' 'bitwise_or') ('comp_for' | (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [','])
public final class DictMaker extends ConjunctionRule {
    private final DictMaker1Group dictMaker1Group;
    private final DictMaker2Group dictMaker2Group;

    public DictMaker(
            DictMaker1Group dictMaker1Group,
            DictMaker2Group dictMaker2Group
    ) {
        this.dictMaker1Group = dictMaker1Group;
        this.dictMaker2Group = dictMaker2Group;
    }

    public DictMaker1Group getDictMaker1Group() {
        return dictMaker1Group;
    }

    public DictMaker2Group getDictMaker2Group() {
        return dictMaker2Group;
    }

    // 'expr' ':' 'expr' | '**' 'bitwise_or'
    public static final class DictMaker1Group extends DisjunctionRule {
        private final DictMaker1Group1 dictMaker1Group1;
        private final DictMaker1Group2 dictMaker1Group2;

        public DictMaker1Group(
                DictMaker1Group1 dictMaker1Group1,
                DictMaker1Group2 dictMaker1Group2
        ) {
            this.dictMaker1Group1 = dictMaker1Group1;
            this.dictMaker1Group2 = dictMaker1Group2;
        }

        public DictMaker1Group1 getDictMaker1Group1() {
            return dictMaker1Group1;
        }

        public DictMaker1Group2 getDictMaker1Group2() {
            return dictMaker1Group2;
        }
    }

    // 'expr' ':' 'expr'
    public static final class DictMaker1Group1 extends ConjunctionRule {
        private final Expr expr;
        private final boolean isTokenColon;
        private final Expr expr1;

        public DictMaker1Group1(
                Expr expr,
                boolean isTokenColon,
                Expr expr1
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;
        }

        public Expr getExpr() {
            return expr;
        }

        public boolean getIsTokenColon() {
            return isTokenColon;
        }

        public Expr getExpr1() {
            return expr1;
        }
    }

    // '**' 'bitwise_or'
    public static final class DictMaker1Group2 extends ConjunctionRule {
        private final boolean isTokenPower;
        private final BitwiseOr bitwiseOr;

        public DictMaker1Group2(
                boolean isTokenPower,
                BitwiseOr bitwiseOr
        ) {
            this.isTokenPower = isTokenPower;
            this.bitwiseOr = bitwiseOr;
        }

        public boolean getIsTokenPower() {
            return isTokenPower;
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }
    }

    // 'comp_for' | (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
    public static final class DictMaker2Group extends DisjunctionRule {
        private final CompFor compFor;
        private final DictMaker2Group2 dictMaker2Group2;

        public DictMaker2Group(
                CompFor compFor,
                DictMaker2Group2 dictMaker2Group2
        ) {
            this.compFor = compFor;
            this.dictMaker2Group2 = dictMaker2Group2;
        }

        public CompFor getCompFor() {
            return compFor;
        }

        public DictMaker2Group2 getDictMaker2Group2() {
            return dictMaker2Group2;
        }
    }

    // (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
    public static final class DictMaker2Group2 extends ConjunctionRule {
        private final List<DictMaker2Group21Group> dictMaker2Group21GroupList;
        private final boolean isTokenComma;

        public DictMaker2Group2(
                List<DictMaker2Group21Group> dictMaker2Group21GroupList,
                boolean isTokenComma
        ) {
            this.dictMaker2Group21GroupList = dictMaker2Group21GroupList;
            this.isTokenComma = isTokenComma;
        }

        public List<DictMaker2Group21Group> getDictMaker2Group21GroupList() {
            return dictMaker2Group21GroupList;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }
    }

    // ',' ('expr' ':' 'expr' | '**' 'bitwise_or')
    public static final class DictMaker2Group21Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final DictMaker2Group21Group2Group dictMaker2Group21Group2Group;

        public DictMaker2Group21Group(
                boolean isTokenComma,
                DictMaker2Group21Group2Group dictMaker2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.dictMaker2Group21Group2Group = dictMaker2Group21Group2Group;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public DictMaker2Group21Group2Group getDictMaker2Group21Group2Group() {
            return dictMaker2Group21Group2Group;
        }
    }

    // 'expr' ':' 'expr' | '**' 'bitwise_or'
    public static final class DictMaker2Group21Group2Group extends DisjunctionRule {
        private final DictMaker2Group21Group2Group1 dictMaker2Group21Group2Group1;
        private final DictMaker2Group21Group2Group2 dictMaker2Group21Group2Group2;

        public DictMaker2Group21Group2Group(
                DictMaker2Group21Group2Group1 dictMaker2Group21Group2Group1,
                DictMaker2Group21Group2Group2 dictMaker2Group21Group2Group2
        ) {
            this.dictMaker2Group21Group2Group1 = dictMaker2Group21Group2Group1;
            this.dictMaker2Group21Group2Group2 = dictMaker2Group21Group2Group2;
        }

        public DictMaker2Group21Group2Group1 getDictMaker2Group21Group2Group1() {
            return dictMaker2Group21Group2Group1;
        }

        public DictMaker2Group21Group2Group2 getDictMaker2Group21Group2Group2() {
            return dictMaker2Group21Group2Group2;
        }
    }

    // 'expr' ':' 'expr'
    public static final class DictMaker2Group21Group2Group1 extends ConjunctionRule {
        private final Expr expr;
        private final boolean isTokenColon;
        private final Expr expr1;

        public DictMaker2Group21Group2Group1(
                Expr expr,
                boolean isTokenColon,
                Expr expr1
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;
        }

        public Expr getExpr() {
            return expr;
        }

        public boolean getIsTokenColon() {
            return isTokenColon;
        }

        public Expr getExpr1() {
            return expr1;
        }
    }

    // '**' 'bitwise_or'
    public static final class DictMaker2Group21Group2Group2 extends ConjunctionRule {
        private final boolean isTokenPower;
        private final BitwiseOr bitwiseOr;

        public DictMaker2Group21Group2Group2(
                boolean isTokenPower,
                BitwiseOr bitwiseOr
        ) {
            this.isTokenPower = isTokenPower;
            this.bitwiseOr = bitwiseOr;
        }

        public boolean getIsTokenPower() {
            return isTokenPower;
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }
    }
}
