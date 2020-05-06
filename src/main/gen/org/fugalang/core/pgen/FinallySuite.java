package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * finally_suite: 'finally' 'suite'
 */
public final class FinallySuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("finally_suite", RuleType.Conjunction, true);

    public static FinallySuite of(ParseTreeNode node) {
        return new FinallySuite(node);
    }

    private FinallySuite(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenFinally(), "finally");
        addRequired(suite());
    }

    public boolean isTokenFinally() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Suite suite() {
        var element = getItem(1);
        element.failIfAbsent(Suite.RULE);
        return Suite.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("finally");
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
