package org.fugalang.grammar.main;

import org.fugalang.grammar.common.GrammarSpec;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CCalcGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/CCalcGrammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "vscode/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser2.c");

    public static void main(String[] args) throws Exception {
        GrammarSpec spec = RuleSetBuilder.generate(
                GeneratorUtil.readPreprocessed(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.tokenMap
        );
        GeneratorUtil.printStats(spec);

        String parser = CTransform.generateParser(spec);
        Files.writeString(C_PATH, parser.replace("\n", System.lineSeparator()));
    }
}
