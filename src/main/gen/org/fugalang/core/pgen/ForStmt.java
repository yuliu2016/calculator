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
        addRequired("isTokenFor", isTokenFor());
        addRequired("targets", targets());
        addRequired("isTokenIn", isTokenIn());
        addRequired("exprlist", exprlist());
        addRequired("suite", suite());
        addOptional("forStmt6", forStmt6());
    }

    public boolean isTokenFor() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Targets targets() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Targets.of(element);
    }

    public boolean isTokenIn() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public Exprlist exprlist() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return Exprlist.of(element);
    }

    public Suite suite() {
        var element = getItem(4);
        if (!element.isPresent()) return null;
        return Suite.of(element);
    }

    public ForStmt6 forStmt6() {
        var element = getItem(5);
        if (!element.isPresent()) return null;
        return ForStmt6.of(element);
    }

    public boolean hasForStmt6() {
        return forStmt6() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("for");
        result = result && Targets.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenLiteral("in");
        result = result && Exprlist.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        ForStmt6.parse(parseTree, level + 1);

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
