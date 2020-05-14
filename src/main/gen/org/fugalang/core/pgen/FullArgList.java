package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * full_arg_list: 'default_arg' (',' 'default_arg')* [',' ['kwargs' | 'args_kwargs']]
 */
public final class FullArgList extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("full_arg_list", RuleType.Conjunction, true);

    public static FullArgList of(ParseTreeNode node) {
        return new FullArgList(node);
    }

    private FullArgList(ParseTreeNode node) {
        super(RULE, node);
    }

    public DefaultArg defaultArg() {
        return DefaultArg.of(getItem(0));
    }

    public List<FullArgList2> fullArgList2List() {
        return getList(1, FullArgList2::of);
    }

    public FullArgList3 fullArgList3() {
        return FullArgList3.of(getItem(2));
    }

    public boolean hasFullArgList3() {
        return hasItemOfRule(2, FullArgList3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DefaultArg.parse(parseTree, level + 1);
        if (result) parseFullArgList2List(parseTree, level + 1);
        if (result) FullArgList3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseFullArgList2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!FullArgList2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'default_arg'
     */
    public static final class FullArgList2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("full_arg_list:2", RuleType.Conjunction, false);

        public static FullArgList2 of(ParseTreeNode node) {
            return new FullArgList2(node);
        }

        private FullArgList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DefaultArg defaultArg() {
            return DefaultArg.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && DefaultArg.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ',' ['kwargs' | 'args_kwargs']
     */
    public static final class FullArgList3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("full_arg_list:3", RuleType.Conjunction, false);

        public static FullArgList3 of(ParseTreeNode node) {
            return new FullArgList3(node);
        }

        private FullArgList3(ParseTreeNode node) {
            super(RULE, node);
        }

        public FullArgList32 fullArgList32() {
            return FullArgList32.of(getItem(1));
        }

        public boolean hasFullArgList32() {
            return hasItemOfRule(1, FullArgList32.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            if (result) FullArgList32.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'kwargs' | 'args_kwargs'
     */
    public static final class FullArgList32 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("full_arg_list:3:2", RuleType.Disjunction, false);

        public static FullArgList32 of(ParseTreeNode node) {
            return new FullArgList32(node);
        }

        private FullArgList32(ParseTreeNode node) {
            super(RULE, node);
        }

        public Kwargs kwargs() {
            return Kwargs.of(getItem(0));
        }

        public boolean hasKwargs() {
            return hasItemOfRule(0, Kwargs.RULE);
        }

        public ArgsKwargs argsKwargs() {
            return ArgsKwargs.of(getItem(1));
        }

        public boolean hasArgsKwargs() {
            return hasItemOfRule(1, ArgsKwargs.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Kwargs.parse(parseTree, level + 1);
            result = result || ArgsKwargs.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
