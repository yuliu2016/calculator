package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else_suite'] ['finally_suite'] | 'finally_suite')
 */
public final class TryStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("try_stmt", RuleType.Conjunction, true);

    public static TryStmt of(ParseTreeNode node) {
        return new TryStmt(node);
    }

    private TryStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenTry(), "try");
        addRequired(suite());
        addRequired(tryStmt3());
    }

    public boolean isTokenTry() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Suite suite() {
        var element = getItem(1);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public TryStmt3 tryStmt3() {
        var element = getItem(2);
        element.failIfAbsent(TryStmt3.RULE);
        return TryStmt3.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("try");
        result = result && Suite.parse(parseTree, level + 1);
        result = result && TryStmt3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ('except_clause' 'suite')+ ['else_suite'] ['finally_suite'] | 'finally_suite'
     */
    public static final class TryStmt3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3", RuleType.Disjunction, false);

        public static TryStmt3 of(ParseTreeNode node) {
            return new TryStmt3(node);
        }

        private TryStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice(tryStmt31OrNull());
            addChoice(finallySuiteOrNull());
        }

        public TryStmt31 tryStmt31() {
            var element = getItem(0);
            element.failIfAbsent(TryStmt31.RULE);
            return TryStmt31.of(element);
        }

        public TryStmt31 tryStmt31OrNull() {
            var element = getItem(0);
            if (!element.isPresent(TryStmt31.RULE)) {
                return null;
            }
            return TryStmt31.of(element);
        }

        public boolean hasTryStmt31() {
            var element = getItem(0);
            return element.isPresent(TryStmt31.RULE);
        }

        public FinallySuite finallySuite() {
            var element = getItem(1);
            element.failIfAbsent(FinallySuite.RULE);
            return FinallySuite.of(element);
        }

        public FinallySuite finallySuiteOrNull() {
            var element = getItem(1);
            if (!element.isPresent(FinallySuite.RULE)) {
                return null;
            }
            return FinallySuite.of(element);
        }

        public boolean hasFinallySuite() {
            var element = getItem(1);
            return element.isPresent(FinallySuite.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = TryStmt31.parse(parseTree, level + 1);
            result = result || FinallySuite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ('except_clause' 'suite')+ ['else_suite'] ['finally_suite']
     */
    public static final class TryStmt31 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1", RuleType.Conjunction, false);

        public static TryStmt31 of(ParseTreeNode node) {
            return new TryStmt31(node);
        }

        private TryStmt31(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<TryStmt311> tryStmt311List;

        @Override
        protected void buildRule() {
            addRequired(tryStmt311List());
            addOptional(elseSuiteOrNull());
            addOptional(finallySuiteOrNull());
        }

        public List<TryStmt311> tryStmt311List() {
            if (tryStmt311List != null) {
                return tryStmt311List;
            }
            List<TryStmt311> result = null;
            var element = getItem(0);
            for (var node : element.asCollection()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(TryStmt311.of(node));
            }
            tryStmt311List = result == null ? Collections.emptyList() : result;
            return tryStmt311List;
        }

        public ElseSuite elseSuite() {
            var element = getItem(1);
            element.failIfAbsent(ElseSuite.RULE);
            return ElseSuite.of(element);
        }

        public ElseSuite elseSuiteOrNull() {
            var element = getItem(1);
            if (!element.isPresent(ElseSuite.RULE)) {
                return null;
            }
            return ElseSuite.of(element);
        }

        public boolean hasElseSuite() {
            var element = getItem(1);
            return element.isPresent(ElseSuite.RULE);
        }

        public FinallySuite finallySuite() {
            var element = getItem(2);
            element.failIfAbsent(FinallySuite.RULE);
            return FinallySuite.of(element);
        }

        public FinallySuite finallySuiteOrNull() {
            var element = getItem(2);
            if (!element.isPresent(FinallySuite.RULE)) {
                return null;
            }
            return FinallySuite.of(element);
        }

        public boolean hasFinallySuite() {
            var element = getItem(2);
            return element.isPresent(FinallySuite.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTryStmt311List(parseTree, level + 1);
            if (result) ElseSuite.parse(parseTree, level + 1);
            if (result) FinallySuite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }

        private static boolean parseTryStmt311List(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            parseTree.enterCollection();
            var result = TryStmt311.parse(parseTree, level + 1);
            if (result) while (true) {
                var pos = parseTree.position();
                if (!TryStmt311.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            return result;
        }
    }

    /**
     * 'except_clause' 'suite'
     */
    public static final class TryStmt311 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1:1", RuleType.Conjunction, false);

        public static TryStmt311 of(ParseTreeNode node) {
            return new TryStmt311(node);
        }

        private TryStmt311(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(exceptClause());
            addRequired(suite());
        }

        public ExceptClause exceptClause() {
            var element = getItem(0);
            element.failIfAbsent(ExceptClause.RULE);
            return ExceptClause.of(element);
        }

        public Suite suite() {
            var element = getItem(1);
            element.failIfAbsent(Suite.RULE);
            return Suite.of(element);
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
}
