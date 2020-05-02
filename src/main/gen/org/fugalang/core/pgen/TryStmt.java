package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite')
 */
public final class TryStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("try_stmt", RuleType.Conjunction, true);

    private final boolean isTokenTry;
    private final Suite suite;
    private final TryStmt3 tryStmt3;

    public TryStmt(
            boolean isTokenTry,
            Suite suite,
            TryStmt3 tryStmt3
    ) {
        this.isTokenTry = isTokenTry;
        this.suite = suite;
        this.tryStmt3 = tryStmt3;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenTry", isTokenTry());
        addRequired("suite", suite());
        addRequired("tryStmt3", tryStmt3());
    }

    public boolean isTokenTry() {
        return isTokenTry;
    }

    public Suite suite() {
        return suite;
    }

    public TryStmt3 tryStmt3() {
        return tryStmt3;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("try");
        result = result && Suite.parse(parseTree, level + 1);
        result = result && TryStmt3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite'
     */
    public static final class TryStmt3 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3", RuleType.Disjunction, false);

        private final TryStmt31 tryStmt31;
        private final TryStmt32 tryStmt32;

        public TryStmt3(
                TryStmt31 tryStmt31,
                TryStmt32 tryStmt32
        ) {
            this.tryStmt31 = tryStmt31;
            this.tryStmt32 = tryStmt32;
        }

        @Override
        protected void buildRule() {
            addChoice("tryStmt31", tryStmt31());
            addChoice("tryStmt32", tryStmt32());
        }

        public TryStmt31 tryStmt31() {
            return tryStmt31;
        }

        public boolean hasTryStmt31() {
            return tryStmt31() != null;
        }

        public TryStmt32 tryStmt32() {
            return tryStmt32;
        }

        public boolean hasTryStmt32() {
            return tryStmt32() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = TryStmt31.parse(parseTree, level + 1);
            result = result || TryStmt32.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite']
     */
    public static final class TryStmt31 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1", RuleType.Conjunction, false);

        private final List<TryStmt311> tryStmt311List;
        private final TryStmt312 tryStmt312;
        private final TryStmt313 tryStmt313;

        public TryStmt31(
                List<TryStmt311> tryStmt311List,
                TryStmt312 tryStmt312,
                TryStmt313 tryStmt313
        ) {
            this.tryStmt311List = tryStmt311List;
            this.tryStmt312 = tryStmt312;
            this.tryStmt313 = tryStmt313;
        }

        @Override
        protected void buildRule() {
            addRequired("tryStmt311List", tryStmt311List());
            addOptional("tryStmt312", tryStmt312());
            addOptional("tryStmt313", tryStmt313());
        }

        public List<TryStmt311> tryStmt311List() {
            return tryStmt311List;
        }

        public TryStmt312 tryStmt312() {
            return tryStmt312;
        }

        public boolean hasTryStmt312() {
            return tryStmt312() != null;
        }

        public TryStmt313 tryStmt313() {
            return tryStmt313;
        }

        public boolean hasTryStmt313() {
            return tryStmt313() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            parseTree.enterCollection();
            result = TryStmt311.parse(parseTree, level + 1);
            while (true) {
                var pos = parseTree.position();
                if (!TryStmt311.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            TryStmt312.parse(parseTree, level + 1);
            TryStmt313.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'except_clause' 'suite'
     */
    public static final class TryStmt311 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1:1", RuleType.Conjunction, false);

        private final ExceptClause exceptClause;
        private final Suite suite;

        public TryStmt311(
                ExceptClause exceptClause,
                Suite suite
        ) {
            this.exceptClause = exceptClause;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("exceptClause", exceptClause());
            addRequired("suite", suite());
        }

        public ExceptClause exceptClause() {
            return exceptClause;
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

            result = ExceptClause.parse(parseTree, level + 1);
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'else' 'suite'
     */
    public static final class TryStmt312 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1:2", RuleType.Conjunction, false);

        private final boolean isTokenElse;
        private final Suite suite;

        public TryStmt312(
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

    /**
     * 'finally' 'suite'
     */
    public static final class TryStmt313 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1:3", RuleType.Conjunction, false);

        private final boolean isTokenFinally;
        private final Suite suite;

        public TryStmt313(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFinally", isTokenFinally());
            addRequired("suite", suite());
        }

        public boolean isTokenFinally() {
            return isTokenFinally;
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

            result = parseTree.consumeTokenLiteral("finally");
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'finally' 'suite'
     */
    public static final class TryStmt32 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:2", RuleType.Conjunction, false);

        private final boolean isTokenFinally;
        private final Suite suite;

        public TryStmt32(
                boolean isTokenFinally,
                Suite suite
        ) {
            this.isTokenFinally = isTokenFinally;
            this.suite = suite;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFinally", isTokenFinally());
            addRequired("suite", suite());
        }

        public boolean isTokenFinally() {
            return isTokenFinally;
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

            result = parseTree.consumeTokenLiteral("finally");
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
