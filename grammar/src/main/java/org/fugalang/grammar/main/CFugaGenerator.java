package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.ctrans.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("SpellCheckingInspection")
public class CFugaGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "ClionProjects/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser.c");
    private static final Path H_PATH = Paths.get(BASE_DIR, "include/parser.h");
    private static final Path AST_PATH = Paths.get(BASE_DIR, "include/astgen.h");
    private static final Path TM_PATH = Paths.get(BASE_DIR, "include/tokenmap.h");

    public static void main(String[] args) throws Exception {
        RuleSet ruleSet = RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.tokenMap
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

        String ast = "#ifndef CPEG_ASTGEN_H\n" +
                "#define CPEG_ASTGEN_H\n" +
                "\n" +
                "#include \"peg.h\"\n" +
                "\n"
                + CTransform.getASTGen(ruleSet, "FAstGen", "ast_gen_t", "ASC")
                + "\n" +
                "#endif //CPEG_ASTGEN_H\n";
        Files.writeString(AST_PATH, ast.replace("\n", System.lineSeparator()));

        String tokenMap = "#ifndef CPEG_TOKENMAP_H\n" +
                "#define CPEG_TOKENMAP_H\n" +
                "\n" +
                CTransform.getTokenMap(ruleSet)
                +
                "#endif //CPEG_TOKENMAP_H";
        Files.writeString(TM_PATH, tokenMap.replace("\n", System.lineSeparator()));
    }
}
