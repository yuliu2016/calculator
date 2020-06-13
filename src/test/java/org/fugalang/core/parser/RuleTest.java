package org.fugalang.core.parser;

import org.junit.jupiter.api.Test;

import static org.fugalang.core.parser.ParserTests.assertDoesNotParse;
import static org.fugalang.core.parser.ParserTests.assertParse;

public class RuleTest {
    @Test
    public void testArgWithExpr() {
        assertParse("func(0)");
    }

    @Test
    public void testArgWithNamedExpr() {
        assertParse("func(a:=2)");
    }

    @Test
    public void testOnePlus() {
        assertDoesNotParse("1+");
    }

    @Test
    public void testAssignExprOnly() {
        assertParse("1");
    }

    @Test
    public void testNamedExprInList() {
        assertParse("[a:=b]");
    }

    @Test
    public void testNameOnlyAssign() {
        assertParse("a = 2");
    }

    @Test
    public void testStarAssign() {
        assertDoesNotParse("*a = 2");
    }

    @Test
    public void testSubscriptAssign() {
        assertParse("a[b] = c");
    }

    @Test
    public void testAttrAssign() {
        assertParse("a.b = c");
    }
}
