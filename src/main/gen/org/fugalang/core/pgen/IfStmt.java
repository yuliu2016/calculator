package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * if_stmt: 'if' 'named_expr' 'suite' ('elif' 'named_expr' 'suite')* ['else_suite']
 */
public final class IfStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("if_stmt", RuleType.Conjunction, true);

    public static IfStmt of(ParseTreeNode node) {
        return new IfStmt(node);
    }

    private IfStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<IfStmt4> ifStmt4List;

    @Override
    protected void buildRule() {
        addRequired(isTokenIf(), "if");
        addRequired(namedExpr());
        addRequired(suite());
        addRequired(ifStmt4List());
        addOptional(elseSuiteOrNull());
    }

    public boolean isTokenIf() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public NamedExpr namedExpr() {
        var element = getItem(1);
        element.failIfAbsent(NamedExpr.RULE);
        return NamedExpr.of(element);
    }

    public Suite suite() {
        var element = getItem(2);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public List<IfStmt4> ifStmt4List() {
        if (ifStmt4List != null) {
            return ifStmt4List;
        }
        List<IfStmt4> result = null;
        var element = getItem(3);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(IfStmt4.of(node));
        }
        ifStmt4List = result == null ? Collections.emptyList() : result;
        return ifStmt4List;
    }

    public ElseSuite elseSuite() {
        var element = getItem(4);
        element.failIfAbsent(ElseSuite.RULE);
        return ElseSuite.of(element);
    }

    public ElseSuite elseSuiteOrNull() {
        var element = getItem(4);
        if (!element.isPresent(ElseSuite.RULE)) {
            return null;
        }
        return ElseSuite.of(element);
    }

    public boolean hasElseSuite() {
        var element = getItem(4);
        return element.isPresent(ElseSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("if");
        result = result && NamedExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) parseIfStmt4List(parseTree, level + 1);
        if (result) ElseSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseIfStmt4List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!IfStmt4.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * 'elif' 'named_expr' 'suite'
     */
    public static final class IfStmt4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("if_stmt:4", RuleType.Conjunction, false);

        public static IfStmt4 of(ParseTreeNode node) {
            return new IfStmt4(node);
        }

        private IfStmt4(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenElif(), "elif");
            addRequired(namedExpr());
            addRequired(suite());
        }

        public boolean isTokenElif() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public NamedExpr namedExpr() {
            var element = getItem(1);
            element.failIfAbsent(NamedExpr.RULE);
            return NamedExpr.of(element);
        }

        public Suite suite() {
            var element = getItem(2);
            element.failIfAbsent(Suite.RULE);
            return Suite.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("elif");
            result = result && NamedExpr.parse(parseTree, level + 1);
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
