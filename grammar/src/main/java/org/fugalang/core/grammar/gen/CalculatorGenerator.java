package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.Rules;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.token.SimpleLexer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CalculatorGenerator {
    public static void main(String[] args) {
        var userDir = System.getProperty("user.dir");
        var grammar = Paths.get(userDir, "src/main/files/CalculatorGrammar");
        try {
            var data = Files.readString(grammar);
            var visitor = LexingVisitor.of(data);
            var lexer = SimpleLexer.of(visitor);
            var context = LazyParserContext.of(lexer, visitor, false);
            var tree = SimpleParseTree.parse(context, Rules::parse, Rules::of);
            var path = Paths.get(userDir, "src/main/gen/org/fugalang/core/calculator/pgen/");
            var gen = new PEGBuilder(tree, new ConverterImpl(),
                    path, "org.fugalang.core.calculator.pgen",
                    "org.fugalang.core.token.TokenType");
            gen.generate(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
