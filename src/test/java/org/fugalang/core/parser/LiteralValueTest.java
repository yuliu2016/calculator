package org.fugalang.core.parser;

import org.junit.jupiter.api.Test;

import static org.fugalang.core.parser.ParserTests.assertDoesNotParse;
import static org.fugalang.core.parser.ParserTests.assertParse;

public class LiteralValueTest {
    @Test
    public void testStringifiedParenthesis() {
        assertDoesNotParse("'('0)");
    }

    @Test
    public void testStringifiedSqb() {
        assertDoesNotParse("'['0)");
    }

    @Test
    public void testParMultiLayer() {
        assertParse("('(')");
    }
}
