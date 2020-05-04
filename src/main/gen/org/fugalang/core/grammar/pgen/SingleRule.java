package org.fugalang.core.grammar.pgen;

import org.fugalang.core.grammar.token.MetaTokenType;
import org.fugalang.core.parser.*;

/**
 * single_rule: 'TOK' ':' 'or_rule' 'NEWLINE'
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

    @Override
    protected void buildRule() {
        addRequired(token());
        addRequired(isTokenColon(), ":");
        addRequired(orRule());
        addRequired(isTokenNewline(), "\n");
    }

    public String token() {
        var element = getItem(0);
        element.failIfAbsent(MetaTokenType.TOK);
        return element.asString();
    }

    public boolean isTokenColon() {
        var element = getItem(1);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public OrRule orRule() {
        var element = getItem(2);
        element.failIfAbsent(OrRule.RULE);
        return OrRule.of(element);
    }

    public boolean isTokenNewline() {
        var element = getItem(3);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(MetaTokenType.TOK);
        result = result && parseTree.consumeToken(":");
        result = result && OrRule.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("\n");

        parseTree.exit(level, marker, result);
        return result;
    }
}
