package org.fugalang.core.parser;

import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.pgen.SingleInput;
import org.fugalang.core.pgen.parser.FugaParser;
import org.fugalang.core.token.SimpleLexer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTests {
    public static void assertParse(String s) {
        assertDoesNotThrow(() -> parse(s));
    }

    public static void assertDoesNotParse(String s) {
        assertThrows(Exception.class, () -> parse(s));
    }

    private static void parse(String s) {
        var visitor = LexingVisitor.of(s);
        var lexer = SimpleLexer.of(visitor);
        var context = LazyParserContext.of(lexer, visitor, false);
        SimpleParseTree.parse(context, FugaParser::single_input, SingleInput::new);
    }
}
