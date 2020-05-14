package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * while_stmt: 'while' 'named_expr' 'suite' ['else_suite']
 */
public final class WhileStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("while_stmt", RuleType.Conjunction, true);

    public static WhileStmt of(ParseTreeNode node) {
        return new WhileStmt(node);
    }

    private WhileStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public Suite suite() {
        return Suite.of(getItem(2));
    }

    public ElseSuite elseSuite() {
        return ElseSuite.of(getItem(3));
    }

    public boolean hasElseSuite() {
        return hasItemOfRule(3, ElseSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("while");
        result = result && NamedExpr.parse(parseTree, level + 1);
        result = result && Suite.parse(parseTree, level + 1);
        if (result) ElseSuite.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
