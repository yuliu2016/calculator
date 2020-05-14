package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * parameters: '(' ['arglist'] ')'
 */
public final class Parameters extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("parameters", RuleType.Conjunction, true);

    public static Parameters of(ParseTreeNode node) {
        return new Parameters(node);
    }

    private Parameters(ParseTreeNode node) {
        super(RULE, node);
    }

    public Arglist arglist() {
        return Arglist.of(getItem(1));
    }

    public boolean hasArglist() {
        return hasItemOfRule(1, Arglist.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("(");
        if (result) Arglist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(")");

        parseTree.exit(level, marker, result);
        return result;
    }
}
