package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_iter: 'comp_for' | 'comp_if'
 */
public final class CompIter extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_iter", RuleType.Disjunction, true);

    public static CompIter of(ParseTreeNode node) {
        return new CompIter(node);
    }

    private CompIter(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice("compFor", compFor());
        addChoice("compIf", compIf());
    }

    public CompFor compFor() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return CompFor.of(element);
    }

    public boolean hasCompFor() {
        return compFor() != null;
    }

    public CompIf compIf() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return CompIf.of(element);
    }

    public boolean hasCompIf() {
        return compIf() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = CompFor.parse(parseTree, level + 1);
        result = result || CompIf.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
