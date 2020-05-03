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

    @Override
    protected void buildRule() {
        addRequired(isTokenContinue());
    }

    public boolean isTokenContinue() {
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

        result = parseTree.consumeToken("continue");

        parseTree.exit(level, marker, result);
        return result;
    }
}
