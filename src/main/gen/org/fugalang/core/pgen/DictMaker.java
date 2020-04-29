package org.fugalang.core.pgen;

import java.util.List;

// dict_maker: ('expr' ':' 'expr' | '**' 'bitwise_or') ('comp_for' | (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [','])
public class DictMaker {
    public final DictMaker1Group dictMaker1Group;
    public final DictMaker2Group dictMaker2Group;

    public DictMaker(
            DictMaker1Group dictMaker1Group,
            DictMaker2Group dictMaker2Group
    ) {
        this.dictMaker1Group = dictMaker1Group;
        this.dictMaker2Group = dictMaker2Group;
    }

    // 'expr' ':' 'expr' | '**' 'bitwise_or'
    public static class DictMaker1Group {
        public final DictMaker1Group1 dictMaker1Group1;
        public final DictMaker1Group2 dictMaker1Group2;

        public DictMaker1Group(
                DictMaker1Group1 dictMaker1Group1,
                DictMaker1Group2 dictMaker1Group2
        ) {
            this.dictMaker1Group1 = dictMaker1Group1;
            this.dictMaker1Group2 = dictMaker1Group2;
        }
    }

    // 'expr' ':' 'expr'
    public static class DictMaker1Group1 {
        public final Expr expr;
        public final boolean isTokenColon;
        public final Expr expr1;

        public DictMaker1Group1(
                Expr expr,
                boolean isTokenColon,
                Expr expr1
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;
        }
    }

    // '**' 'bitwise_or'
    public static class DictMaker1Group2 {
        public final boolean isTokenPower;
        public final BitwiseOr bitwiseOr;

        public DictMaker1Group2(
                boolean isTokenPower,
                BitwiseOr bitwiseOr
        ) {
            this.isTokenPower = isTokenPower;
            this.bitwiseOr = bitwiseOr;
        }
    }

    // 'comp_for' | (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
    public static class DictMaker2Group {
        public final CompFor compFor;
        public final DictMaker2Group2 dictMaker2Group2;

        public DictMaker2Group(
                CompFor compFor,
                DictMaker2Group2 dictMaker2Group2
        ) {
            this.compFor = compFor;
            this.dictMaker2Group2 = dictMaker2Group2;
        }
    }

    // (',' ('expr' ':' 'expr' | '**' 'bitwise_or'))* [',']
    public static class DictMaker2Group2 {
        public final List<DictMaker2Group21Group> dictMaker2Group21GroupList;
        public final boolean isTokenComma;

        public DictMaker2Group2(
                List<DictMaker2Group21Group> dictMaker2Group21GroupList,
                boolean isTokenComma
        ) {
            this.dictMaker2Group21GroupList = dictMaker2Group21GroupList;
            this.isTokenComma = isTokenComma;
        }
    }

    // ',' ('expr' ':' 'expr' | '**' 'bitwise_or')
    public static class DictMaker2Group21Group {
        public final boolean isTokenComma;
        public final DictMaker2Group21Group2Group dictMaker2Group21Group2Group;

        public DictMaker2Group21Group(
                boolean isTokenComma,
                DictMaker2Group21Group2Group dictMaker2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.dictMaker2Group21Group2Group = dictMaker2Group21Group2Group;
        }
    }

    // 'expr' ':' 'expr' | '**' 'bitwise_or'
    public static class DictMaker2Group21Group2Group {
        public final DictMaker2Group21Group2Group1 dictMaker2Group21Group2Group1;
        public final DictMaker2Group21Group2Group2 dictMaker2Group21Group2Group2;

        public DictMaker2Group21Group2Group(
                DictMaker2Group21Group2Group1 dictMaker2Group21Group2Group1,
                DictMaker2Group21Group2Group2 dictMaker2Group21Group2Group2
        ) {
            this.dictMaker2Group21Group2Group1 = dictMaker2Group21Group2Group1;
            this.dictMaker2Group21Group2Group2 = dictMaker2Group21Group2Group2;
        }
    }

    // 'expr' ':' 'expr'
    public static class DictMaker2Group21Group2Group1 {
        public final Expr expr;
        public final boolean isTokenColon;
        public final Expr expr1;

        public DictMaker2Group21Group2Group1(
                Expr expr,
                boolean isTokenColon,
                Expr expr1
        ) {
            this.expr = expr;
            this.isTokenColon = isTokenColon;
            this.expr1 = expr1;
        }
    }

    // '**' 'bitwise_or'
    public static class DictMaker2Group21Group2Group2 {
        public final boolean isTokenPower;
        public final BitwiseOr bitwiseOr;

        public DictMaker2Group21Group2Group2(
                boolean isTokenPower,
                BitwiseOr bitwiseOr
        ) {
            this.isTokenPower = isTokenPower;
            this.bitwiseOr = bitwiseOr;
        }
    }
}
