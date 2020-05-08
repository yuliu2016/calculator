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

    @Override
    protected void buildRule() {
        addRequired(isTokenLpar(), "(");
        addOptional(arglistOrNull());
        addRequired(isTokenRpar(), ")");
    }

    public boolean isTokenLpar() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Arglist arglist() {
        var element = getItem(1);
        element.failIfAbsent(Arglist.RULE);
        return Arglist.of(element);
    }

    public Arglist arglistOrNull() {
        var element = getItem(1);
        if (!element.isPresent(Arglist.RULE)) {
            return null;
        }
        return Arglist.of(element);
    }

    public boolean hasArglist() {
        var element = getItem(1);
        return element.isPresent(Arglist.RULE);
    }

    public boolean isTokenRpar() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("(");
        if (result) Arglist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(")");

        parseTree.exit(level, marker, result);
        return result;
    }
}
