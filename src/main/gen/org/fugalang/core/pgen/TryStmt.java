package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * try_stmt: 'try' 'suite' (('except_clause' 'suite')+ ['else' 'suite'] ['finally' 'suite'] | 'finally' 'suite')
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
        addRequired("isTokenTry", isTokenTry());
        addRequired("suite", suite());
        addRequired("tryStmt3", tryStmt3());
    }

    public boolean isTokenTry() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Suite suite() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Suite.of(element);
    }

    public TryStmt3 tryStmt3() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return TryStmt3.of(element);
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
            addChoice("tryStmt31", tryStmt31());
            addChoice("tryStmt32", tryStmt32());
        }

        public TryStmt31 tryStmt31() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return TryStmt31.of(element);
        }

        public boolean hasTryStmt31() {
            return tryStmt31() != null;
        }

        public TryStmt32 tryStmt32() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return TryStmt32.of(element);
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
            addRequired("tryStmt311List", tryStmt311List());
            addOptional("tryStmt312", tryStmt312());
            addOptional("tryStmt313", tryStmt313());
        }

        public List<TryStmt311> tryStmt311List() {
            if (tryStmt311List != null) {
                return tryStmt311List;
            }
            List<TryStmt311> result = null;
            var element = getItem(0);
            for (var node : element.asCollection()) {
                if (result == null) result = new ArrayList<>();
                result.add(TryStmt311.of(node));
            }
            tryStmt311List = result == null ? Collections.emptyList() : result;
            return tryStmt311List;
        }

        public TryStmt312 tryStmt312() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return TryStmt312.of(element);
        }

        public boolean hasTryStmt312() {
            return tryStmt312() != null;
        }

        public TryStmt313 tryStmt313() {
            var element = getItem(2);
            if (!element.isPresent()) return null;
            return TryStmt313.of(element);
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
            addRequired("exceptClause", exceptClause());
            addRequired("suite", suite());
        }

        public ExceptClause exceptClause() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return ExceptClause.of(element);
        }

        public Suite suite() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
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

    /**
     * 'else' 'suite'
     */
    public static final class TryStmt312 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1:2", RuleType.Conjunction, false);

        public static TryStmt312 of(ParseTreeNode node) {
            return new TryStmt312(node);
        }

        private TryStmt312(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenElse", isTokenElse());
            addRequired("suite", suite());
        }

        public boolean isTokenElse() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Suite suite() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Suite.of(element);
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
    public static final class TryStmt313 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:1:3", RuleType.Conjunction, false);

        public static TryStmt313 of(ParseTreeNode node) {
            return new TryStmt313(node);
        }

        private TryStmt313(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFinally", isTokenFinally());
            addRequired("suite", suite());
        }

        public boolean isTokenFinally() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Suite suite() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Suite.of(element);
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
    public static final class TryStmt32 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("try_stmt:3:2", RuleType.Conjunction, false);

        public static TryStmt32 of(ParseTreeNode node) {
            return new TryStmt32(node);
        }

        private TryStmt32(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFinally", isTokenFinally());
            addRequired("suite", suite());
        }

        public boolean isTokenFinally() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Suite suite() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Suite.of(element);
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
