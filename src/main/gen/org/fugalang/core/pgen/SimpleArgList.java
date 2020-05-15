package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * simple_arg_list: 'simple_arg' ('simple_arg')*
 */
public final class SimpleArgList extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("simple_arg_list", RuleType.Conjunction);

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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = SimpleArg.parse(t, lv + 1);
        if (r) parseSimpleArgList2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseSimpleArgList2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!SimpleArgList2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'simple_arg'
     */
    public static final class SimpleArgList2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("simple_arg_list:2", RuleType.Conjunction);

        public static SimpleArgList2 of(ParseTreeNode node) {
            return new SimpleArgList2(node);
        }

        private SimpleArgList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleArg simpleArg() {
            return SimpleArg.of(getItem(0));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = SimpleArg.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
