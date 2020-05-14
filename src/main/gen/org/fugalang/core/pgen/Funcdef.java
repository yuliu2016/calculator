package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * funcdef: 'def' ['func_type_hint'] ['func_args'] 'func_suite'
 */
public final class Funcdef extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("funcdef", RuleType.Conjunction, true);

    public static Funcdef of(ParseTreeNode node) {
        return new Funcdef(node);
    }

    private Funcdef(ParseTreeNode node) {
        super(RULE, node);
    }

    public FuncTypeHint funcTypeHint() {
        return FuncTypeHint.of(getItem(1));
    }

    public boolean hasFuncTypeHint() {
        return hasItemOfRule(1, FuncTypeHint.RULE);
    }

    public FuncArgs funcArgs() {
        return FuncArgs.of(getItem(2));
    }

    public boolean hasFuncArgs() {
        return hasItemOfRule(2, FuncArgs.RULE);
    }

    public FuncSuite funcSuite() {
        return FuncSuite.of(getItem(3));
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("def");
        if (result) FuncTypeHint.parse(parseTree, level + 1);
        if (result) FuncArgs.parse(parseTree, level + 1);
        result = result && FuncSuite.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}
