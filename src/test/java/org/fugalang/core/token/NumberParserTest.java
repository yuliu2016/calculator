package org.fugalang.core.token;

import org.junit.jupiter.api.Test;

import static org.fugalang.core.token.TokenAssertions.assertType;
import static org.fugalang.core.token.TokenAssertions.assertValue;

public class NumberParserTest {

    @Test
    public void testParseInteger() {
        var result = new Tokenizer("123").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 123);
    }


//    @Test
    public void testParseIntegerUnderscore() {
        var result = new Tokenizer("12_3").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 123);
    }


    @Test
    public void testParseFloat() {
        var result = new Tokenizer("1.23").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 1.23);
    }

    @Test
    public void testParseFloatComplex() {
        var result = new Tokenizer("1.23j").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 1.23);
    }

    @Test
    public void testParseIntComplex() {
        var result = new Tokenizer("123j").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 123.0);
    }

//    @Test
    public void testFloatExp() {
        var result = new Tokenizer("1E5").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 1E5);
    }


//    @Test
    public void testFloatExp2() {
        var result = new Tokenizer("1.23E-5").tokenizeAll();

        assertType(result, 0, TokenType.NUMBER);
        assertValue(result, 0, 1.23E-5);
    }
}
