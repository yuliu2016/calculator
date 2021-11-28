package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.JPackageOutput;
import org.fugalang.grammar.transform.JGenerator;

public class CalculatorGenerator {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/CalculatorGrammar";
    private static final JPackageOutput PACKAGE_OUTPUT = new JPackageOutput(USER_DIR,
            "src/main/gen", "org.fugalang.core.calculator2.peg", "Calculator");

    public static void main(String[] args) throws Exception {
        RuleSet ruleSet = RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.classicTokenMap
        );
        JGenerator.generateFiles(ruleSet, PACKAGE_OUTPUT);
    }
}
