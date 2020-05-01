package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// exprlist: 'expr' (',' 'expr')* [',']
public final class Exprlist extends ConjunctionRule {
    public static final String RULE_NAME = "exprlist";

    private final Expr expr;
    private final List<Exprlist2> exprlist2List;
    private final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            List<Exprlist2> exprlist2List,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2List = exprlist2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("expr", expr);
        addRequired("exprlist2List", exprlist2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public Expr expr() {
        return expr;
    }

    public List<Exprlist2> exprlist2List() {
        return exprlist2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        while (true) {
            if (!Exprlist2.parse(parseTree, level + 1)) {
                break;
            }
        }
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    // ',' 'expr'
    public static final class Exprlist2 extends ConjunctionRule {
        public static final String RULE_NAME = "exprlist:2";

        private final boolean isTokenComma;
        private final Expr expr;

        public Exprlist2(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("expr", expr);
        }

        public boolean isTokenComma() {
            return isTokenComma;
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

            result = parseTree.consumeTokenLiteral(",");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
