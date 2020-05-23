package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pipe_for: ['comp_for'] 'for' 'targetlist' ['if' 'named_expr'] ['parameters' | 'block_suite']
 */
public final class PipeFor extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("pipe_for", RuleType.Conjunction);

    public static PipeFor of(ParseTreeNode node) {
        return new PipeFor(node);
    }

    private PipeFor(ParseTreeNode node) {
        super(RULE, node);
    }

    public CompFor compFor() {
        return get(0, CompFor::of);
    }

    public boolean hasCompFor() {
        return has(0, CompFor.RULE);
    }

    public Targetlist targetlist() {
        return get(2, Targetlist::of);
    }

    public PipeFor4 ifNamedExpr() {
        return get(3, PipeFor4::of);
    }

    public boolean hasIfNamedExpr() {
        return has(3, PipeFor4.RULE);
    }

    public PipeFor5 parametersOrBlockSuite() {
        return get(4, PipeFor5::of);
    }

    public boolean hasParametersOrBlockSuite() {
        return has(4, PipeFor5.RULE);
    }

    /**
     * 'if' 'named_expr'
     */
    public static final class PipeFor4 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("pipe_for:4", RuleType.Conjunction);

        public static PipeFor4 of(ParseTreeNode node) {
            return new PipeFor4(node);
        }

        private PipeFor4(ParseTreeNode node) {
            super(RULE, node);
        }

        public NamedExpr namedExpr() {
            return get(1, NamedExpr::of);
        }
    }

    /**
     * 'parameters' | 'block_suite'
     */
    public static final class PipeFor5 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("pipe_for:5", RuleType.Disjunction);

        public static PipeFor5 of(ParseTreeNode node) {
            return new PipeFor5(node);
        }

        private PipeFor5(ParseTreeNode node) {
            super(RULE, node);
        }

        public Parameters parameters() {
            return get(0, Parameters::of);
        }

        public boolean hasParameters() {
            return has(0, Parameters.RULE);
        }

        public BlockSuite blockSuite() {
            return get(1, BlockSuite::of);
        }

        public boolean hasBlockSuite() {
            return has(1, BlockSuite.RULE);
        }
    }
}
