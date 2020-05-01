package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.List;
import java.util.Optional;

/**
 * if_stmt: 'if' 'namedexpr_expr' 'suite' ('elif' 'namedexpr_expr' 'suite')* ['else' 'suite']
 */
public final class IfStmt extends ConjunctionRule {
    public static final String RULE_NAME = "if_stmt";

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
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("if");
        result = result && NamedexprExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!IfStmt4.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        IfStmt5.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'elif' 'namedexpr_expr' 'suite'
     */
    public static final class IfStmt4 extends ConjunctionRule {
        public static final String RULE_NAME = "if_stmt:4";

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
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("elif");
            result = result && NamedexprExpr.parse(parseTree, level + 1);
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'else' 'suite'
     */
    public static final class IfStmt5 extends ConjunctionRule {
        public static final String RULE_NAME = "if_stmt:5";

        private final boolean isTokenElse;
        private final Suite suite;

        public IfStmt5(
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
