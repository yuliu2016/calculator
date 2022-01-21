package org.fugalang.grammar.main;

import org.fugalang.grammar.common.GrammarSpec;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.JPackageOutput;
import org.fugalang.grammar.transform.JGenerator;

public class MetaGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/MetaGrammar";
    private static final JPackageOutput PACKAGE_OUTPUT = new JPackageOutput(USER_DIR,
            "grammar/src/main/gen", "org.fugalang.grammar.peg", "Meta");

    public static void main(String[] args) throws Exception {
        GrammarSpec spec = RuleSetBuilder.generate(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.classicTokenMap
        );
        JGenerator.generateFiles(spec, PACKAGE_OUTPUT);
    }
}
