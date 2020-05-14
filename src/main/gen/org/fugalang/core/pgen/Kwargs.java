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

    public TypedArg typedArg() {
        return TypedArg.of(getItem(1));
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("**");
        result = result && TypedArg.parse(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(result);
        return result;
    }
}
