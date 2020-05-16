package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * augassign: '+=' | '-=' | '*=' | '@=' | '/=' | '%=' | '&=' | '|=' | '^=' | '<<=' | '>>=' | '**=' | '//='
 */
public final class Augassign extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("augassign", RuleType.Disjunction);

    public static Augassign of(ParseTreeNode node) {
        return new Augassign(node);
    }

    private Augassign(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isPlusAssign() {
        return is(0);
    }

    public boolean isMinusAssign() {
        return is(1);
    }

    public boolean isTimesAssign() {
        return is(2);
    }

    public boolean isMatrixTimesAssign() {
        return is(3);
    }

    public boolean isDivAssign() {
        return is(4);
    }

    public boolean isModulusAssign() {
        return is(5);
    }

    public boolean isBitAndAssign() {
        return is(6);
    }

    public boolean isBitOrAssign() {
        return is(7);
    }

    public boolean isBitXorAssign() {
        return is(8);
    }

    public boolean isLshiftAssign() {
        return is(9);
    }

    public boolean isRshiftAssign() {
        return is(10);
    }

    public boolean isPowerAssign() {
        return is(11);
    }

    public boolean isFloorDivAssign() {
        return is(12);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("+=");
        r = r || t.consume("-=");
        r = r || t.consume("*=");
        r = r || t.consume("@=");
        r = r || t.consume("/=");
        r = r || t.consume("%=");
        r = r || t.consume("&=");
        r = r || t.consume("|=");
        r = r || t.consume("^=");
        r = r || t.consume("<<=");
        r = r || t.consume(">>=");
        r = r || t.consume("**=");
        r = r || t.consume("//=");
        t.exit(r);
        return r;
    }
}
