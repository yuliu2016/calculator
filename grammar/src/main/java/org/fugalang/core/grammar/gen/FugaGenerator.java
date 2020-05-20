package org.fugalang.core.grammar.gen;

public class FugaGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";
    private static final String TOKEN_TYPE_CLASS = "org.fugalang.core.token.TokenType";
    private static final PackageOutput PACKAGE_OUTPUT = new PackageOutput(USER_DIR,
            "src/main/gen", "org.fugalang.core.pgen");

    public static void main(String[] args) throws Exception {
        new PEGBuilder(
                GeneratorUtil.readRules(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.simpleConverter(),
                PACKAGE_OUTPUT,
                TOKEN_TYPE_CLASS
        ).generate(true);
    }
}
