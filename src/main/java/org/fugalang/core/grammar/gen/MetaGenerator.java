package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.Rules;
import org.fugalang.core.parser.SimpleParseTree;
import org.fugalang.core.parser.context.LazyParserContext;
import org.fugalang.core.parser.context.LexingVisitor;
import org.fugalang.core.token.SimpleLexer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MetaGenerator {
    public static void main(String[] args) {
        var res = MetaGenerator.class.getResource("/org/fugalang/core/grammar/MetaGrammar");
        try {
            var data = Files.readString(Paths.get(res.toURI()));

            var visitor = LexingVisitor.of(data);
            var lexer = SimpleLexer.of(visitor);
            var context = LazyParserContext.of(lexer, visitor, false);
            var tree = SimpleParseTree.parse(context, Rules::parse, Rules::of);

            var path = Paths.get(
                    System.getProperty("user.dir"),
                    "src/main/gen/org/fugalang/core/grammar/pgen/"
            );

            var gen = new PEGBuilder(tree, FugaGenerator::checkToken,
                    path, "org.fugalang.core.grammar.pgen",
                    "org.fugalang.core.token.TokenType");
            gen.generate(true);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
