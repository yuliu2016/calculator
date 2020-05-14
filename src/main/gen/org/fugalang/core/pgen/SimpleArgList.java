package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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

    public SimpleArg simpleArg() {
        return SimpleArg.of(getItem(0));
    }

    public List<SimpleArgList2> simpleArgList2List() {
        return getList(1, SimpleArgList2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = SimpleArg.parse(parseTree, level + 1);
        if (result) parseSimpleArgList2List(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static void parseSimpleArgList2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!SimpleArgList2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
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

        public SimpleArg simpleArg() {
            return SimpleArg.of(getItem(0));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = SimpleArg.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
