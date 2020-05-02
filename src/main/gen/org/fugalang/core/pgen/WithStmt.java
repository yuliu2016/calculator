package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * with_stmt: 'with' 'with_item' (',' 'with_item')* 'suite'
 */
public final class WithStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("with_stmt", RuleType.Conjunction, true);

    public static WithStmt of(ParseTreeNode node) {
        return new WithStmt(node);
    }

    private WithStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<WithStmt3> withStmt3List;

    @Override
    protected void buildRule() {
        addRequired("isTokenWith", isTokenWith());
        addRequired("withItem", withItem());
        addRequired("withStmt3List", withStmt3List());
        addRequired("suite", suite());
    }

    public boolean isTokenWith() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public WithItem withItem() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return WithItem.of(element);
    }

    public List<WithStmt3> withStmt3List() {
        return withStmt3List;
    }

    public Suite suite() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return Suite.of(element);
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
    public static final class WithStmt3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("with_stmt:3", RuleType.Conjunction, false);

        public static WithStmt3 of(ParseTreeNode node) {
            return new WithStmt3(node);
        }

        private WithStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("withItem", withItem());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public WithItem withItem() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return WithItem.of(element);
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
