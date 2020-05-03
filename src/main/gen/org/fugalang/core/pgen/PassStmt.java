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

    @Override
    protected void buildRule() {
        addRequired(isTokenPass());
    }

    public boolean isTokenPass() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("pass");

        parseTree.exit(level, marker, result);
        return result;
    }
}
