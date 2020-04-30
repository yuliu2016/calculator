package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// while_stmt: 'while' 'namedexpr_expr' 'suite' ['else' 'suite']
public final class WhileStmt extends ConjunctionRule {
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

    // 'else' 'suite'
    public static final class WhileStmt4 extends ConjunctionRule {
        private final boolean isTokenElse;
        private final Suite suite;

        public WhileStmt4(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;

            addRequired("isTokenElse", isTokenElse);
            addRequired("suite", suite);
        }

        public boolean isTokenElse() {
            return isTokenElse;
        }

        public Suite suite() {
            return suite;
        }
    }
}
