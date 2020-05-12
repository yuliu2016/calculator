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
        addRequired(colon());
        addRequired(orRule());
        addRequired(newline());
    }

    public String token() {
        var element = getItem(0);
        element.failIfAbsent(MetaTokenType.TOK);
        return element.asString();
    }

    public String colon() {
        var element = getItem(1);
        element.failIfAbsent(MetaTokenType.COL);
        return element.asString();
    }

    public OrRule orRule() {
        var element = getItem(2);
        element.failIfAbsent(OrRule.RULE);
        return OrRule.of(element);
    }

    public String newline() {
        var element = getItem(3);
        element.failIfAbsent(MetaTokenType.NEWLINE);
        return element.asString();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(MetaTokenType.TOK);
        result = result && parseTree.consumeToken(MetaTokenType.COL);
        result = result && OrRule.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(MetaTokenType.NEWLINE);

        parseTree.exit(level, marker, result);
        return result;
    }
}
