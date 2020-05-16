package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * rules: ['NEWLINE'] 'single_rule'+
 */
public final class Rules extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("rules", RuleType.Conjunction);

    public static Rules of(ParseTreeNode node) {
        return new Rules(node);
    }

    private Rules(ParseTreeNode node) {
        super(RULE, node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0, TokenType.NEWLINE);
    }

    public List<SingleRule> singleRules() {
        return getList(1, SingleRule::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = parseSingleRules(t, lv);
        t.exit(r);
        return r;
    }

    private static boolean parseSingleRules(ParseTree t, int lv) {
        t.enterCollection();
        var r = SingleRule.parse(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!SingleRule.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
        return r;
    }
}
