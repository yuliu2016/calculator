package org.fugalang.grammar.main;

import org.fugalang.grammar.cgen.RuleSetBuilder;

public class CFugaGenerator {

    public static void main(String[] args) throws Exception{
        RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar("/", ""),
                GeneratorUtil.simpleConverter()
        );
    }
}
