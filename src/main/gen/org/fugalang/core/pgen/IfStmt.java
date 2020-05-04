package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * if_stmt: 'if' 'namedexpr_expr' 'suite' ('elif' 'namedexpr_expr' 'suite')* ['else' 'suite']
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
        addRequired(namedexprExpr());
        addRequired(suite());
        addRequired(ifStmt4List());
        addOptional(ifStmt5());
    }

    public boolean isTokenIf() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public NamedexprExpr namedexprExpr() {
        var element = getItem(1);
        element.failIfAbsent(NamedexprExpr.RULE);
        return NamedexprExpr.of(element);
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
            if (result == null) result = new ArrayList<>();
            result.add(IfStmt4.of(node));
        }
        ifStmt4List = result == null ? Collections.emptyList() : result;
        return ifStmt4List;
    }

    public IfStmt5 ifStmt5() {
        var element = getItem(4);
        if (!element.isPresent(IfStmt5.RULE)) {
            return null;
        }
        return IfStmt5.of(element);
    }

    public boolean hasIfStmt5() {
        return ifStmt5() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("if");
        result = result && NamedexprExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!IfStmt4.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        if (result) IfStmt5.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'elif' 'namedexpr_expr' 'suite'
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
            addRequired(namedexprExpr());
            addRequired(suite());
        }

        public boolean isTokenElif() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public NamedexprExpr namedexprExpr() {
            var element = getItem(1);
            element.failIfAbsent(NamedexprExpr.RULE);
            return NamedexprExpr.of(element);
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
            result = result && NamedexprExpr.parse(parseTree, level + 1);
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'else' 'suite'
     */
    public static final class IfStmt5 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("if_stmt:5", RuleType.Conjunction, false);

        public static IfStmt5 of(ParseTreeNode node) {
            return new IfStmt5(node);
        }

        private IfStmt5(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenElse(), "else");
            addRequired(suite());
        }

        public boolean isTokenElse() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
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

            result = parseTree.consumeToken("else");
            result = result && Suite.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
