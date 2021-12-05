package org.fugalang.grammar.common;

public record ResultSource(
        Kind kind,
        Object value
) {
    public enum Kind {
        UnitRule,
        TokenType,
        TokenLiteral
    }

    public boolean isUnitRule() {
        return kind == Kind.UnitRule;
    }

    public boolean isTokenType() {
        return kind == Kind.TokenType;
    }

    public boolean isTokenLiteral() {
        return kind == Kind.TokenLiteral;
    }

    public RuleName asRuleName() {
        return (RuleName) value;
    }

    public TokenEntry asTokenEntry() {
        return (TokenEntry) value;
    }
}
