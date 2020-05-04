package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * simple_stmt: 'small_stmt' (';' 'small_stmt')* [';']
 */
public final class SimpleStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_stmt", RuleType.Conjunction, true);

    public static SimpleStmt of(ParseTreeNode node) {
        return new SimpleStmt(node);
    }

    private SimpleStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<SimpleStmt2> simpleStmt2List;

    @Override
    protected void buildRule() {
        addRequired(smallStmt());
        addRequired(simpleStmt2List());
        addRequired(isTokenSemicolon(), ";");
    }

    public SmallStmt smallStmt() {
        var element = getItem(0);
        element.failIfAbsent(SmallStmt.RULE);
        return SmallStmt.of(element);
    }

    public List<SimpleStmt2> simpleStmt2List() {
        if (simpleStmt2List != null) {
            return simpleStmt2List;
        }
        List<SimpleStmt2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(SimpleStmt2.of(node));
        }
        simpleStmt2List = result == null ? Collections.emptyList() : result;
        return simpleStmt2List;
    }

    public boolean isTokenSemicolon() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SmallStmt.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!SimpleStmt2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeToken(";");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ';' 'small_stmt'
     */
    public static final class SimpleStmt2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_stmt:2", RuleType.Conjunction, false);

        public static SimpleStmt2 of(ParseTreeNode node) {
            return new SimpleStmt2(node);
        }

        private SimpleStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenSemicolon(), ";");
            addRequired(smallStmt());
        }

        public boolean isTokenSemicolon() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public SmallStmt smallStmt() {
            var element = getItem(1);
            element.failIfAbsent(SmallStmt.RULE);
            return SmallStmt.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(";");
            result = result && SmallStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
