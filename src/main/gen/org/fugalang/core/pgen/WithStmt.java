package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("with_stmt", RuleType.Conjunction, true);

    private final boolean isTokenWith;
    private final WithItem withItem;
    private final List<WithStmt3> withStmt3List;
    private final Suite suite;

    public WithStmt(
            boolean isTokenWith,
            WithItem withItem,
            List<WithStmt3> withStmt3List,
            Suite suite
    ) {
        this.isTokenWith = isTokenWith;
        this.withItem = withItem;
        this.withStmt3List = withStmt3List;
        this.suite = suite;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenWith", isTokenWith());
        addRequired("withItem", withItem());
        addRequired("withStmt3List", withStmt3List());
        addRequired("suite", suite());
    }

    public boolean isTokenWith() {
        return isTokenWith;
    }

    public WithItem withItem() {
        return withItem;
    }

    public List<WithStmt3> withStmt3List() {
        return withStmt3List;
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

        result = parseTree.consumeTokenLiteral("with");
        result = result && WithItem.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!WithStmt3.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'with_item'
     */
    public static final class WithStmt3 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("with_stmt:3", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final WithItem withItem;

        public WithStmt3(
                boolean isTokenComma,
                WithItem withItem
        ) {
            this.isTokenComma = isTokenComma;
            this.withItem = withItem;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("withItem", withItem());
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public WithItem withItem() {
            return withItem;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && WithItem.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
