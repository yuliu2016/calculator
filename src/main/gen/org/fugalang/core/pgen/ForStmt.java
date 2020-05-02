package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * for_stmt: 'for' 'targets' 'in' 'exprlist' 'suite' ['else' 'suite']
 */
public final class ForStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("for_stmt", RuleType.Conjunction, true);

    private final boolean isTokenFor;
    private final Targets targets;
    private final boolean isTokenIn;
    private final Exprlist exprlist;
    private final Suite suite;
    private final ForStmt6 forStmt6;

    public ForStmt(
            boolean isTokenFor,
            Targets targets,
            boolean isTokenIn,
            Exprlist exprlist,
            Suite suite,
            ForStmt6 forStmt6
    ) {
        this.isTokenFor = isTokenFor;
        this.targets = targets;
        this.isTokenIn = isTokenIn;
        this.exprlist = exprlist;
        this.suite = suite;
        this.forStmt6 = forStmt6;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenFor", isTokenFor());
        addRequired("targets", targets());
        addRequired("isTokenIn", isTokenIn());
        addRequired("exprlist", exprlist());
        addRequired("suite", suite());
        addOptional("forStmt6", forStmt6());
    }

    public boolean isTokenFor() {
        return isTokenFor;
    }

    public Targets targets() {
        return targets;
    }

    public boolean isTokenIn() {
        return isTokenIn;
    }

    public Exprlist exprlist() {
        return exprlist;
    }

    public Suite suite() {
        return suite;
    }

    public ForStmt6 forStmt6() {
        return forStmt6;
    }

    public boolean hasForStmt6() {
        return forStmt6() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("for");
        result = result && Targets.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenLiteral("in");
        result = result && Exprlist.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        ForStmt6.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'else' 'suite'
     */
    public static final class ForStmt6 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("for_stmt:6", RuleType.Conjunction, false);

        private final boolean isTokenElse;
        private final Suite suite;

        public ForStmt6(
                boolean isTokenElse,
                Suite suite
        ) {
            this.isTokenElse = isTokenElse;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenElse", isTokenElse());
            addRequired("suite", suite());
        }

        public boolean isTokenElse() {
            return isTokenElse;
        }

        public Suite suite() {
            return suite;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("else");
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
