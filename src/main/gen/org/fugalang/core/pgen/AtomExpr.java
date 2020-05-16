package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * atom_expr: 'atom' 'trailer'* ['block_suite']
 */
public final class AtomExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("atom_expr", RuleType.Conjunction);

    public static AtomExpr of(ParseTreeNode node) {
        return new AtomExpr(node);
    }

    private AtomExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom atom() {
        return get(0, Atom::of);
    }

    public List<Trailer> trailers() {
        return getList(1, Trailer::of);
    }

    public BlockSuite blockSuite() {
        return get(2, BlockSuite::of);
    }

    public boolean hasBlockSuite() {
        return has(2, BlockSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Atom.parse(t, lv + 1);
        if (r) parseTrailers(t, lv);
        if (r) BlockSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static void parseTrailers(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Trailer.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }
}
