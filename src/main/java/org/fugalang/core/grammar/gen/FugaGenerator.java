package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.parser.MetaParser;
import org.fugalang.core.grammar.token.MetaLexer;
import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.TokenType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FugaGenerator {
    public static void main(String[] args) {
        var res = FugaGenerator.class.getResource("/org/fugalang/core/grammar/Grammar");
        try {
            var data = Files.readString(Paths.get(res.toURI()));
            var tokens = new MetaLexer(data).tokenize();

            var cst = MetaParser.parseRules(tokens);

            var path = Paths.get(
                    System.getProperty("user.dir"),
                    "src/main/gen/org/fugalang/core/pgen/"
            );

            var gen = new ParserGenerator(cst, FugaGenerator::checkToken,
                    path, "org.fugalang.core.pgen",
                    "org.fugalang.core.token.TokenType");
            gen.generate(true);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Optional<ConvertedValue> checkToken(String s) {

        if (Keyword.ALL_KEYWORDS.contains(s)) {
            return Optional.of(new ConvertedValue("boolean", s, s));
        }
        if (Operator.CODE_TO_NAME.containsKey(s)) {
            var name = ParserStringUtil.convertCase(Operator.CODE_TO_NAME.get(s));
            return Optional.of(new ConvertedValue("boolean", name, s));
        }

        if (TokenType.NAMES.contains(s)) {
            return Optional.of(new ConvertedValue("String", s.toLowerCase(), s));
        }

        return Optional.empty();
    }
}
