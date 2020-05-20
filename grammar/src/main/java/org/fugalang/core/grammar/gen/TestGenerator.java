package org.fugalang.core.grammar.gen;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final Path GRAMMAR_PATH = Paths.get(USER_DIR, "src/main/files/TestGrammar");
    private static final Path OUTPUT_PATH = Paths.get(USER_DIR,
            "grammar/src/main/gen/org/fugalang/core/grammar/test/");
    private static final String PACKAGE_NAME = "org.fugalang.core.grammar.test";
    private static final String TOKEN_TYPE_CLASS = "org.fugalang.core.token.TokenType";

    public static void main(String[] args) throws Exception {
        new PEGBuilder(
                GeneratorUtil.readRules(GRAMMAR_PATH),
                GeneratorUtil.simpleConverter(),
                OUTPUT_PATH,
                PACKAGE_NAME,
                TOKEN_TYPE_CLASS
        ).generate(true);
    }
}
