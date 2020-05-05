package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * for_stmt: 'for' 'targets' 'in' 'exprlist' 'suite' ['else' 'suite']
 */
public final class ForStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("for_stmt", RuleType.Conjunction, true);

    public static ForStmt of(ParseTreeNode node) {
        return new ForStmt(node);
    }

    private ForStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenFor(), "for");
        addRequired(targets());
        addRequired(isTokenIn(), "in");
        addRequired(exprlist());
        addRequired(suite());
        addOptional(forStmt6());
    }

    public boolean isTokenFor() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targets targets() {
        var element = getItem(1);
        element.failIfAbsent(Targets.RULE);
        return Targets.of(element);
    }

    public boolean isTokenIn() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Exprlist exprlist() {
        var element = getItem(3);
        element.failIfAbsent(Exprlist.RULE);
        return Exprlist.of(element);
    }

    public Suite suite() {
        var element = getItem(4);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public ForStmt6 forStmt6() {
        var element = getItem(5);
        if (!element.isPresent(ForStmt6.RULE)) {
            return null;
        }
        return ForStmt6.of(element);
    }

    public boolean hasForStmt6() {
        var element = getItem(5);
        return element.isPresent(ForStmt6.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("for");
        result = result && Targets.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("in");
        result = result && Exprlist.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) ForStmt6.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'else' 'suite'
     */
    public static final class ForStmt6 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("for_stmt:6", RuleType.Conjunction, false);

        public static ForStmt6 of(ParseTreeNode node) {
            return new ForStmt6(node);
        }

        private ForStmt6(ParseTreeNode node) {
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
