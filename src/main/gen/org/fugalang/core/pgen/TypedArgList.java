package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * typed_arg_list: 'kwargs' | 'args_kwargs' | 'full_arg_list'
 */
public final class TypedArgList extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("typed_arg_list", RuleType.Disjunction);

    public static TypedArgList of(ParseTreeNode node) {
        return new TypedArgList(node);
    }

    private TypedArgList(ParseTreeNode node) {
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

    public FullArgList fullArgList() {
        return get(2, FullArgList::of);
    }

    public boolean hasFullArgList() {
        return has(2, FullArgList.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Kwargs.parse(t, lv + 1);
        r = r || ArgsKwargs.parse(t, lv + 1);
        r = r || FullArgList.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}
