package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.Rules;
import org.fugalang.core.grammar.token.MetaLexer;
import org.fugalang.core.parser.SimpleParseTree;
import org.fugalang.core.parser.context.SimpleContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CalculatorGenerator {
    public static void main(String[] args) {
        var res = CalculatorGenerator.class.getResource("/org/fugalang/core/grammar/CalculatorGrammar");
        try {
            var data = Files.readString(Paths.get(res.toURI()));
            var tokens = new MetaLexer(data).tokenize();

            var context = new SimpleContext(tokens, false);
            var tree = SimpleParseTree.parse(context, Rules::parse, Rules::of);

            var path = Paths.get(
                    System.getProperty("user.dir"),
                    "src/main/gen/org/fugalang/core/calculator/pgen/"
            );

            var gen = new PEGBuilder(tree, FugaGenerator::checkToken,
                    path, "org.fugalang.core.calculator.pgen",
                    "org.fugalang.core.token.TokenType");

            gen.generate(true);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
