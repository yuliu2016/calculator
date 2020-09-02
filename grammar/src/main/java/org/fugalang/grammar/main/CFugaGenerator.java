package org.fugalang.grammar.main;

import org.fugalang.grammar.common.RuleSet;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CFugaGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/Grammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "ClionProjects/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser.c");
    private static final Path H_PATH = Paths.get(BASE_DIR, "include/parser.h");
    private static final Path AST_PATH = Paths.get(BASE_DIR, "include/astgen.h");
    private static final Path TM_PATH = Paths.get(BASE_DIR, "include/tokenmap.h");
    private static final Path DC_PATH = Paths.get(BASE_DIR, "exclude/dummyc.c");

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
                "PARSER",
                CTransform.getFuncDeclarations(ruleSet),
                "peg.h");
        Files.writeString(H_PATH, h);

        String c = "#include \"include/parser.h\"\n" +
                "#include \"include/internal/peg_macros.h\"\n" +
                CTransform.getFunctionBodies(ruleSet);
        Files.writeString(C_PATH, c.replace("\n", System.lineSeparator()));

        String ast = formatHeaderFile("ASTGEN",
                CTransform.getASTGen(ruleSet),
                "peg.h");
        Files.writeString(AST_PATH, ast);

        String tokenMap = formatHeaderFile("TOKENMAP", CTransform.getTokenMap(ruleSet));
        Files.writeString(TM_PATH, tokenMap);

        String dummy = "#include \"astgen.h\"\n" +
                "#include \"tokenmap.h\"\n\n" +
                CTransform.getDummyCompiler(ruleSet);

        Files.writeString(DC_PATH, dummy.replace("\n", System.lineSeparator()));
    }
}
