package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * del_stmt: 'del' 'targets'
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
        addRequired(targets());
    }

    public boolean isTokenDel() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targets targets() {
        var element = getItem(1);
        element.failIfAbsent(Targets.RULE);
        return Targets.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("del");
        result = result && Targets.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
