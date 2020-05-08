package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * atom_expr: 'atom' 'trailer'* ['block_suite']
 */
public final class AtomExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("atom_expr", RuleType.Conjunction, true);

    public static AtomExpr of(ParseTreeNode node) {
        return new AtomExpr(node);
    }

    private AtomExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Trailer> trailerList;

    @Override
    protected void buildRule() {
        addRequired(atom());
        addRequired(trailerList());
        addOptional(blockSuiteOrNull());
    }

    public Atom atom() {
        var element = getItem(0);
        element.failIfAbsent(Atom.RULE);
        return Atom.of(element);
    }

    public List<Trailer> trailerList() {
        if (trailerList != null) {
            return trailerList;
        }
        List<Trailer> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Trailer.of(node));
        }
        trailerList = result == null ? Collections.emptyList() : result;
        return trailerList;
    }

    public BlockSuite blockSuite() {
        var element = getItem(2);
        element.failIfAbsent(BlockSuite.RULE);
        return BlockSuite.of(element);
    }

    public BlockSuite blockSuiteOrNull() {
        var element = getItem(2);
        if (!element.isPresent(BlockSuite.RULE)) {
            return null;
        }
        return BlockSuite.of(element);
    }

    public boolean hasBlockSuite() {
        var element = getItem(2);
        return element.isPresent(BlockSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Atom.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Trailer.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        if (result) BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
