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
            System.getProperty("user.home"), "vscode/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser.c");
    private static final Path TM_PATH = Paths.get(BASE_DIR, "include/tokenmap.h");

    private static final String ENTRY_POINT = """
            
            
            
            // Parser Entry Point
            void *parse_grammar(FParser *p, int entry_point) {
                switch (entry_point) {
                case 0:
                    return single_input(p);
                case 1:
                    return file_input(p);
                case 2:
                    return eval_input(p);
                default:
                    return 0;
                }
            }
            """;

    @SuppressWarnings("SameParameterValue")
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

        String c = "#include \"include/internal/parser.h\"\n\n\n" +
                CTransform.getFuncDeclarations(ruleSet) +
                ENTRY_POINT +
                CTransform.getFunctionBodies(ruleSet);
        Files.writeString(C_PATH, c.replace("\n", System.lineSeparator()));

        String tokenMap = formatHeaderFile("TOKENMAP", CTransform.getTokenMap(ruleSet));
        Files.writeString(TM_PATH, tokenMap);
    }
}
