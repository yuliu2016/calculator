package org.fugalang.grammar.gen;

import org.fugalang.grammar.util.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {
    @Test
    public void testSplitLines() {
        var split = StringUtil.splitLines("a\nb\nc\nd");
        Assertions.assertEquals(4, split.size());

        split = StringUtil.splitLines("a\nb\nc\nd\n");
        Assertions.assertEquals(5, split.size());

        split = StringUtil.splitLines("\n\n\nf");
        Assertions.assertEquals(4, split.size());

    }
}
