package org.fugalang.core.parser;

import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.peg.parser.FugaParser;
import org.fugalang.core.peg.wrapper.*;
import org.fugalang.core.token.SimpleLexer;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
public class ParserTests {
    public static void assertParse(String s) {
        assertDoesNotThrow(() -> parse(s));
    }

    public static void assertDoesNotParse(String s) {
        assertThrows(Exception.class, () -> parse(s));
    }

    private static void parse(String s) {
        parse(s, FugaParser::single_input, SingleInput::new);
    }

    private static void parseFile(String s) {
        parse(s, FugaParser::file_input, FileInput::new);
    }

    private static void parseEval(String s) {
        parse(s, FugaParser::eval_input, EvalInput::new);
    }

    private static void parseExpr(String s) {
        parse(s, FugaParser::expr, Expr::new);
    }

    private static void parseAtom(String s) {
        parse(s, FugaParser::atom, Atom::new);
    }

    private static <T> void parse(
            String s, Function<ParseTree, Boolean> start,
            Function<ParseTreeNode, T> converter) {
        var visitor = LexingVisitor.of(s);
        var lexer = SimpleLexer.of(visitor);
        var context = LazyParserContext.of(lexer, visitor);
        converter.apply(SimpleParseTree.parse(context, start));
    }
}
