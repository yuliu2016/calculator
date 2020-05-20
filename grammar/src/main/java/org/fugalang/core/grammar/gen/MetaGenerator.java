package org.fugalang.core.grammar.gen;

public class MetaGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/MetaGrammar";
    private static final String TOKEN_TYPE_CLASS = "org.fugalang.core.token.TokenType";
    private static final PackageOutput PACKAGE_OUTPUT = new PackageOutput(USER_DIR,
            "grammar/src/main/gen", "org.fugalang.core.grammar.pgen");

    public static void main(String[] args) throws Exception {
        new PEGBuilder(
                GeneratorUtil.readRules(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.simpleConverter(),
                PACKAGE_OUTPUT,
                TOKEN_TYPE_CLASS
        ).generate(true);
    }
}
