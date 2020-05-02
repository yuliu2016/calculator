package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addRequired("isTokenIf", isTokenIf());
        addRequired("namedexprExpr", namedexprExpr());
        addRequired("suite", suite());
        addRequired("ifStmt4List", ifStmt4List());
        addOptional("ifStmt5", ifStmt5());
    }

    public boolean isTokenIf() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public NamedexprExpr namedexprExpr() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return NamedexprExpr.of(element);
    }

    public Suite suite() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return Suite.of(element);
    }

    public List<IfStmt4> ifStmt4List() {
        return ifStmt4List;
    }

    public IfStmt5 ifStmt5() {
        var element = getItem(4);
        if (!element.isPresent()) return null;
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

        result = parseTree.consumeTokenLiteral("if");
        result = result && NamedexprExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!IfStmt4.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        IfStmt5.parse(parseTree, level + 1);

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
            addRequired("isTokenElif", isTokenElif());
            addRequired("namedexprExpr", namedexprExpr());
            addRequired("suite", suite());
        }

        public boolean isTokenElif() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public NamedexprExpr namedexprExpr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return NamedexprExpr.of(element);
        }

        public Suite suite() {
            var element = getItem(2);
            if (!element.isPresent()) return null;
            return Suite.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("elif");
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
}
