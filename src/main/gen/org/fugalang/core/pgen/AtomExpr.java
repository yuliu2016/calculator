package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * atom_expr: ['await'] 'atom'
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

    @Override
    protected void buildRule() {
        addOptional(isTokenAwait(), "await");
        addRequired(atom());
    }

    public boolean isTokenAwait() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Atom atom() {
        var element = getItem(1);
        element.failIfAbsent(Atom.RULE);
        return Atom.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        parseTree.consumeToken("await");
        result = Atom.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
