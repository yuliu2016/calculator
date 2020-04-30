package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// for_stmt: 'for' 'targets' 'in' 'exprlist' 'suite' ['else' 'suite']
public final class ForStmt extends ConjunctionRule {
    public static final String RULE_NAME = "for_stmt";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenFor", isTokenFor);
        addRequired("targets", targets);
        addRequired("isTokenIn", isTokenIn);
        addRequired("exprlist", exprlist);
        addRequired("suite", suite);
        addOptional("forStmt6", forStmt6);
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

    public Optional<ForStmt6> forStmt6() {
        return Optional.ofNullable(forStmt6);
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

    // 'else' 'suite'
    public static final class ForStmt6 extends ConjunctionRule {
        public static final String RULE_NAME = "for_stmt:6";

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
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
