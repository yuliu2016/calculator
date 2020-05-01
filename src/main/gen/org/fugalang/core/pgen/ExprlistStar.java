package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
public final class ExprlistStar extends ConjunctionRule {
    public static final String RULE_NAME = "exprlist_star";

    private final ExprOrStar exprOrStar;
    private final List<ExprlistStar2> exprlistStar2List;
    private final boolean isTokenComma;

    public ExprlistStar(
            ExprOrStar exprOrStar,
            List<ExprlistStar2> exprlistStar2List,
            boolean isTokenComma
    ) {
        this.exprOrStar = exprOrStar;
        this.exprlistStar2List = exprlistStar2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("exprOrStar", exprOrStar);
        addRequired("exprlistStar2List", exprlistStar2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public List<ExprlistStar2> exprlistStar2List() {
        return exprlistStar2List;
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

        result = ExprOrStar.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ExprlistStar2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    // ',' 'expr_or_star'
    public static final class ExprlistStar2 extends ConjunctionRule {
        public static final String RULE_NAME = "exprlist_star:2";

        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public ExprlistStar2(
                boolean isTokenComma,
                ExprOrStar exprOrStar
        ) {
            this.isTokenComma = isTokenComma;
            this.exprOrStar = exprOrStar;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("exprOrStar", exprOrStar);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public ExprOrStar exprOrStar() {
            return exprOrStar;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && ExprOrStar.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
