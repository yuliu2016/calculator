package org.fugalang.grammar.common;

public class ResultSource {

    private final SourceKind sourceKind;
    private final Object value;

    private ResultSource(SourceKind sourceKind, Object value) {
        this.sourceKind = sourceKind;
        this.value = value;
    }

    public SourceKind getType() {
        return sourceKind;
    }

    public Object getValue() {
        return value;
    }

    public static ResultSource ofUnitRule(RuleName ruleName) {
        return new ResultSource(SourceKind.UnitRule, ruleName);
    }

    public static ResultSource ofTokenType(TokenEntry tokenType) {
        return new ResultSource(SourceKind.TokenType, tokenType);
    }

    public static ResultSource ofTokenLiteral(TokenEntry literal) {
        return new ResultSource(SourceKind.TokenLiteral, literal);
    }
}
