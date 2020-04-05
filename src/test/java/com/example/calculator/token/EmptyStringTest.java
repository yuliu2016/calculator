package com.example.calculator.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyStringTest {
    @Test
    public void testEmptyString() {
        var result = new Tokenizer("").tokenizeAll();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testSingleCharComment() {
        var result = new Tokenizer("#").tokenizeAll();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testMultiCharComment() {
        var result = new Tokenizer("#comment").tokenizeAll();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testMultiLineComment() {
        var result = new Tokenizer("/* comment */").tokenizeAll();
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testMultiSingleLineComment() {
        var result = new Tokenizer("#x\n#y").tokenizeAll();
        Assertions.assertEquals(0, result.size());
    }
}
