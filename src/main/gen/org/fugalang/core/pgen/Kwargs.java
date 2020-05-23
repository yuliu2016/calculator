package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * kwargs: '**' 'typed_arg' [',']
 */
public final class Kwargs extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("kwargs", RuleType.Conjunction);

    public static Kwargs of(ParseTreeNode node) {
        return new Kwargs(node);
    }

    private Kwargs(ParseTreeNode node) {
        super(RULE, node);
    }

    public TypedArg typedArg() {
        return get(1, TypedArg::of);
    }

    public boolean isComma() {
        return is(2);
    }
}
