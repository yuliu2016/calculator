package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * args_kwargs: '*' ['typed_arg'] (',' 'default_arg')* [',' ['kwargs']]
 */
public final class ArgsKwargs extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("args_kwargs", RuleType.Conjunction);

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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("*");
        if (r) TypedArg.parse(t, lv + 1);
        if (r) parseArgsKwargs3List(t, lv);
        if (r) ArgsKwargs4.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parseArgsKwargs3List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ArgsKwargs3.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'default_arg'
     */
    public static final class ArgsKwargs3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("args_kwargs:3", RuleType.Conjunction);

        public static ArgsKwargs3 of(ParseTreeNode node) {
            return new ArgsKwargs3(node);
        }

        private ArgsKwargs3(ParseTreeNode node) {
            super(RULE, node);
        }

        public DefaultArg defaultArg() {
            return DefaultArg.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && DefaultArg.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * ',' ['kwargs']
     */
    public static final class ArgsKwargs4 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("args_kwargs:4", RuleType.Conjunction);

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

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken(",");
            if (r) Kwargs.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
