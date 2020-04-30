package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// argument: 'NAME' ['comp_for'] | 'NAME' ':=' 'expr' | 'NAME' '=' 'expr' | '**' 'expr' | '*' 'expr'
public final class Argument extends DisjunctionRule {
    public static final String RULE_NAME = "argument";

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

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("argument1", argument1);
        addChoice("argument2", argument2);
        addChoice("argument3", argument3);
        addChoice("argument4", argument4);
        addChoice("argument5", argument5);
    }

    public Argument1 argument1() {
        return argument1;
    }

    public Argument2 argument2() {
        return argument2;
    }

    public Argument3 argument3() {
        return argument3;
    }

    public Argument4 argument4() {
        return argument4;
    }

    public Argument5 argument5() {
        return argument5;
    }

    // 'NAME' ['comp_for']
    public static final class Argument1 extends ConjunctionRule {
        public static final String RULE_NAME = "argument:1";

        private final String name;
        private final CompFor compFor;

        public Argument1(
                String name,
                CompFor compFor
        ) {
            this.name = name;
            this.compFor = compFor;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("name", name);
            addOptional("compFor", compFor);
        }

        public String name() {
            return name;
        }

        public Optional<CompFor> compFor() {
            return Optional.ofNullable(compFor);
        }
    }

    // 'NAME' ':=' 'expr'
    public static final class Argument2 extends ConjunctionRule {
        public static final String RULE_NAME = "argument:2";

        private final String name;
        private final boolean isTokenAsgnExpr;
        private final Expr expr;

        public Argument2(
                String name,
                boolean isTokenAsgnExpr,
                Expr expr
        ) {
            this.name = name;
            this.isTokenAsgnExpr = isTokenAsgnExpr;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("name", name);
            addRequired("isTokenAsgnExpr", isTokenAsgnExpr);
            addRequired("expr", expr);
        }

        public String name() {
            return name;
        }

        public boolean isTokenAsgnExpr() {
            return isTokenAsgnExpr;
        }

        public Expr expr() {
            return expr;
        }
    }

    // 'NAME' '=' 'expr'
    public static final class Argument3 extends ConjunctionRule {
        public static final String RULE_NAME = "argument:3";

        private final String name;
        private final boolean isTokenAssign;
        private final Expr expr;

        public Argument3(
                String name,
                boolean isTokenAssign,
                Expr expr
        ) {
            this.name = name;
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("name", name);
            addRequired("isTokenAssign", isTokenAssign);
            addRequired("expr", expr);
        }

        public String name() {
            return name;
        }

        public boolean isTokenAssign() {
            return isTokenAssign;
        }

        public Expr expr() {
            return expr;
        }
    }

    // '**' 'expr'
    public static final class Argument4 extends ConjunctionRule {
        public static final String RULE_NAME = "argument:4";

        private final boolean isTokenPower;
        private final Expr expr;

        public Argument4(
                boolean isTokenPower,
                Expr expr
        ) {
            this.isTokenPower = isTokenPower;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenPower", isTokenPower);
            addRequired("expr", expr);
        }

        public boolean isTokenPower() {
            return isTokenPower;
        }

        public Expr expr() {
            return expr;
        }
    }

    // '*' 'expr'
    public static final class Argument5 extends ConjunctionRule {
        public static final String RULE_NAME = "argument:5";

        private final boolean isTokenTimes;
        private final Expr expr;

        public Argument5(
                boolean isTokenTimes,
                Expr expr
        ) {
            this.isTokenTimes = isTokenTimes;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenTimes", isTokenTimes);
            addRequired("expr", expr);
        }

        public boolean isTokenTimes() {
            return isTokenTimes;
        }

        public Expr expr() {
            return expr;
        }
    }
}
