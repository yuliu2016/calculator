package org.fugalang.core.grammar.gen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserStringUtilTest {
    @Test
    public void testSplitLines() {
        var split = ParserStringUtil.splitLines("a\nb\nc\nd");
        Assertions.assertEquals(4, split.size());

        split = ParserStringUtil.splitLines("a\nb\nc\nd\n");
        Assertions.assertEquals(5, split.size());

        split = ParserStringUtil.splitLines("\n\n\nf");
        Assertions.assertEquals(4, split.size());

    }
}
