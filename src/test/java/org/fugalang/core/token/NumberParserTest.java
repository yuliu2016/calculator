package org.fugalang.core.token;

import org.junit.jupiter.api.Test;

import static org.fugalang.core.token.LexerTests.assertType;
import static org.fugalang.core.token.LexerTests.assertValue;

public class NumberParserTest {

    @Test
    public void testParseInteger() {
        var result = LexerTests.tokenize("123");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "123");
    }


    @Test
    public void testParseIntegerUnderscore() {
        var result = LexerTests.tokenize("12_3");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "12_3");
    }


    @Test
    public void testParseFloat() {
        var result = LexerTests.tokenize("1.23");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "1.23");
    }

    @Test
    public void testParseFloatComplex() {
        var result = LexerTests.tokenize("1.23j");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "1.23j");
    }

    @Test
    public void testParseIntComplex() {
        var result = LexerTests.tokenize("123j");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "123j");
    }

    @Test
    public void testFloatExp() {
        var result = LexerTests.tokenize("1E5");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "1E5");
    }


    @Test
    public void testFloatExp2() {
        var result = LexerTests.tokenize("1.23E-5");

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, "1.23E-5");
    }
}
