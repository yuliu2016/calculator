package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// while_stmt: 'while' 'namedexpr_expr' 'suite' ['else' 'suite']
public final class WhileStmt extends ConjunctionRule {
    private final boolean isTokenWhile;
    private final NamedexprExpr namedexprExpr;
    private final Suite suite;
    private final WhileStmt4Group whileStmt4Group;

    public WhileStmt(
            boolean isTokenWhile,
            NamedexprExpr namedexprExpr,
            Suite suite,
            WhileStmt4Group whileStmt4Group
    ) {
        this.isTokenWhile = isTokenWhile;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
        this.whileStmt4Group = whileStmt4Group;

        addRequired("isTokenWhile", isTokenWhile);
        addRequired("namedexprExpr", namedexprExpr);
        addRequired("suite", suite);
        addOptional("whileStmt4Group", whileStmt4Group);
    }

    public boolean getIsTokenWhile() {
        return isTokenWhile;
    }

    public NamedexprExpr getNamedexprExpr() {
        return namedexprExpr;
    }

    public Suite getSuite() {
        return suite;
    }

    public Optional<WhileStmt4Group> getWhileStmt4Group() {
        return Optional.ofNullable(whileStmt4Group);
    }

    // 'else' 'suite'
    public static final class WhileStmt4Group extends ConjunctionRule {
        private final boolean isTokenElse;
        private final Suite suite;

        public WhileStmt4Group(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;

            addRequired("isTokenElse", isTokenElse);
            addRequired("suite", suite);
        }

        public boolean getIsTokenElse() {
            return isTokenElse;
        }

        public Suite getSuite() {
            return suite;
        }
    }
}
