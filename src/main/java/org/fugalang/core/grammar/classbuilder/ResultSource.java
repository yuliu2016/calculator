package org.fugalang.core.grammar.classbuilder;


public class ResultSource {
    public enum SourceType {
        Class,
        TokenType,
        TokenLiteral
    }

    private final SourceType sourceType;
    private final String value;

    private ResultSource(SourceType sourceType, String value) {
        this.sourceType = sourceType;
        this.value = value;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public String getValue() {
        return value;
    }

    public static ResultSource ofClass(String className) {
        return new ResultSource(SourceType.Class, className);
    }

    public static ResultSource ofClass(ClassName className) {
        return ofClass(className.asType());
    }

    public static ResultSource ofTokenType(String tokenType) {
        return new ResultSource(SourceType.TokenType, tokenType);
    }

    public static ResultSource ofTokenLiteral(String literal) {
        return new ResultSource(SourceType.TokenLiteral, literal);
    }
}