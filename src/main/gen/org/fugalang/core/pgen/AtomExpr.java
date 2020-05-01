package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// atom_expr: ['await'] 'atom' 'trailer'*
public final class AtomExpr extends ConjunctionRule {
    public static final String RULE_NAME = "atom_expr";

    private final boolean isTokenAwait;
    private final Atom atom;
    private final List<Trailer> trailerList;

    public AtomExpr(
            boolean isTokenAwait,
            Atom atom,
            List<Trailer> trailerList
    ) {
        this.isTokenAwait = isTokenAwait;
        this.atom = atom;
        this.trailerList = trailerList;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenAwait", isTokenAwait);
        addRequired("atom", atom);
        addRequired("trailerList", trailerList);
    }

    public boolean isTokenAwait() {
        return isTokenAwait;
    }

    public Atom atom() {
        return atom;
    }

    public List<Trailer> trailerList() {
        return trailerList;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("await");
        result = result && Atom.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            if (!Trailer.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }
}
