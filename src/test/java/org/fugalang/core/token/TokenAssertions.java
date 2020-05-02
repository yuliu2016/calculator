package org.fugalang.core.token;

import org.junit.jupiter.api.Assertions;

import java.util.List;

public class TokenAssertions {
    public static void assertType(List<Token> tokens, int i, TokenType expectedType) {
        var actualType = tokens.get(i).type;

        Assertions.assertEquals(expectedType, actualType, () -> "Token at #" + i);
    }

    public static void assertValue(List<Token> tokens, int i, String expectedValue) {
        var actualValue = tokens.get(i).value;
        Assertions.assertEquals(expectedValue, actualValue, () -> "Token at #" + i);
    }
}
