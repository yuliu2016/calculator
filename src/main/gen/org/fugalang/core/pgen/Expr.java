package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// expr: 'if' 'disjunction' '?' 'disjunction' 'else' 'expr' | 'disjunction' | 'funcdef'
public final class Expr extends DisjunctionRule {
    public static final String RULE_NAME = "expr";

    private final Expr1 expr1;
    private final Disjunction disjunction;
    private final Funcdef funcdef;

    public Expr(
            Expr1 expr1,
            Disjunction disjunction,
            Funcdef funcdef
    ) {
        this.expr1 = expr1;
        this.disjunction = disjunction;
        this.funcdef = funcdef;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("expr1", expr1);
        addChoice("disjunction", disjunction);
        addChoice("funcdef", funcdef);
    }

    public Expr1 expr1() {
        return expr1;
    }

    public Disjunction disjunction() {
        return disjunction;
    }

    public Funcdef funcdef() {
        return funcdef;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Expr1.parse(parseTree, level + 1);
        result = result || Disjunction.parse(parseTree, level + 1);
        result = result || Funcdef.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
    public static final class Expr1 extends ConjunctionRule {
        public static final String RULE_NAME = "expr:1";

        private final boolean isTokenIf;
        private final Disjunction disjunction;
        private final boolean isTokenTernery;
        private final Disjunction disjunction1;
        private final boolean isTokenElse;
        private final Expr expr;

        public Expr1(
                boolean isTokenIf,
                Disjunction disjunction,
                boolean isTokenTernery,
                Disjunction disjunction1,
                boolean isTokenElse,
                Expr expr
        ) {
            this.isTokenIf = isTokenIf;
            this.disjunction = disjunction;
            this.isTokenTernery = isTokenTernery;
            this.disjunction1 = disjunction1;
            this.isTokenElse = isTokenElse;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenIf", isTokenIf);
            addRequired("disjunction", disjunction);
            addRequired("isTokenTernery", isTokenTernery);
            addRequired("disjunction1", disjunction1);
            addRequired("isTokenElse", isTokenElse);
            addRequired("expr", expr);
        }

        public boolean isTokenIf() {
            return isTokenIf;
        }

        public Disjunction disjunction() {
            return disjunction;
        }

        public boolean isTokenTernery() {
            return isTokenTernery;
        }

        public Disjunction disjunction1() {
            return disjunction1;
        }

        public boolean isTokenElse() {
            return isTokenElse;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("if");
            result = result && Disjunction.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("?");
            result = result && Disjunction.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("else");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
