package org.fugalang.grammar.main;

import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.token.SimpleLexer;
import org.fugalang.grammar.gen.TokenConverter;
import org.fugalang.grammar.peg.parser.MetaParser;
import org.fugalang.grammar.peg.wrapper.Grammar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneratorUtil {
    public static Grammar readGrammar(String base, String grammarPath) throws IOException {
        String data;
        data = Files.readString(Paths.get(base, grammarPath));
        var visitor = LexingVisitor.of(data);
        var lexer = SimpleLexer.of(visitor);
        var context = LazyParserContext.of(lexer, visitor);
        var node = SimpleParseTree.parse(context, MetaParser::grammar);
        return new Grammar(node);
    }

    public static TokenConverter simpleConverter() {
        return new SimpleConverter();
    }
}
