package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * break_stmt: 'break'
 */
public final class BreakStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("break_stmt", RuleType.Conjunction, true);

    public static BreakStmt of(ParseTreeNode node) {
        return new BreakStmt(node);
    }

    private BreakStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("break");

        parseTree.exit(result);
        return result;
    }
}
