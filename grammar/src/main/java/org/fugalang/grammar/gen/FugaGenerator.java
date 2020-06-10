package org.fugalang.grammar.gen;

public class FugaGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";
    private static final PackageOutput PACKAGE_OUTPUT = new PackageOutput(USER_DIR,
            "src/main/gen", "org.fugalang.core.peg", "Fuga");

    public static void main(String[] args) throws Exception {
        new PEGBuilder(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.simpleConverter(),
                PACKAGE_OUTPUT
        ).generate(true);
    }
}
