package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
 */
public final class SimpleAtom extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("simple_atom", RuleType.Disjunction);

    public static SimpleAtom of(ParseTreeNode node) {
        return new SimpleAtom(node);
    }

    private SimpleAtom(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public boolean hasName() {
        return has(0, TokenType.NAME);
    }

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1, TokenType.NUMBER);
    }

    public String string() {
        return get(2, TokenType.STRING);
    }

    public boolean hasString() {
        return has(2, TokenType.STRING);
    }

    public boolean isNone() {
        return is(3);
    }

    public boolean isTrue() {
        return is(4);
    }

    public boolean isFalse() {
        return is(5);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.NUMBER);
        r = r || t.consume(TokenType.STRING);
        r = r || t.consume("None");
        r = r || t.consume("True");
        r = r || t.consume("False");
        t.exit(r);
        return r;
    }
}
