package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// comp_iter: 'comp_for' | 'comp_if'
public final class CompIter extends DisjunctionRule {
    public static final String RULE_NAME = "comp_iter";

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
        setExplicitName(RULE_NAME);
        addChoice("compFor", compFor);
        addChoice("compIf", compIf);
    }

    public CompFor compFor() {
        return compFor;
    }

    public CompIf compIf() {
        return compIf;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = CompFor.parse(parseTree, level + 1);
        result = result || CompIf.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
