package org.fugalang.grammar.common;

public record ResultSource(SourceKind kind, Object value) {

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
