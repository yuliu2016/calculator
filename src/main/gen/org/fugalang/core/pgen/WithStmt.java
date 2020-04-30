package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
public final class WithStmt extends ConjunctionRule {
    public static final String RULE_NAME = "with_stmt";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenWith", isTokenWith);
        addRequired("withItem", withItem);
        addRequired("withStmt3List", withStmt3List);
        addRequired("suite", suite);
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
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }

    // ',' 'with_item'
    public static final class WithStmt3 extends ConjunctionRule {
        public static final String RULE_NAME = "with_stmt:3";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("withItem", withItem);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public WithItem withItem() {
            return withItem;
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
