package org.fugalang.core.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmptyStringTest {
    @Test
    public void testEmptyString() {
        var result = LexerTests.tokenize("");
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testSingleCharComment() {
        var result = LexerTests.tokenize("#");
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testMultiCharComment() {
        var result = LexerTests.tokenize("#comment");
        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testMultiSingleLineComment() {
        var result = LexerTests.tokenize("#x\n#y");
        Assertions.assertEquals(1, result.size());
    }
}
