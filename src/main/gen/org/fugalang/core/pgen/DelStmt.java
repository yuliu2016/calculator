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

    public Targetlist targetlist() {
        return Targetlist.of(getItem(1));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("del");
        result = result && Targetlist.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
