package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * arglist: 'argument' (',' 'argument')* [',']
 */
public final class Arglist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("arglist", RuleType.Conjunction, true);

    public static Arglist of(ParseTreeNode node) {
        return new Arglist(node);
    }

    private Arglist(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Arglist2> arglist2List;

    @Override
    protected void buildRule() {
        addRequired("argument", argument());
        addRequired("arglist2List", arglist2List());
        addRequired("isTokenComma", isTokenComma());
    }

    public Argument argument() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Argument.of(element);
    }

    public List<Arglist2> arglist2List() {
        return arglist2List;
    }

    public boolean isTokenComma() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Argument.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Arglist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'argument'
     */
    public static final class Arglist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("arglist:2", RuleType.Conjunction, false);

        public static Arglist2 of(ParseTreeNode node) {
            return new Arglist2(node);
        }

        private Arglist2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("argument", argument());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Argument argument() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Argument.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Argument.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
