package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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

    public Atom atom() {
        return Atom.of(getItem(0));
    }

    public List<Trailer> trailerList() {
        return getList(1, Trailer::of);
    }

    public BlockSuite blockSuite() {
        return BlockSuite.of(getItem(2));
    }

    public boolean hasBlockSuite() {
        return hasItemOfRule(2, BlockSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Atom.parse(parseTree, level + 1);
        if (result) parseTrailerList(parseTree, level + 1);
        if (result) BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseTrailerList(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Trailer.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }
}
