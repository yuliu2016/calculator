package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * subscript: 'slice' (',' 'slice')* [',']
 */
public final class Subscript extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("subscript", RuleType.Conjunction, true);

    public static Subscript of(ParseTreeNode node) {
        return new Subscript(node);
    }

    private Subscript(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Subscript2> subscript2List;

    @Override
    protected void buildRule() {
        addRequired(slice());
        addRequired(subscript2List());
        addOptional(isTokenComma(), ",");
    }

    public Slice slice() {
        var element = getItem(0);
        element.failIfAbsent(Slice.RULE);
        return Slice.of(element);
    }

    public List<Subscript2> subscript2List() {
        if (subscript2List != null) {
            return subscript2List;
        }
        List<Subscript2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Subscript2.of(node));
        }
        subscript2List = result == null ? Collections.emptyList() : result;
        return subscript2List;
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

        result = Slice.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Subscript2.parse(parseTree, level + 1) ||
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
     * ',' 'slice'
     */
    public static final class Subscript2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("subscript:2", RuleType.Conjunction, false);

        public static Subscript2 of(ParseTreeNode node) {
            return new Subscript2(node);
        }

        private Subscript2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(slice());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Slice slice() {
            var element = getItem(1);
            element.failIfAbsent(Slice.RULE);
            return Slice.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Slice.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
