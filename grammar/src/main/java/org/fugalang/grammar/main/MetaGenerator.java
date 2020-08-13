package org.fugalang.grammar.main;

import org.fugalang.grammar.gen.PEGBuilder;
import org.fugalang.grammar.gen.PackageOutput;

public class MetaGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/MetaGrammar";
    private static final PackageOutput PACKAGE_OUTPUT = new PackageOutput(USER_DIR,
            "grammar/src/main/gen", "org.fugalang.grammar.peg", "Meta");

    public static void main(String[] args) throws Exception {
        new PEGBuilder(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.simpleConverter(),
                PACKAGE_OUTPUT
        ).generate(true);
    }
}
