package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//='
 */
public final class Augassign extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("augassign", RuleType.Disjunction, true);

    public static Augassign of(ParseTreeNode node) {
        return new Augassign(node);
    }

    private Augassign(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isTokenPlusAssign() {
        return getBoolean(0);
    }

    public boolean isTokenMinusAssign() {
        return getBoolean(1);
    }

    public boolean isTokenTimesAssign() {
        return getBoolean(2);
    }

    public boolean isTokenMatrixTimesAssign() {
        return getBoolean(3);
    }

    public boolean isTokenDivAssign() {
        return getBoolean(4);
    }

    public boolean isTokenModulusAssign() {
        return getBoolean(5);
    }

    public boolean isTokenBitAndAssign() {
        return getBoolean(6);
    }

    public boolean isTokenBitOrAssign() {
        return getBoolean(7);
    }

    public boolean isTokenBitXorAssign() {
        return getBoolean(8);
    }

    public boolean isTokenLshiftAssign() {
        return getBoolean(9);
    }

    public boolean isTokenRshiftAssign() {
        return getBoolean(10);
    }

    public boolean isTokenPowerAssign() {
        return getBoolean(11);
    }

    public boolean isTokenFloorDivAssign() {
        return getBoolean(12);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("+=");
        result = result || parseTree.consumeToken("-=");
        result = result || parseTree.consumeToken("*=");
        result = result || parseTree.consumeToken("@=");
        result = result || parseTree.consumeToken("/=");
        result = result || parseTree.consumeToken("%=");
        result = result || parseTree.consumeToken("&=");
        result = result || parseTree.consumeToken("|=");
        result = result || parseTree.consumeToken("^=");
        result = result || parseTree.consumeToken("<<=");
        result = result || parseTree.consumeToken(">>=");
        result = result || parseTree.consumeToken("**=");
        result = result || parseTree.consumeToken("//=");

        parseTree.exit(result);
        return result;
    }
}
