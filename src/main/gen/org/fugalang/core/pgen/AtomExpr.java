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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Atom.parse(t, l + 1);
        if (r) parseTrailerList(t, l);
        if (r) BlockSuite.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    private static void parseTrailerList(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Trailer.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }
}
