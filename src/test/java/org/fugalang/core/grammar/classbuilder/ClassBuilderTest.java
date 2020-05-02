package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.ConvertedValue;
import org.fugalang.core.grammar.gen.ParserGenerator;
import org.fugalang.core.grammar.parser.MetaParser;
import org.fugalang.core.grammar.token.MetaLexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Optional;

public class ClassBuilderTest {
    @Test
    public void testGuardEmptyConjunction() {
        var tokens = new MetaLexer("test_rule: 'elem'*\n").tokenize();
        var cst = MetaParser.parseRules(tokens);

        var gen = new ParserGenerator(cst, tok -> tok.equals("elem") ?
                Optional.of(new ConvertedValue("String", "elem", "elem")) :
                Optional.empty(),
                Path.of(""), "test");

        Assertions.assertThrows(IllegalStateException.class, () -> gen.generate(false));
    }
}
