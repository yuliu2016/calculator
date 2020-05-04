package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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
        addRequired(isTokenAwait(), "await");
        addRequired(atom());
        addRequired(trailerList());
    }

    public boolean isTokenAwait() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Atom atom() {
        var element = getItem(1);
        element.failIfAbsent(Atom.RULE);
        return Atom.of(element);
    }

    public List<Trailer> trailerList() {
        if (trailerList != null) {
            return trailerList;
        }
        List<Trailer> result = null;
        var element = getItem(2);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Trailer.of(node));
        }
        trailerList = result == null ? Collections.emptyList() : result;
        return trailerList;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("await");
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
