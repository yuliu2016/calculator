package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.ctrans.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CFugaGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "ClionProjects/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser.c");
    private static final Path H_PATH = Paths.get(BASE_DIR, "include/parser.h");

    public static void main(String[] args) throws Exception {
        RuleSet ruleSet = RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.createTokenMap()
        );

        String h = "#ifndef CPEG_PARSER_H\n" +
                "#define CPEG_PARSER_H\n" +
                "\n" +
                "#include \"peg.h\"\n" +
                "\n"
                + CTransform.getFuncDeclarations(ruleSet)
                + "\n" +
                "#endif //CPEG_PARSER_H\n";
        Files.writeString(H_PATH, h.replace("\n", System.lineSeparator()));

        String c = "#include \"include/parser.h\"\n" +
                "#include \"include/internal/peg_macros.h\"\n" +
                CTransform.getFunctionBodies(ruleSet);
        Files.writeString(C_PATH, c.replace("\n", System.lineSeparator()));
    }
}
