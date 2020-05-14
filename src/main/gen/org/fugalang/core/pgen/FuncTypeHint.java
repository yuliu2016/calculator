package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_type_hint: '<' 'expr' '>'
 */
public final class FuncTypeHint extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("func_type_hint", RuleType.Conjunction, true);

    public static FuncTypeHint of(ParseTreeNode node) {
        return new FuncTypeHint(node);
    }

    private FuncTypeHint(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("<");
        result = result && Expr.parse(parseTree, level + 1);
        result = result && parseTree.consumeToken(">");

        parseTree.exit(level, marker, result);
        return result;
    }
}
