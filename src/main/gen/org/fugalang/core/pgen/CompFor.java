package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_for: 'for' 'targetlist' 'in' 'disjunction' ['comp_iter']
 */
public final class CompFor extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_for", RuleType.Conjunction, true);

    public static CompFor of(ParseTreeNode node) {
        return new CompFor(node);
    }

    private CompFor(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenFor(), "for");
        addRequired(targetlist());
        addRequired(isTokenIn(), "in");
        addRequired(disjunction());
        addOptional(compIterOrNull());
    }

    public boolean isTokenFor() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targetlist targetlist() {
        var element = getItem(1);
        element.failIfAbsent(Targetlist.RULE);
        return Targetlist.of(element);
    }

    public boolean isTokenIn() {
        var element = getItem(2);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Disjunction disjunction() {
        var element = getItem(3);
        element.failIfAbsent(Disjunction.RULE);
        return Disjunction.of(element);
    }

    public CompIter compIter() {
        var element = getItem(4);
        element.failIfAbsent(CompIter.RULE);
        return CompIter.of(element);
    }

    public CompIter compIterOrNull() {
        var element = getItem(4);
        if (!element.isPresent(CompIter.RULE)) {
            return null;
        }
        return CompIter.of(element);
    }

    public boolean hasCompIter() {
        var element = getItem(4);
        return element.isPresent(CompIter.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("for");
        result = result && Targetlist.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("in");
        result = result && Disjunction.parse(parseTree, level + 1);
        if (result) CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
