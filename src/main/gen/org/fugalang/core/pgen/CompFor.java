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
        addRequired("isTokenFor", isTokenFor());
        addRequired("targets", targets());
        addRequired("isTokenIn", isTokenIn());
        addRequired("disjunction", disjunction());
        addOptional("compIter", compIter());
    }

    public boolean isTokenFor() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Targets targets() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Targets.of(element);
    }

    public boolean isTokenIn() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public Disjunction disjunction() {
        var element = getItem(3);
        if (!element.isPresent()) return null;
        return Disjunction.of(element);
    }

    public CompIter compIter() {
        var element = getItem(4);
        if (!element.isPresent()) return null;
        return CompIter.of(element);
    }

    public boolean hasCompIter() {
        return compIter() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("for");
        result = result && Targets.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenLiteral("in");
        result = result && Disjunction.parse(parseTree, level + 1);
        CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
