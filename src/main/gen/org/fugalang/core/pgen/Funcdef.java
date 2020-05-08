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

    @Override
    protected void buildRule() {
        addRequired(isTokenDef(), "def");
        addOptional(funcTypeHintOrNull());
        addOptional(funcArgsOrNull());
        addRequired(funcSuite());
    }

    public boolean isTokenDef() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public FuncTypeHint funcTypeHint() {
        var element = getItem(1);
        element.failIfAbsent(FuncTypeHint.RULE);
        return FuncTypeHint.of(element);
    }

    public FuncTypeHint funcTypeHintOrNull() {
        var element = getItem(1);
        if (!element.isPresent(FuncTypeHint.RULE)) {
            return null;
        }
        return FuncTypeHint.of(element);
    }

    public boolean hasFuncTypeHint() {
        var element = getItem(1);
        return element.isPresent(FuncTypeHint.RULE);
    }

    public FuncArgs funcArgs() {
        var element = getItem(2);
        element.failIfAbsent(FuncArgs.RULE);
        return FuncArgs.of(element);
    }

    public FuncArgs funcArgsOrNull() {
        var element = getItem(2);
        if (!element.isPresent(FuncArgs.RULE)) {
            return null;
        }
        return FuncArgs.of(element);
    }

    public boolean hasFuncArgs() {
        var element = getItem(2);
        return element.isPresent(FuncArgs.RULE);
    }

    public FuncSuite funcSuite() {
        var element = getItem(3);
        element.failIfAbsent(FuncSuite.RULE);
        return FuncSuite.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("def");
        if (result) FuncTypeHint.parse(parseTree, level + 1);
        if (result) FuncArgs.parse(parseTree, level + 1);
        result = result && FuncSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
