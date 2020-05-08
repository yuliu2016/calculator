package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * args_kwargs: '*' ['typed_arg'] (',' 'default_arg')* [',' ['kwargs']]
 */
public final class ArgsKwargs extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("args_kwargs", RuleType.Conjunction, true);

    public static ArgsKwargs of(ParseTreeNode node) {
        return new ArgsKwargs(node);
    }

    private ArgsKwargs(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<ArgsKwargs3> argsKwargs3List;

    @Override
    protected void buildRule() {
        addRequired(isTokenTimes(), "*");
        addOptional(typedArgOrNull());
        addRequired(argsKwargs3List());
        addOptional(argsKwargs4OrNull());
    }

    public boolean isTokenTimes() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public TypedArg typedArg() {
        var element = getItem(1);
        element.failIfAbsent(TypedArg.RULE);
        return TypedArg.of(element);
    }

    public TypedArg typedArgOrNull() {
        var element = getItem(1);
        if (!element.isPresent(TypedArg.RULE)) {
            return null;
        }
        return TypedArg.of(element);
    }

    public boolean hasTypedArg() {
        var element = getItem(1);
        return element.isPresent(TypedArg.RULE);
    }

    public List<ArgsKwargs3> argsKwargs3List() {
        if (argsKwargs3List != null) {
            return argsKwargs3List;
        }
        List<ArgsKwargs3> result = null;
        var element = getItem(2);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(ArgsKwargs3.of(node));
        }
        argsKwargs3List = result == null ? Collections.emptyList() : result;
        return argsKwargs3List;
    }

    public ArgsKwargs4 argsKwargs4() {
        var element = getItem(3);
        element.failIfAbsent(ArgsKwargs4.RULE);
        return ArgsKwargs4.of(element);
    }

    public ArgsKwargs4 argsKwargs4OrNull() {
        var element = getItem(3);
        if (!element.isPresent(ArgsKwargs4.RULE)) {
            return null;
        }
        return ArgsKwargs4.of(element);
    }

    public boolean hasArgsKwargs4() {
        var element = getItem(3);
        return element.isPresent(ArgsKwargs4.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("*");
        if (result) TypedArg.parse(parseTree, level + 1);
        if (result) parseArgsKwargs3List(parseTree, level + 1);
        if (result) ArgsKwargs4.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseArgsKwargs3List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ArgsKwargs3.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'default_arg'
     */
    public static final class ArgsKwargs3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("args_kwargs:3", RuleType.Conjunction, false);

        public static ArgsKwargs3 of(ParseTreeNode node) {
            return new ArgsKwargs3(node);
        }

        private ArgsKwargs3(ParseTreeNode node) {
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
     * ',' ['kwargs']
     */
    public static final class ArgsKwargs4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("args_kwargs:4", RuleType.Conjunction, false);

        public static ArgsKwargs4 of(ParseTreeNode node) {
            return new ArgsKwargs4(node);
        }

        private ArgsKwargs4(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addOptional(kwargsOrNull());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Kwargs kwargs() {
            var element = getItem(1);
            element.failIfAbsent(Kwargs.RULE);
            return Kwargs.of(element);
        }

        public Kwargs kwargsOrNull() {
            var element = getItem(1);
            if (!element.isPresent(Kwargs.RULE)) {
                return null;
            }
            return Kwargs.of(element);
        }

        public boolean hasKwargs() {
            var element = getItem(1);
            return element.isPresent(Kwargs.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            if (result) Kwargs.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
