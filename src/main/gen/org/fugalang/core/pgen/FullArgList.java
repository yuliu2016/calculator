package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<FullArgList2> fullArgList2List;

    @Override
    protected void buildRule() {
        addRequired(defaultArg());
        addRequired(fullArgList2List());
        addOptional(fullArgList3OrNull());
    }

    public DefaultArg defaultArg() {
        var element = getItem(0);
        element.failIfAbsent(DefaultArg.RULE);
        return DefaultArg.of(element);
    }

    public List<FullArgList2> fullArgList2List() {
        if (fullArgList2List != null) {
            return fullArgList2List;
        }
        List<FullArgList2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(FullArgList2.of(node));
        }
        fullArgList2List = result == null ? Collections.emptyList() : result;
        return fullArgList2List;
    }

    public FullArgList3 fullArgList3() {
        var element = getItem(2);
        element.failIfAbsent(FullArgList3.RULE);
        return FullArgList3.of(element);
    }

    public FullArgList3 fullArgList3OrNull() {
        var element = getItem(2);
        if (!element.isPresent(FullArgList3.RULE)) {
            return null;
        }
        return FullArgList3.of(element);
    }

    public boolean hasFullArgList3() {
        var element = getItem(2);
        return element.isPresent(FullArgList3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DefaultArg.parse(parseTree, level + 1);
        if (result) parseFullArgList2List(parseTree, level + 1);
        if (result) FullArgList3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseFullArgList2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!FullArgList2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(defaultArg());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public DefaultArg defaultArg() {
            var element = getItem(1);
            element.failIfAbsent(DefaultArg.RULE);
            return DefaultArg.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addOptional(fullArgList32OrNull());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public FullArgList32 fullArgList32() {
            var element = getItem(1);
            element.failIfAbsent(FullArgList32.RULE);
            return FullArgList32.of(element);
        }

        public FullArgList32 fullArgList32OrNull() {
            var element = getItem(1);
            if (!element.isPresent(FullArgList32.RULE)) {
                return null;
            }
            return FullArgList32.of(element);
        }

        public boolean hasFullArgList32() {
            var element = getItem(1);
            return element.isPresent(FullArgList32.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addChoice(kwargsOrNull());
            addChoice(argsKwargsOrNull());
        }

        public Kwargs kwargs() {
            var element = getItem(0);
            element.failIfAbsent(Kwargs.RULE);
            return Kwargs.of(element);
        }

        public Kwargs kwargsOrNull() {
            var element = getItem(0);
            if (!element.isPresent(Kwargs.RULE)) {
                return null;
            }
            return Kwargs.of(element);
        }

        public boolean hasKwargs() {
            var element = getItem(0);
            return element.isPresent(Kwargs.RULE);
        }

        public ArgsKwargs argsKwargs() {
            var element = getItem(1);
            element.failIfAbsent(ArgsKwargs.RULE);
            return ArgsKwargs.of(element);
        }

        public ArgsKwargs argsKwargsOrNull() {
            var element = getItem(1);
            if (!element.isPresent(ArgsKwargs.RULE)) {
                return null;
            }
            return ArgsKwargs.of(element);
        }

        public boolean hasArgsKwargs() {
            var element = getItem(1);
            return element.isPresent(ArgsKwargs.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Kwargs.parse(parseTree, level + 1);
            result = result || ArgsKwargs.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
