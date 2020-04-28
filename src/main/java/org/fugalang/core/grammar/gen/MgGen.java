package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.parser.MgParser;
import org.fugalang.core.grammar.psi.Rules;
import org.fugalang.core.grammar.token.MgTokenizer;
import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.TokenType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MgGen {
    public static void main(String[] args) {
        var res = MgGen.class.getResource("/org/fugalang/core/grammar/Grammar");
        try {
            var data = Files.readString(Paths.get(res.toURI()));
            var tokens = new MgTokenizer(data).tokenize();

            Rules cst = MgParser.parseRules(tokens);

            ParserGenerator gen = new ParserGenerator(cst, s -> {
                return TokenType.NAMES.contains(s)
                        || Keyword.ALL_KEYWORDS.contains(s)
                        || Operator.CODES.contains(s);
            });

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
