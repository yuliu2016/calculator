package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
 */
public final class SimpleAtom extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("simple_atom", RuleType.Disjunction, true);

    private final String name;
    private final Object number;
    private final String string;
    private final boolean isTokenNone;
    private final boolean isTokenTrue;
    private final boolean isTokenFalse;

    public SimpleAtom(
            String name,
            Object number,
            String string,
            boolean isTokenNone,
            boolean isTokenTrue,
            boolean isTokenFalse
    ) {
        this.name = name;
        this.number = number;
        this.string = string;
        this.isTokenNone = isTokenNone;
        this.isTokenTrue = isTokenTrue;
        this.isTokenFalse = isTokenFalse;
    }

    @Override
    protected void buildRule() {
        addChoice("name", name());
        addChoice("number", number());
        addChoice("string", string());
        addChoice("isTokenNone", isTokenNone());
        addChoice("isTokenTrue", isTokenTrue());
        addChoice("isTokenFalse", isTokenFalse());
    }

    public String name() {
        return name;
    }

    public boolean hasName() {
        return name() != null;
    }

    public Object number() {
        return number;
    }

    public boolean hasNumber() {
        return number() != null;
    }

    public String string() {
        return string;
    }

    public boolean hasString() {
        return string() != null;
    }

    public boolean isTokenNone() {
        return isTokenNone;
    }

    public boolean isTokenTrue() {
        return isTokenTrue;
    }

    public boolean isTokenFalse() {
        return isTokenFalse;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("NAME");
        result = result || parseTree.consumeTokenType("NUMBER");
        result = result || parseTree.consumeTokenType("STRING");
        result = result || parseTree.consumeTokenLiteral("None");
        result = result || parseTree.consumeTokenLiteral("True");
        result = result || parseTree.consumeTokenLiteral("False");

        parseTree.exit(level, marker, result);
        return result;
    }
}
