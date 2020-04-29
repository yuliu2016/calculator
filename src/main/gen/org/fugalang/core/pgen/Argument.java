package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// argument: 'NAME' ['comp_for'] | 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr'
public final class Argument extends DisjunctionRule {
    private final Argument1 argument1;
    private final Argument2 argument2;
    private final Argument3 argument3;
    private final Argument4 argument4;
    private final Argument5 argument5;

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

    public Argument1 getArgument1() {
        return argument1;
    }

    public Argument2 getArgument2() {
        return argument2;
    }

    public Argument3 getArgument3() {
        return argument3;
    }

    public Argument4 getArgument4() {
        return argument4;
    }

    public Argument5 getArgument5() {
        return argument5;
    }

    // 'NAME' ['comp_for']
    public static final class Argument1 extends ConjunctionRule {
        private final Object name;
        private final CompFor compFor;

        public Argument1(
                Object name,
                CompFor compFor
        ) {
            this.name = name;
            this.compFor = compFor;
        }

        public Object getName() {
            return name;
        }

        public Optional<CompFor> getCompFor() {
            return Optional.ofNullable(compFor);
        }
    }

    // 'NAME' ':=' 'expr'
    public static final class Argument2 extends ConjunctionRule {
        private final Object name;
        private final boolean isTokenAsgnExpr;
        private final Expr expr;

        public Argument2(
                Object name,
                boolean isTokenAsgnExpr,
                Expr expr
        ) {
            this.name = name;
            this.isTokenAsgnExpr = isTokenAsgnExpr;
            this.expr = expr;
        }

        public Object getName() {
            return name;
        }

        public boolean getIsTokenAsgnExpr() {
            return isTokenAsgnExpr;
        }

        public Expr getExpr() {
            return expr;
        }
    }

    // 'NAME' '=' 'expr'
    public static final class Argument3 extends ConjunctionRule {
        private final Object name;
        private final boolean isTokenAssign;
        private final Expr expr;

        public Argument3(
                Object name,
                boolean isTokenAssign,
                Expr expr
        ) {
            this.name = name;
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }

        public Object getName() {
            return name;
        }

        public boolean getIsTokenAssign() {
            return isTokenAssign;
        }

        public Expr getExpr() {
            return expr;
        }
    }

    // '**' 'expr'
    public static final class Argument4 extends ConjunctionRule {
        private final boolean isTokenPower;
        private final Expr expr;

        public Argument4(
                boolean isTokenPower,
                Expr expr
        ) {
            this.isTokenPower = isTokenPower;
            this.expr = expr;
        }

        public boolean getIsTokenPower() {
            return isTokenPower;
        }

        public Expr getExpr() {
            return expr;
        }
    }

    // '*' 'expr'
    public static final class Argument5 extends ConjunctionRule {
        private final boolean isTokenTimes;
        private final Expr expr;

        public Argument5(
                boolean isTokenTimes,
                Expr expr
        ) {
            this.isTokenTimes = isTokenTimes;
            this.expr = expr;
        }

        public boolean getIsTokenTimes() {
            return isTokenTimes;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}
