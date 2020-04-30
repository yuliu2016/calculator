package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// except_clause: 'except' ['expr' ['as' 'NAME']]
public final class ExceptClause extends ConjunctionRule {
    public static final String RULE_NAME = "except_clause";

    private final boolean isTokenExcept;
    private final ExceptClause2 exceptClause2;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2 exceptClause2
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2 = exceptClause2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenExcept", isTokenExcept);
        addOptional("exceptClause2", exceptClause2);
    }

    public boolean isTokenExcept() {
        return isTokenExcept;
    }

    public Optional<ExceptClause2> exceptClause2() {
        return Optional.ofNullable(exceptClause2);
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

    // 'expr' ['as' 'NAME']
    public static final class ExceptClause2 extends ConjunctionRule {
        public static final String RULE_NAME = "except_clause:2";

        private final Expr expr;
        private final ExceptClause22 exceptClause22;

        public ExceptClause2(
                Expr expr,
                ExceptClause22 exceptClause22
        ) {
            this.expr = expr;
            this.exceptClause22 = exceptClause22;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("expr", expr);
            addOptional("exceptClause22", exceptClause22);
        }

        public Expr expr() {
            return expr;
        }

        public Optional<ExceptClause22> exceptClause22() {
            return Optional.ofNullable(exceptClause22);
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

    // 'as' 'NAME'
    public static final class ExceptClause22 extends ConjunctionRule {
        public static final String RULE_NAME = "except_clause:2:2";

        private final boolean isTokenAs;
        private final String name;

        public ExceptClause22(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenAs", isTokenAs);
            addRequired("name", name);
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
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
