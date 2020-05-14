package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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

    public Suite suite() {
        return Suite.of(getItem(1));
    }

    public TryStmt3 tryStmt3() {
        return TryStmt3.of(getItem(2));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("try");
        result = result && Suite.parse(parseTree, level + 1);
        result = result && TryStmt3.parse(parseTree, level + 1);

        parseTree.exit(result);
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

        public TryStmt31 tryStmt31() {
            return TryStmt31.of(getItem(0));
        }

        public boolean hasTryStmt31() {
            return hasItemOfRule(0, TryStmt31.RULE);
        }

        public FinallySuite finallySuite() {
            return FinallySuite.of(getItem(1));
        }

        public boolean hasFinallySuite() {
            return hasItemOfRule(1, FinallySuite.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = TryStmt31.parse(parseTree, level + 1);
            result = result || FinallySuite.parse(parseTree, level + 1);

            parseTree.exit(result);
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

        public List<TryStmt311> tryStmt311List() {
            return getList(0, TryStmt311::of);
        }

        public ElseSuite elseSuite() {
            return ElseSuite.of(getItem(1));
        }

        public boolean hasElseSuite() {
            return hasItemOfRule(1, ElseSuite.RULE);
        }

        public FinallySuite finallySuite() {
            return FinallySuite.of(getItem(2));
        }

        public boolean hasFinallySuite() {
            return hasItemOfRule(2, FinallySuite.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTryStmt311List(parseTree, level);
            if (result) ElseSuite.parse(parseTree, level + 1);
            if (result) FinallySuite.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }

        private static boolean parseTryStmt311List(ParseTree parseTree, int level) {
            parseTree.enterCollection();
            var result = TryStmt311.parse(parseTree, level + 1);
            if (result) while (true) {
                var pos = parseTree.position();
                if (!TryStmt311.parse(parseTree, level + 1)) break;
                if (parseTree.guardLoopExit(pos)) break;
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

        public ExceptClause exceptClause() {
            return ExceptClause.of(getItem(0));
        }

        public Suite suite() {
            return Suite.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = ExceptClause.parse(parseTree, level + 1);
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
