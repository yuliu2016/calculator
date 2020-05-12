package org.fugalang.core.token;

import org.junit.jupiter.api.Test;

import static org.fugalang.core.token.CharTest.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharTestTest {
    @Test
    public void testChars() {
        assertTrue(isUnderscore('_'));
    }

    @Test
    public void testNumericeChars() {
        assertTrue(isAnyHex('a'));
        assertTrue(isAnyHex('f'));
        assertTrue(isAnyHex('A'));
        assertTrue(isAnyHex('F'));
        assertFalse(isAnyHex('G'));
        assertFalse(isAnyHex('g'));

        assertTrue(isNumeric('0'));
        assertTrue(isNumeric('9'));

        assertTrue(isAnyOct('0'));
        assertTrue(isAnyOct('7'));
        assertFalse(isAnyOct('8'));
        assertFalse(isAnyOct('9'));
    }

    @Test
    public void testSpaceChars() {
        assertTrue(isSpace(' '));
        assertTrue(isSpace('\n'));
        assertTrue(isSpace('\t'));
        assertTrue(isSpace('\r'));
    }
}
