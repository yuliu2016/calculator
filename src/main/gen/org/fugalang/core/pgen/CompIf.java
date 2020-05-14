package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_if: 'if' 'expr' ['comp_iter']
 */
public final class CompIf extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_if", RuleType.Conjunction, true);

    public static CompIf of(ParseTreeNode node) {
        return new CompIf(node);
    }

    private CompIf(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public CompIter compIter() {
        return CompIter.of(getItem(2));
    }

    public boolean hasCompIter() {
        return hasItemOfRule(2, CompIter.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("if");
        result = result && Expr.parse(parseTree, level + 1);
        if (result) CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
