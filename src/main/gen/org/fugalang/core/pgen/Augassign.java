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

    @Override
    protected void buildRule() {
        addChoice("isTokenPlusAssign", isTokenPlusAssign());
        addChoice("isTokenMinusAssign", isTokenMinusAssign());
        addChoice("isTokenTimesAssign", isTokenTimesAssign());
        addChoice("isTokenMatrixTimesAssign", isTokenMatrixTimesAssign());
        addChoice("isTokenDivAssign", isTokenDivAssign());
        addChoice("isTokenModulusAssign", isTokenModulusAssign());
        addChoice("isTokenBitAndAssign", isTokenBitAndAssign());
        addChoice("isTokenBitOrAssign", isTokenBitOrAssign());
        addChoice("isTokenBitXorAssign", isTokenBitXorAssign());
        addChoice("isTokenLshiftAssign", isTokenLshiftAssign());
        addChoice("isTokenRshiftAssign", isTokenRshiftAssign());
        addChoice("isTokenPowerAssign", isTokenPowerAssign());
        addChoice("isTokenFloorDivAssign", isTokenFloorDivAssign());
    }

    public boolean isTokenPlusAssign() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public boolean isTokenMinusAssign() {
        var element = getItem(1);
        return element.asBoolean();
    }

    public boolean isTokenTimesAssign() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public boolean isTokenMatrixTimesAssign() {
        var element = getItem(3);
        return element.asBoolean();
    }

    public boolean isTokenDivAssign() {
        var element = getItem(4);
        return element.asBoolean();
    }

    public boolean isTokenModulusAssign() {
        var element = getItem(5);
        return element.asBoolean();
    }

    public boolean isTokenBitAndAssign() {
        var element = getItem(6);
        return element.asBoolean();
    }

    public boolean isTokenBitOrAssign() {
        var element = getItem(7);
        return element.asBoolean();
    }

    public boolean isTokenBitXorAssign() {
        var element = getItem(8);
        return element.asBoolean();
    }

    public boolean isTokenLshiftAssign() {
        var element = getItem(9);
        return element.asBoolean();
    }

    public boolean isTokenRshiftAssign() {
        var element = getItem(10);
        return element.asBoolean();
    }

    public boolean isTokenPowerAssign() {
        var element = getItem(11);
        return element.asBoolean();
    }

    public boolean isTokenFloorDivAssign() {
        var element = getItem(12);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("+=");
        result = result || parseTree.consumeTokenLiteral("-=");
        result = result || parseTree.consumeTokenLiteral("*=");
        result = result || parseTree.consumeTokenLiteral("@=");
        result = result || parseTree.consumeTokenLiteral("/=");
        result = result || parseTree.consumeTokenLiteral("%=");
        result = result || parseTree.consumeTokenLiteral("&=");
        result = result || parseTree.consumeTokenLiteral("|=");
        result = result || parseTree.consumeTokenLiteral("^=");
        result = result || parseTree.consumeTokenLiteral("<<=");
        result = result || parseTree.consumeTokenLiteral(">>=");
        result = result || parseTree.consumeTokenLiteral("**=");
        result = result || parseTree.consumeTokenLiteral("//=");

        parseTree.exit(level, marker, result);
        return result;
    }
}
