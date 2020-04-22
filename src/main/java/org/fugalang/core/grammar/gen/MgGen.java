package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.token.MgTokenizer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MgGen {
    public static void main(String[] args) {
        var res = MgGen.class.getResource("/org/fugalang/core/grammar/MetaGrammar");
        try {
            var data = Files.readString(Paths.get(res.toURI()));
            new MgTokenizer(data).tokenize().forEach(System.out::print);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
