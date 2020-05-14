package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * finally_suite: 'finally' 'suite'
 */
public final class FinallySuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("finally_suite", RuleType.Conjunction, true);

    public static FinallySuite of(ParseTreeNode node) {
        return new FinallySuite(node);
    }

    private FinallySuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return Suite.of(getItem(1));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("finally");
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
