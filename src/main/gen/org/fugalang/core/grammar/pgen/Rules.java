package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * rules: ['NEWLINE'] 'single_rule'+
 */
public final class Rules extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("rules", RuleType.Conjunction, true);

    public static Rules of(ParseTreeNode node) {
        return new Rules(node);
    }

    private Rules(ParseTreeNode node) {
        super(RULE, node);
    }

    public String newline() {
        return getItemOfType(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return hasItemOfType(0, TokenType.NEWLINE);
    }

    public List<SingleRule> singleRuleList() {
        return getList(1, SingleRule::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        t.consumeToken(TokenType.NEWLINE);
        r = parseSingleRuleList(t, l);
        t.exit(r);
        return r;
    }

    private static boolean parseSingleRuleList(ParseTree t, int l) {
        t.enterCollection();
        var r = SingleRule.parse(t, l + 1);
        if (r) while (true) {
            var p = t.position();
            if (!SingleRule.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
        return r;
    }
}
