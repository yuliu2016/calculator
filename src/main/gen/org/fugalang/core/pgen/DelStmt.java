package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * del_stmt: 'del' 'targetlist'
 */
public final class DelStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("del_stmt", RuleType.Conjunction, true);

    public static DelStmt of(ParseTreeNode node) {
        return new DelStmt(node);
    }

    private DelStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenDel(), "del");
        addRequired(targetlist());
    }

    public boolean isTokenDel() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targetlist targetlist() {
        var element = getItem(1);
        element.failIfAbsent(Targetlist.RULE);
        return Targetlist.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("del");
        result = result && Targetlist.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
