package org.fugalang.grammar.classbuilder;


public class ResultSource {

    private final SourceType sourceType;
    private final String value;

    private ResultSource(SourceType sourceType, String value) {
        this.sourceType = sourceType;
        this.value = value;
    }

    public SourceType getType() {
        return sourceType;
    }

    public String getValue() {
        return value;
    }

    public static ResultSource ofClass(ClassName className) {
        return new ResultSource(SourceType.Class, className.getRuleName());
    }

    public static ResultSource ofTokenType(String tokenType) {
        return new ResultSource(SourceType.TokenType, tokenType);
    }

    public static ResultSource ofTokenLiteral(String literal) {
        return new ResultSource(SourceType.TokenLiteral, literal);
    }
}
