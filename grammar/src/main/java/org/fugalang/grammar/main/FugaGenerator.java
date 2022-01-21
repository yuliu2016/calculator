package org.fugalang.grammar.main;

import org.fugalang.grammar.common.GrammarSpec;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.JPackageOutput;
import org.fugalang.grammar.transform.JGenerator;

public class FugaGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";
    private static final JPackageOutput PACKAGE_OUTPUT = new JPackageOutput(USER_DIR,
            "src/main/gen", "org.fugalang.core.peg", "Fuga");

    public static void main(String[] args) throws Exception {
        GrammarSpec spec = RuleSetBuilder.generate(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.classicTokenMap
        );
        GeneratorUtil.printStats(spec);
        JGenerator.generateFiles(spec, PACKAGE_OUTPUT);
    }
}
