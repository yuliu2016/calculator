package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * typed_arg_list: 'kwargs' | 'args_kwargs' | 'full_arg_list'
 */
public final class TypedArgList extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("typed_arg_list", RuleType.Disjunction, true);

    public static TypedArgList of(ParseTreeNode node) {
        return new TypedArgList(node);
    }

    private TypedArgList(ParseTreeNode node) {
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

    public FullArgList fullArgList() {
        return FullArgList.of(getItem(2));
    }

    public boolean hasFullArgList() {
        return hasItemOfRule(2, FullArgList.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Kwargs.parse(parseTree, level + 1);
        result = result || ArgsKwargs.parse(parseTree, level + 1);
        result = result || FullArgList.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
