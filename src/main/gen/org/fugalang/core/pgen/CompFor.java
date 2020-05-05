package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_for: 'for' 'targets' 'in' 'disjunction' ['comp_iter']
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
        addRequired(targets());
        addRequired(isTokenIn(), "in");
        addRequired(disjunction());
        addOptional(compIter());
    }

    public boolean isTokenFor() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targets targets() {
        var element = getItem(1);
        element.failIfAbsent(Targets.RULE);
        return Targets.of(element);
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
        result = result && Targets.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken("in");
        result = result && Disjunction.parse(parseTree, level + 1);
        if (result) CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
