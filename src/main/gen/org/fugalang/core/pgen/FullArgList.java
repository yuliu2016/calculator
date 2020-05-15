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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = DefaultArg.parse(t, l + 1);
        if (r) parseFullArgList2List(t, l);
        if (r) FullArgList3.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    private static void parseFullArgList2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!FullArgList2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && DefaultArg.parse(t, l + 1);
            t.exit(r);
            return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            if (r) FullArgList32.parse(t, l + 1);
            t.exit(r);
            return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = Kwargs.parse(t, l + 1);
            r = r || ArgsKwargs.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}
