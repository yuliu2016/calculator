package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.parser.MgParser;
import org.fugalang.core.grammar.token.MgTokenizer;

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
            var tokens = new MgTokenizer(data).tokenize();

            var cst = MgParser.parseRules(tokens);

            var path = Paths.get(
                    System.getProperty("user.dir"),
                    "src/main/gen/org/fugalang/core/grammar/pgen/"
            );

            var gen = new ParserGenerator(cst, MetaGenerator::checkToken,
                    path, "org.fugalang.core.grammar.pgen");
            gen.generate(true);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Optional<ConvertedValue> checkToken(String s) {
        return switch (s) {
            case "NEWLINE" -> of("boolean", "newline", "NEWLINE");
            case "TOK" -> of("String", "token", "TOK");
            case ":" -> of("boolean", "colon", ":");
            case "(" -> of("boolean", "lpar", "(");
            case ")" -> of("boolean", "rpar", ")");
            case "[" -> of("boolean", "lsqb", "[");
            case "]" -> of("boolean", "rsqb", "]");
            case "+" -> of("boolean", "plus", "+");
            case "*" -> of("boolean", "star", "*");
            case "|" -> of("boolean", "or", "|");
            default -> Optional.empty();
        };
    }

    private static Optional<ConvertedValue> of(String className, String fieldName, String literal) {
        return Optional.of(new ConvertedValue(className, fieldName, literal));
    }
}
