package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.Rules;
import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.token.SimpleLexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneratorUtil {
    public static Rules readRules(String base, String grammarPath) throws IOException {
        String data;
        data = Files.readString(Paths.get(base, grammarPath));
        var visitor = LexingVisitor.of(data);
        var lexer = SimpleLexer.of(visitor);
        var context = LazyParserContext.of(lexer, visitor, false);
        return SimpleParseTree.parse(context, Rules::parse, Rules::of);
    }

    public static TokenConverter simpleConverter() {
        return new SimpleConverter();
    }
}
