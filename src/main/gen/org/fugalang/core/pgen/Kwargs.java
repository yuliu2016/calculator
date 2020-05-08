package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * kwargs: '**' 'typed_arg' [',']
 */
public final class Kwargs extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("kwargs", RuleType.Conjunction, true);

    public static Kwargs of(ParseTreeNode node) {
        return new Kwargs(node);
    }

    private Kwargs(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenPower(), "**");
        addRequired(typedArg());
        addOptional(isTokenComma(), ",");
    }

    public boolean isTokenPower() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public TypedArg typedArg() {
        var element = getItem(1);
        element.failIfAbsent(TypedArg.RULE);
        return TypedArg.of(element);
    }

    public boolean isTokenComma() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("**");
        result = result && TypedArg.parse(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }
}
