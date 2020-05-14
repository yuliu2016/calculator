package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * continue_stmt: 'continue'
 */
public final class ContinueStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("continue_stmt", RuleType.Conjunction, true);

    public static ContinueStmt of(ParseTreeNode node) {
        return new ContinueStmt(node);
    }

    private ContinueStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("continue");

        parseTree.exit(result);
        return result;
    }
}
