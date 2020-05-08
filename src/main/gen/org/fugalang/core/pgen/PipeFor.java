package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pipe_for: ['comp_for'] 'for' 'targetlist' ('if' 'expr')* ['parameters'] ['block_suite']
 */
public final class PipeFor extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pipe_for", RuleType.Conjunction, true);

    public static PipeFor of(ParseTreeNode node) {
        return new PipeFor(node);
    }

    private PipeFor(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<PipeFor4> pipeFor4List;

    @Override
    protected void buildRule() {
        addOptional(compForOrNull());
        addRequired(isTokenFor(), "for");
        addRequired(targetlist());
        addRequired(pipeFor4List());
        addOptional(parametersOrNull());
        addOptional(blockSuiteOrNull());
    }

    public CompFor compFor() {
        var element = getItem(0);
        element.failIfAbsent(CompFor.RULE);
        return CompFor.of(element);
    }

    public CompFor compForOrNull() {
        var element = getItem(0);
        if (!element.isPresent(CompFor.RULE)) {
            return null;
        }
        return CompFor.of(element);
    }

    public boolean hasCompFor() {
        var element = getItem(0);
        return element.isPresent(CompFor.RULE);
    }

    public boolean isTokenFor() {
        var element = getItem(1);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Targetlist targetlist() {
        var element = getItem(2);
        element.failIfAbsent(Targetlist.RULE);
        return Targetlist.of(element);
    }

    public List<PipeFor4> pipeFor4List() {
        if (pipeFor4List != null) {
            return pipeFor4List;
        }
        List<PipeFor4> result = null;
        var element = getItem(3);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(PipeFor4.of(node));
        }
        pipeFor4List = result == null ? Collections.emptyList() : result;
        return pipeFor4List;
    }

    public Parameters parameters() {
        var element = getItem(4);
        element.failIfAbsent(Parameters.RULE);
        return Parameters.of(element);
    }

    public Parameters parametersOrNull() {
        var element = getItem(4);
        if (!element.isPresent(Parameters.RULE)) {
            return null;
        }
        return Parameters.of(element);
    }

    public boolean hasParameters() {
        var element = getItem(4);
        return element.isPresent(Parameters.RULE);
    }

    public BlockSuite blockSuite() {
        var element = getItem(5);
        element.failIfAbsent(BlockSuite.RULE);
        return BlockSuite.of(element);
    }

    public BlockSuite blockSuiteOrNull() {
        var element = getItem(5);
        if (!element.isPresent(BlockSuite.RULE)) {
            return null;
        }
        return BlockSuite.of(element);
    }

    public boolean hasBlockSuite() {
        var element = getItem(5);
        return element.isPresent(BlockSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        CompFor.parse(parseTree, level + 1);
        result = parseTree.consumeToken("for");
        result = result && Targetlist.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!PipeFor4.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        if (result) Parameters.parse(parseTree, level + 1);
        if (result) BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'if' 'expr'
     */
    public static final class PipeFor4 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("pipe_for:4", RuleType.Conjunction, false);

        public static PipeFor4 of(ParseTreeNode node) {
            return new PipeFor4(node);
        }

        private PipeFor4(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenIf(), "if");
            addRequired(expr());
        }

        public boolean isTokenIf() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("if");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
