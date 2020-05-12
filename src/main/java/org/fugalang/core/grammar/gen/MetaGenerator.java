package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.pgen.Rules;
import org.fugalang.core.grammar.token.MetaLexer;
import org.fugalang.core.parser.SimpleParseTree;
import org.fugalang.core.parser.context.SimpleContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class MetaGenerator {
    public static void main(String[] args) {
        var res = MetaGenerator.class.getResource("/org/fugalang/core/grammar/MetaGrammar");
        try {
            var data = Files.readString(Paths.get(res.toURI()));
            var tokens = new MetaLexer(data).tokenize();

            var context = new SimpleContext(tokens, false);
            var tree = SimpleParseTree.parse(context, Rules::parse, Rules::of);

            var path = Paths.get(
                    System.getProperty("user.dir"),
                    "src/main/gen/org/fugalang/core/grammar/pgen/"
            );

            var gen = new PEGBuilder(tree, MetaGenerator::checkToken,
                    path, "org.fugalang.core.grammar.pgen",
                    "org.fugalang.core.grammar.token.MetaTokenType");
            gen.generate(true);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Optional<ConvertedValue> checkToken(String s) {
        return switch (s) {
            case "NEWLINE" -> of("newline", "NEWLINE");
            case "TOK" -> of("token", "TOK");
            case ":" -> of("colon", "COL");
            case "(" -> of("lpar", "LPAR");
            case ")" -> of("rpar", "RPAR");
            case "[" -> of("lsqb", "LSQB");
            case "]" -> of("rsqb", "RSQB");
            case "+" -> of("plus", "PLUS");
            case "*" -> of("star", "STAR");
            case "|" -> of("or", "OR");
            default -> Optional.empty();
        };
    }

    private static Optional<ConvertedValue> of(String fieldName, String literal) {
        return Optional.of(new ConvertedValue("String", fieldName, literal));
    }
}
