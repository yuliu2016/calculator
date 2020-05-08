package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * slicelist: 'slice' (',' 'slice')* [',']
 */
public final class Slicelist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("slicelist", RuleType.Conjunction, true);

    public static Slicelist of(ParseTreeNode node) {
        return new Slicelist(node);
    }

    private Slicelist(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Slicelist2> slicelist2List;

    @Override
    protected void buildRule() {
        addRequired(slice());
        addRequired(slicelist2List());
        addOptional(isTokenComma(), ",");
    }

    public Slice slice() {
        var element = getItem(0);
        element.failIfAbsent(Slice.RULE);
        return Slice.of(element);
    }

    public List<Slicelist2> slicelist2List() {
        if (slicelist2List != null) {
            return slicelist2List;
        }
        List<Slicelist2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Slicelist2.of(node));
        }
        slicelist2List = result == null ? Collections.emptyList() : result;
        return slicelist2List;
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
        if (result) parseSlicelist2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseSlicelist2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Slicelist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'slice'
     */
    public static final class Slicelist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slicelist:2", RuleType.Conjunction, false);

        public static Slicelist2 of(ParseTreeNode node) {
            return new Slicelist2(node);
        }

        private Slicelist2(ParseTreeNode node) {
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
