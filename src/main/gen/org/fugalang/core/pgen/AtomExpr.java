package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * atom_expr: ['await'] 'atom' 'trailer'*
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
        addRequired("isTokenAwait", isTokenAwait());
        addRequired("atom", atom());
        addRequired("trailerList", trailerList());
    }

    public boolean isTokenAwait() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Atom atom() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Atom.of(element);
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
