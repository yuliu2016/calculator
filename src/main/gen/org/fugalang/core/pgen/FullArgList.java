package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * full_arg_list: 'default_arg' (',' 'default_arg')* [',' ['kwargs' | 'args_kwargs']]
 */
public final class FullArgList extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("full_arg_list", RuleType.Conjunction);

    public static FullArgList of(ParseTreeNode node) {
        return new FullArgList(node);
    }

    private FullArgList(ParseTreeNode node) {
        super(RULE, node);
    }

    public DefaultArg defaultArg() {
        return get(0, DefaultArg::of);
    }

    public List<FullArgList2> defaultArgs() {
        return getList(1, FullArgList2::of);
    }

    public FullArgList3 fullArgList3() {
        return get(2, FullArgList3::of);
    }

    public boolean hasFullArgList3() {
        return has(2, FullArgList3.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = DefaultArg.parse(t, lv + 1);
        if (r) parseDefaultArgs(t, lv);
        if (r) FullArgList3.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parseDefaultArgs(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!FullArgList2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'default_arg'
     */
    public static final class FullArgList2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("full_arg_list:2", RuleType.Conjunction);

        public static FullArgList2 of(ParseTreeNode node) {
            return new FullArgList2(node);
        }

        private FullArgList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DefaultArg defaultArg() {
            return get(1, DefaultArg::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && DefaultArg.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * ',' ['kwargs' | 'args_kwargs']
     */
    public static final class FullArgList3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("full_arg_list:3", RuleType.Conjunction);

        public static FullArgList3 of(ParseTreeNode node) {
            return new FullArgList3(node);
        }

        private FullArgList3(ParseTreeNode node) {
            super(RULE, node);
        }

        public FullArgList32 kwargsOrArgsKwargs() {
            return get(1, FullArgList32::of);
        }

        public boolean hasKwargsOrArgsKwargs() {
            return has(1, FullArgList32.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            if (r) FullArgList32.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * 'kwargs' | 'args_kwargs'
     */
    public static final class FullArgList32 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("full_arg_list:3:2", RuleType.Disjunction);

        public static FullArgList32 of(ParseTreeNode node) {
            return new FullArgList32(node);
        }

        private FullArgList32(ParseTreeNode node) {
            super(RULE, node);
        }

        public Kwargs kwargs() {
            return get(0, Kwargs::of);
        }

        public boolean hasKwargs() {
            return has(0, Kwargs.RULE);
        }

        public ArgsKwargs argsKwargs() {
            return get(1, ArgsKwargs::of);
        }

        public boolean hasArgsKwargs() {
            return has(1, ArgsKwargs.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Kwargs.parse(t, lv + 1);
            r = r || ArgsKwargs.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
