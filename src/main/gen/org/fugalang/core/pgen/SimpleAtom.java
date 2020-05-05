package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

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
        addChoice(name());
        addChoice(number());
        addChoice(string());
        addChoice(isTokenNone(), "None");
        addChoice(isTokenTrue(), "True");
        addChoice(isTokenFalse(), "False");
    }

    public String name() {
        var element = getItem(0);
        if (!element.isPresent(TokenType.NAME)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasName() {
        var element = getItem(0);
        return element.isPresent(TokenType.NAME);
    }

    public String number() {
        var element = getItem(1);
        if (!element.isPresent(TokenType.NUMBER)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasNumber() {
        var element = getItem(1);
        return element.isPresent(TokenType.NUMBER);
    }

    public String string() {
        var element = getItem(2);
        if (!element.isPresent(TokenType.STRING)) {
            return null;
        }
        return element.asString();
    }

    public boolean hasString() {
        var element = getItem(2);
        return element.isPresent(TokenType.STRING);
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

        result = parseTree.consumeToken(TokenType.NAME);
        result = result || parseTree.consumeToken(TokenType.NUMBER);
        result = result || parseTree.consumeToken(TokenType.STRING);
        result = result || parseTree.consumeToken("None");
        result = result || parseTree.consumeToken("True");
        result = result || parseTree.consumeToken("False");

        parseTree.exit(level, marker, result);
        return result;
    }
}
