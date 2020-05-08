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

    @Override
    protected void buildRule() {
        addChoice(kwargsOrNull());
        addChoice(argsKwargsOrNull());
        addChoice(fullArgListOrNull());
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

    public FullArgList fullArgList() {
        var element = getItem(2);
        element.failIfAbsent(FullArgList.RULE);
        return FullArgList.of(element);
    }

    public FullArgList fullArgListOrNull() {
        var element = getItem(2);
        if (!element.isPresent(FullArgList.RULE)) {
            return null;
        }
        return FullArgList.of(element);
    }

    public boolean hasFullArgList() {
        var element = getItem(2);
        return element.isPresent(FullArgList.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Kwargs.parse(parseTree, level + 1);
        result = result || ArgsKwargs.parse(parseTree, level + 1);
        result = result || FullArgList.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
