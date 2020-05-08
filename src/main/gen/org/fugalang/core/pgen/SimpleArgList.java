package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * simple_arg_list: 'simple_arg' ('simple_arg')*
 */
public final class SimpleArgList extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_arg_list", RuleType.Conjunction, true);

    public static SimpleArgList of(ParseTreeNode node) {
        return new SimpleArgList(node);
    }

    private SimpleArgList(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<SimpleArgList2> simpleArgList2List;

    @Override
    protected void buildRule() {
        addRequired(simpleArg());
        addRequired(simpleArgList2List());
    }

    public SimpleArg simpleArg() {
        var element = getItem(0);
        element.failIfAbsent(SimpleArg.RULE);
        return SimpleArg.of(element);
    }

    public List<SimpleArgList2> simpleArgList2List() {
        if (simpleArgList2List != null) {
            return simpleArgList2List;
        }
        List<SimpleArgList2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(SimpleArgList2.of(node));
        }
        simpleArgList2List = result == null ? Collections.emptyList() : result;
        return simpleArgList2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = SimpleArg.parse(parseTree, level + 1);
        if (result) parseSimpleArgList2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseSimpleArgList2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!SimpleArgList2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * 'simple_arg'
     */
    public static final class SimpleArgList2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("simple_arg_list:2", RuleType.Conjunction, false);

        public static SimpleArgList2 of(ParseTreeNode node) {
            return new SimpleArgList2(node);
        }

        private SimpleArgList2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(simpleArg());
        }

        public SimpleArg simpleArg() {
            var element = getItem(0);
            element.failIfAbsent(SimpleArg.RULE);
            return SimpleArg.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = SimpleArg.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
