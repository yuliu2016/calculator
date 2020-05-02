package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
 */
public final class SimpleAtom extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("simple_atom", RuleType.Disjunction, true);

    public static SimpleAtom of(ParseTreeNode node) {
        return new SimpleAtom(node);
    }

    private SimpleAtom(ParseTreeNode node) {
        super(RULE, node);
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
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return (String) element.asObject();
    }

    public boolean hasName() {
        return name() != null;
    }

    public Object number() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return element.asObject();
    }

    public boolean hasNumber() {
        return number() != null;
    }

    public String string() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return (String) element.asObject();
    }

    public boolean hasString() {
        return string() != null;
    }

    public boolean isTokenNone() {
        var element = getItem(3);
        return element.asBoolean();
    }

    public boolean isTokenTrue() {
        var element = getItem(4);
        return element.asBoolean();
    }

    public boolean isTokenFalse() {
        var element = getItem(5);
        return element.asBoolean();
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
