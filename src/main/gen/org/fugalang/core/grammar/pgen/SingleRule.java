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

    public String token() {
        return getItemOfType(0, MetaTokenType.TOK);
    }

    public String colon() {
        return getItemOfType(1, MetaTokenType.COL);
    }

    public OrRule orRule() {
        return OrRule.of(getItem(2));
    }

    public String newline() {
        return getItemOfType(3, MetaTokenType.NEWLINE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken(MetaTokenType.TOK);
        result = result && parseTree.consumeToken(MetaTokenType.COL);
        result = result && OrRule.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(MetaTokenType.NEWLINE);

        parseTree.exit(result);
        return result;
    }
}
