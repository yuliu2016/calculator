package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * atom_expr: ['await'] 'atom' 'trailer'*
 */
public final class AtomExpr extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("atom_expr", RuleType.Conjunction, true);

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
        addRequired("isTokenAwait", isTokenAwait());
        addRequired("atom", atom());
        addRequired("trailerList", trailerList());
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
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("await");
        result = result && Atom.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Trailer.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }
}
