package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CCalcGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/CalculatorGrammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "ClionProjects/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "exclude/calc2_parser.c");
    private static final Path H_PATH = Paths.get(BASE_DIR, "exclude/calc2_parser.h");
    private static final Path AST_PATH = Paths.get(BASE_DIR, "exclude/calc2_astgen.h");
    private static final Path TM_PATH = Paths.get(BASE_DIR, "exclude/calc2_tokenmap.h");

    private static String formatHeaderFile(String name, String content, String... includes) {
        var s = "#ifndef CPEG_" + name + "_H\n" +
                "#define CPEG_" + name + "_H\n" +
                "\n" + Arrays
                .stream(includes)
                .map(x -> "#include \"" + x + "\"\n")
                .collect(Collectors.joining()) +
                "\n" +
                content +
                "\n" +
                "#endif // CPEG_" + name + "_H\n";
        return s.replace("\n", System.lineSeparator());
    }

    public static void main(String[] args) throws Exception {
        RuleSet ruleSet = RuleSetBuilder.generateRuleSet(
                GeneratorUtil.readGrammar(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.tokenMap
        );

        String h = formatHeaderFile(
                "CALC2_PARSER",
                CTransform.getFuncDeclarations(ruleSet),
                "peg.h");
        Files.writeString(H_PATH, h);

        String c = "#include \"calc2_parser.h\"\n" +
                "#include \"peg_macros.h\"\n" +
                CTransform.getFunctionBodies(ruleSet);
        Files.writeString(C_PATH, c.replace("\n", System.lineSeparator()));

        String ast = formatHeaderFile("CALC2_ASTGEN",
                CTransform.getASTGen(ruleSet, "FAstGen", "ast_gen_t", "ASC"),
                "peg.h");
        Files.writeString(AST_PATH, ast);

        String tokenMap = formatHeaderFile("CALC2_TOKENMAP", CTransform.getTokenMap(ruleSet));
        Files.writeString(TM_PATH, tokenMap);
    }
}
