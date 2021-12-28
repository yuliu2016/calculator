package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CCalcGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/CCalcGrammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "vscode/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser2.c");

    private static final String ENTRY_POINT = """
            
            
            
            // Parser Entry Point
            double *parse_calc(parser_t *p) {
                return csum(p);
            }
            """;

    public static void main(String[] args) throws Exception {
        RuleSet ruleSet = RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.tokenMap
        );

        String c = "#include \"include/internal/parser.h\"\n\n\n" +
                CTransform.getFuncDeclarations(ruleSet) +
                ENTRY_POINT +
                CTransform.getFunctionBodies(ruleSet);
        Files.writeString(C_PATH, c.replace("\n", System.lineSeparator()));
    }
}
