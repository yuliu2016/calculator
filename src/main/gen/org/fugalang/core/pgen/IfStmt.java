package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;

// if_stmt: 'if' 'namedexpr_expr' 'suite' ('elif' 'namedexpr_expr' 'suite')* ['else' 'suite']
public final class IfStmt extends ConjunctionRule {
    private final boolean isTokenIf;
    private final NamedexprExpr namedexprExpr;
    private final Suite suite;
    private final List<IfStmt4> ifStmt4List;
    private final IfStmt5 ifStmt5;

    public IfStmt(
            boolean isTokenIf,
            NamedexprExpr namedexprExpr,
            Suite suite,
            List<IfStmt4> ifStmt4List,
            IfStmt5 ifStmt5
    ) {
        this.isTokenIf = isTokenIf;
        this.namedexprExpr = namedexprExpr;
        this.suite = suite;
        this.ifStmt4List = ifStmt4List;
        this.ifStmt5 = ifStmt5;

        addRequired("isTokenIf", isTokenIf);
        addRequired("namedexprExpr", namedexprExpr);
        addRequired("suite", suite);
        addRequired("ifStmt4List", ifStmt4List);
        addOptional("ifStmt5", ifStmt5);
    }

    public boolean isTokenIf() {
        return isTokenIf;
    }

    public NamedexprExpr namedexprExpr() {
        return namedexprExpr;
    }

    public Suite suite() {
        return suite;
    }

    public List<IfStmt4> ifStmt4List() {
        return ifStmt4List;
    }

    public Optional<IfStmt5> ifStmt5() {
        return Optional.ofNullable(ifStmt5);
    }

    // 'elif' 'namedexpr_expr' 'suite'
    public static final class IfStmt4 extends ConjunctionRule {
        private final boolean isTokenElif;
        private final NamedexprExpr namedexprExpr;
        private final Suite suite;

        public IfStmt4(
                boolean isTokenElif,
                NamedexprExpr namedexprExpr,
                Suite suite
        ) {
            this.isTokenElif = isTokenElif;
            this.namedexprExpr = namedexprExpr;
            this.suite = suite;

            addRequired("isTokenElif", isTokenElif);
            addRequired("namedexprExpr", namedexprExpr);
            addRequired("suite", suite);
        }

        public boolean isTokenElif() {
            return isTokenElif;
        }

        public NamedexprExpr namedexprExpr() {
            return namedexprExpr;
        }

        public Suite suite() {
            return suite;
        }
    }

    // 'else' 'suite'
    public static final class IfStmt5 extends ConjunctionRule {
        private final boolean isTokenElse;
        private final Suite suite;

        public IfStmt5(
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
