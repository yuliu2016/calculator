package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.context.LazyArrayList;
import org.fugalang.core.parser.context.LexingVisitor;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class LexerTests {
    public static void assertType(List<ParserElement> tokens, int i, ElementType expectedType) {
        var actualType = tokens.get(i).getType();

        Assertions.assertEquals(expectedType, actualType, () -> "Token at #" + i);
    }

    public static void assertValue(List<ParserElement> tokens, int i, String expectedValue) {
        var actualValue = tokens.get(i).getValue();
        Assertions.assertEquals(expectedValue, actualValue, () -> "Token at #" + i);
    }

    public static List<ParserElement> tokenize(String code) {
        var c = LexingVisitor.of(code);
        var r = SimpleLexer.of(c);
        return new LazyArrayList<>(r).getInnerList();
    }
}
