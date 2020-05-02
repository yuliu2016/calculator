package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_iter: 'comp_for' | 'comp_if'
 */
public final class CompIter extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("comp_iter", RuleType.Disjunction, true);

    private final CompFor compFor;
    private final CompIf compIf;

    public CompIter(
            CompFor compFor,
            CompIf compIf
    ) {
        this.compFor = compFor;
        this.compIf = compIf;
    }

    @Override
    protected void buildRule() {
        addChoice("compFor", compFor());
        addChoice("compIf", compIf());
    }

    public CompFor compFor() {
        return compFor;
    }

    public boolean hasCompFor() {
        return compFor() != null;
    }

    public CompIf compIf() {
        return compIf;
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
