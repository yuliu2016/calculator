package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * elif_stmt: 'elif' 'named_expr' 'suite'
 */
public final class ElifStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("elif_stmt", RuleType.Conjunction, true);

    public static ElifStmt of(ParseTreeNode node) {
        return new ElifStmt(node);
    }

    private ElifStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isTokenElif() {
        return true;
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public Suite suite() {
        return Suite.of(getItem(2));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("elif");
        result = result && NamedExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
