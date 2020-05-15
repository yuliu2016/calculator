package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * single_rule: 'NAME' ':' 'or_rule' 'NEWLINE'
 */
public final class SingleRule extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("single_rule", RuleType.Conjunction);

    public static SingleRule of(ParseTreeNode node) {
        return new SingleRule(node);
    }

    private SingleRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return getItemOfType(0, TokenType.NAME);
    }

    public OrRule orRule() {
        return OrRule.of(getItem(2));
    }

    public String newline() {
        return getItemOfType(3, TokenType.NEWLINE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken(TokenType.NAME);
        r = r && t.consumeToken(":");
        r = r && OrRule.parse(t, lv + 1);
        r = r && t.consumeToken(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }
}
