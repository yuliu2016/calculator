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
            case "NEWLINE" -> Optional.of(new ConvertedValue("boolean", "newline"));
            case "TOK" -> Optional.of(new ConvertedValue("String", "token"));
            case ":" -> Optional.of(new ConvertedValue("boolean", "colon"));
            case "(" -> Optional.of(new ConvertedValue("boolean", "lpar"));
            case ")" -> Optional.of(new ConvertedValue("boolean", "rpar"));
            case "[" -> Optional.of(new ConvertedValue("boolean", "lsqb"));
            case "]" -> Optional.of(new ConvertedValue("boolean", "rsqb"));
            case "+" -> Optional.of(new ConvertedValue("boolean", "plus"));
            case "*" -> Optional.of(new ConvertedValue("boolean", "star"));
            case "|" -> Optional.of(new ConvertedValue("boolean", "or"));
            default -> Optional.empty();
        };
    }
}
