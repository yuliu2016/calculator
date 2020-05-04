package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * subscriptlist: 'subscript' (',' 'subscript')* [',']
 */
public final class Subscriptlist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("subscriptlist", RuleType.Conjunction, true);

    public static Subscriptlist of(ParseTreeNode node) {
        return new Subscriptlist(node);
    }

    private Subscriptlist(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Subscriptlist2> subscriptlist2List;

    @Override
    protected void buildRule() {
        addRequired(subscript());
        addRequired(subscriptlist2List());
        addOptional(isTokenComma(), ",");
    }

    public Subscript subscript() {
        var element = getItem(0);
        element.failIfAbsent(Subscript.RULE);
        return Subscript.of(element);
    }

    public List<Subscriptlist2> subscriptlist2List() {
        if (subscriptlist2List != null) {
            return subscriptlist2List;
        }
        List<Subscriptlist2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Subscriptlist2.of(node));
        }
        subscriptlist2List = result == null ? Collections.emptyList() : result;
        return subscriptlist2List;
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

        result = Subscript.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Subscriptlist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'subscript'
     */
    public static final class Subscriptlist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("subscriptlist:2", RuleType.Conjunction, false);

        public static Subscriptlist2 of(ParseTreeNode node) {
            return new Subscriptlist2(node);
        }

        private Subscriptlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(subscript());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Subscript subscript() {
            var element = getItem(1);
            element.failIfAbsent(Subscript.RULE);
            return Subscript.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Subscript.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
