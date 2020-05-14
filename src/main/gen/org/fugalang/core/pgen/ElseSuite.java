package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * else_suite: 'else' 'suite'
 */
public final class ElseSuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("else_suite", RuleType.Conjunction, true);

    public static ElseSuite of(ParseTreeNode node) {
        return new ElseSuite(node);
    }

    private ElseSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return Suite.of(getItem(1));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("else");
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
