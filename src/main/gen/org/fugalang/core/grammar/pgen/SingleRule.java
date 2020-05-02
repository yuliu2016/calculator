package org.fugalang.core.grammar.pgen;

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
        addRequired("token", token());
        addRequired("isTokenColon", isTokenColon());
        addRequired("orRule", orRule());
        addRequired("isTokenNewline", isTokenNewline());
    }

    public String token() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return (String) element.asObject();
    }

    public boolean isTokenColon() {
        var element = getItem(1);
        return element.asBoolean();
    }

    public OrRule orRule() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return OrRule.of(element);
    }

    public boolean isTokenNewline() {
        var element = getItem(3);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("TOK");
        result = result && parseTree.consumeTokenLiteral(":");
        result = result && OrRule.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenLiteral("NEWLINE");

        parseTree.exit(level, marker, result);
        return result;
    }
}
