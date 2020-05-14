package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * return_stmt: 'return' ['exprlist_star']
 */
public final class ReturnStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("return_stmt", RuleType.Conjunction, true);

    public static ReturnStmt of(ParseTreeNode node) {
        return new ReturnStmt(node);
    }

    private ReturnStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprlistStar exprlistStar() {
        return ExprlistStar.of(getItem(1));
    }

    public boolean hasExprlistStar() {
        return hasItemOfRule(1, ExprlistStar.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("return");
        if (result) ExprlistStar.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
