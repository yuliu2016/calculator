package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * while_stmt: 'while' 'namedexpr_expr' 'suite' ['else' 'suite']
 */
public final class WhileStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("while_stmt", RuleType.Conjunction, true);

    public static WhileStmt of(ParseTreeNode node) {
        return new WhileStmt(node);
    }

    private WhileStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenWhile(), "while");
        addRequired(namedexprExpr());
        addRequired(suite());
        addOptional(whileStmt4());
    }

    public boolean isTokenWhile() {
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

    public WhileStmt4 whileStmt4() {
        var element = getItem(3);
        if (!element.isPresent(WhileStmt4.RULE)) {
            return null;
        }
        return WhileStmt4.of(element);
    }

    public boolean hasWhileStmt4() {
        return whileStmt4() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("while");
        result = result && NamedexprExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        WhileStmt4.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'else' 'suite'
     */
    public static final class WhileStmt4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("while_stmt:4", RuleType.Conjunction, false);

        public static WhileStmt4 of(ParseTreeNode node) {
            return new WhileStmt4(node);
        }

        private WhileStmt4(ParseTreeNode node) {
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
