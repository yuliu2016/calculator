package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * single_rule: 'NAME' ':' 'or_rule' 'NEWLINE'
 */
public final class SingleRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("single_rule", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(TokenType.NAME);
        result = result && parseTree.consumeToken(":");
        result = result && OrRule.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(TokenType.NEWLINE);

        parseTree.exit(result);
        return result;
    }
}
