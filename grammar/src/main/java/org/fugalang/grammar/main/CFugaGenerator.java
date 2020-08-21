package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.ctrans.CTransform;

public class CFugaGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";

    public static void main(String[] args) throws Exception{
        RuleSet ruleSet = RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.createTokenMap()
        );
        String s = CTransform.getParserFileContent(ruleSet);
        System.out.println("#include <peg_macros.h>\n");
        System.out.println(s);
    }
}
