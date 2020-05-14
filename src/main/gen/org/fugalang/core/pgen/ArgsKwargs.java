package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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

    public TypedArg typedArg() {
        return TypedArg.of(getItem(1));
    }

    public boolean hasTypedArg() {
        return hasItemOfRule(1, TypedArg.RULE);
    }

    public List<ArgsKwargs3> argsKwargs3List() {
        return getList(2, ArgsKwargs3::of);
    }

    public ArgsKwargs4 argsKwargs4() {
        return ArgsKwargs4.of(getItem(3));
    }

    public boolean hasArgsKwargs4() {
        return hasItemOfRule(3, ArgsKwargs4.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("*");
        if (result) TypedArg.parse(parseTree, level + 1);
        if (result) parseArgsKwargs3List(parseTree, level);
        if (result) ArgsKwargs4.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }

    private static void parseArgsKwargs3List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!ArgsKwargs3.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
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

        public DefaultArg defaultArg() {
            return DefaultArg.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && DefaultArg.parse(parseTree, level + 1);

            parseTree.exit(result);
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

        public Kwargs kwargs() {
            return Kwargs.of(getItem(1));
        }

        public boolean hasKwargs() {
            return hasItemOfRule(1, Kwargs.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            if (result) Kwargs.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}
