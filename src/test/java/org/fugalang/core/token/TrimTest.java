package org.fugalang.core.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrimTest {
    @Test
    public void testTrimSpace() {
        var result = new Tokenizer(" 1+1 ").tokenizeAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void testTrimNewline() {
        var result = new Tokenizer("\n1+1\n").tokenizeAll();
        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void testDiscardOperatorSpace() {
        var result = new Tokenizer("1 + 1").tokenizeAll();
        Assertions.assertEquals(3, result.size());
    }
}
