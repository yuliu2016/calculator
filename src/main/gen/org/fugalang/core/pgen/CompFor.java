package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.Optional;

/**
 * comp_for: 'for' 'targets' 'in' 'disjunction' ['comp_iter']
 */
public final class CompFor extends ConjunctionRule {
    public static final String RULE_NAME = "comp_for";

    private final boolean isTokenFor;
    private final Targets targets;
    private final boolean isTokenIn;
    private final Disjunction disjunction;
    private final CompIter compIter;

    public CompFor(
            boolean isTokenFor,
            Targets targets,
            boolean isTokenIn,
            Disjunction disjunction,
            CompIter compIter
    ) {
        this.isTokenFor = isTokenFor;
        this.targets = targets;
        this.isTokenIn = isTokenIn;
        this.disjunction = disjunction;
        this.compIter = compIter;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenFor", isTokenFor);
        addRequired("targets", targets);
        addRequired("isTokenIn", isTokenIn);
        addRequired("disjunction", disjunction);
        addOptional("compIter", compIter);
    }

    public boolean isTokenFor() {
        return isTokenFor;
    }

    public Targets targets() {
        return targets;
    }

    public boolean isTokenIn() {
        return isTokenIn;
    }

    public Disjunction disjunction() {
        return disjunction;
    }

    public Optional<CompIter> compIter() {
        return Optional.ofNullable(compIter);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
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
