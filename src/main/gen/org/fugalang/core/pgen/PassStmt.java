package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pass_stmt: 'pass'
 */
public final class PassStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pass_stmt", RuleType.Conjunction, true);

    public static PassStmt of(ParseTreeNode node) {
        return new PassStmt(node);
    }

    private PassStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("pass");

        parseTree.exit(result);
        return result;
    }
}
