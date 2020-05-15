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

    public String name() {
        return getItemOfType(0, TokenType.NAME);
    }

    public boolean hasName() {
        return hasItemOfType(0, TokenType.NAME);
    }

    public String number() {
        return getItemOfType(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return hasItemOfType(1, TokenType.NUMBER);
    }

    public String string() {
        return getItemOfType(2, TokenType.STRING);
    }

    public boolean hasString() {
        return hasItemOfType(2, TokenType.STRING);
    }

    public boolean isTokenNone() {
        return getBoolean(3);
    }

    public boolean isTokenTrue() {
        return getBoolean(4);
    }

    public boolean isTokenFalse() {
        return getBoolean(5);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken(TokenType.NAME);
        r = r || t.consumeToken(TokenType.NUMBER);
        r = r || t.consumeToken(TokenType.STRING);
        r = r || t.consumeToken("None");
        r = r || t.consumeToken("True");
        r = r || t.consumeToken("False");
        t.exit(r);
        return r;
    }
}
