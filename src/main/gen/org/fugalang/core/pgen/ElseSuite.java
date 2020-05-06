package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * else_suite: 'else' 'suite'
 */
public final class ElseSuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("else_suite", RuleType.Conjunction, true);

    public static ElseSuite of(ParseTreeNode node) {
        return new ElseSuite(node);
    }

    private ElseSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenElse(), "else");
        addRequired(suite());
    }

    public boolean isTokenElse() {
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

        result = parseTree.consumeToken("else");
        result = result && Suite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
