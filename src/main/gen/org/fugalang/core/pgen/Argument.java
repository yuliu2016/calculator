package org.fugalang.core.pgen;

// argument: 'NAME' ['comp_for'] | 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr'
public class Argument {
    public final Argument1 argument1;
    public final Argument2 argument2;
    public final Argument3 argument3;
    public final Argument4 argument4;
    public final Argument5 argument5;

    public Argument(
            Argument1 argument1,
            Argument2 argument2,
            Argument3 argument3,
            Argument4 argument4,
            Argument5 argument5
    ) {
        this.argument1 = argument1;
        this.argument2 = argument2;
        this.argument3 = argument3;
        this.argument4 = argument4;
        this.argument5 = argument5;
    }

    // 'NAME' ['comp_for']
    public static class Argument1 {
        public final Object name;
        public final CompFor compFor;

        public Argument1(
                Object name,
                CompFor compFor
        ) {
            this.name = name;
            this.compFor = compFor;
        }
    }

    // 'NAME' ':=' 'expr'
    public static class Argument2 {
        public final Object name;
        public final boolean isTokenAsgnExpr;
        public final Expr expr;

        public Argument2(
                Object name,
                boolean isTokenAsgnExpr,
                Expr expr
        ) {
            this.name = name;
            this.isTokenAsgnExpr = isTokenAsgnExpr;
            this.expr = expr;
        }
    }

    // 'NAME' '=' 'expr'
    public static class Argument3 {
        public final Object name;
        public final boolean isTokenAssign;
        public final Expr expr;

        public Argument3(
                Object name,
                boolean isTokenAssign,
                Expr expr
        ) {
            this.name = name;
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }
    }

    // '**' 'expr'
    public static class Argument4 {
        public final boolean isTokenPower;
        public final Expr expr;

        public Argument4(
                boolean isTokenPower,
                Expr expr
        ) {
            this.isTokenPower = isTokenPower;
            this.expr = expr;
        }
    }

    // '*' 'expr'
    public static class Argument5 {
        public final boolean isTokenTimes;
        public final Expr expr;

        public Argument5(
                boolean isTokenTimes,
                Expr expr
        ) {
            this.isTokenTimes = isTokenTimes;
            this.expr = expr;
        }
    }
}
