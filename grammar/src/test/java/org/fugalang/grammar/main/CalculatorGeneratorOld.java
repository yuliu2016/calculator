package org.fugalang.grammar.main;

import org.fugalang.grammar.gen.PEGBuilder;
import org.fugalang.grammar.transform.JPackageOutput;

@Deprecated
public class CalculatorGeneratorOld {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/CalculatorGrammar";
    private static final JPackageOutput PACKAGE_OUTPUT = new JPackageOutput(USER_DIR,
            "src/main/gen", "org.fugalang.core.calculator.peg", "Calculator");

    public static void main(String[] args) throws Exception {
        new PEGBuilder(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.simpleConverter(),
                PACKAGE_OUTPUT
        ).generate(true);
    }
}
