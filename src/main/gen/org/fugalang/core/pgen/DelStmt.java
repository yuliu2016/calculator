package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * del_stmt: 'del' 'targets'
 */
public final class DelStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("del_stmt", RuleType.Conjunction, true);

    private final boolean isTokenDel;
    private final Targets targets;

    public DelStmt(
            boolean isTokenDel,
            Targets targets
    ) {
        this.isTokenDel = isTokenDel;
        this.targets = targets;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenDel", isTokenDel());
        addRequired("targets", targets());
    }

    public boolean isTokenDel() {
        return isTokenDel;
    }

    public Targets targets() {
        return targets;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("del");
        result = result && Targets.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
