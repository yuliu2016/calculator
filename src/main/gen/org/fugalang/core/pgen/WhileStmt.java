package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.Optional;

/**
 * while_stmt: 'while' 'namedexpr_expr' 'suite' ['else' 'suite']
 */
public final class WhileStmt extends ConjunctionRule {
    public static final String RULE_NAME = "while_stmt";

    private final boolean isTokenWhile;
    private final NamedexprExpr namedexprExpr;
    private final Suite suite;
    private final WhileStmt4 whileStmt4;

    public WhileStmt(
            boolean isTokenWhile,
            NamedexprExpr namedexprExpr,
            Suite suite,
            WhileStmt4 whileStmt4
    ) {
        this.isTokenWhile = isTokenWhile;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
        this.whileStmt4 = whileStmt4;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenWhile", isTokenWhile);
        addRequired("namedexprExpr", namedexprExpr);
        addRequired("suite", suite);
        addOptional("whileStmt4", whileStmt4);
    }

    public boolean isTokenWhile() {
        return isTokenWhile;
    }

    public NamedexprExpr namedexprExpr() {
        return namedexprExpr;
    }

    public Suite suite() {
        return suite;
    }

    public Optional<WhileStmt4> whileStmt4() {
        return Optional.ofNullable(whileStmt4);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("while");
        result = result && NamedexprExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        WhileStmt4.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'else' 'suite'
     */
    public static final class WhileStmt4 extends ConjunctionRule {
        public static final String RULE_NAME = "while_stmt:4";

        private final boolean isTokenElse;
        private final Suite suite;

        public WhileStmt4(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenElse", isTokenElse);
            addRequired("suite", suite);
        }

        public boolean isTokenElse() {
            return isTokenElse;
        }

        public Suite suite() {
            return suite;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("else");
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
