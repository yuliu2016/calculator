package org.fugalang.grammar.main;

import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.grammar.common.GrammarSpec;
import org.fugalang.grammar.common.RuleSetBuilder;
import org.fugalang.grammar.transform.CTransform;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CFugaGenerator {

    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String GRAMMAR_PATH = "src/main/files/CGrammar";
    private static final String BASE_DIR = Paths.get(
            System.getProperty("user.home"), "vscode/cpeg").toString();
    private static final Path C_PATH = Paths.get(BASE_DIR, "parser.c");
    private static final Path TM_PATH = Paths.get(BASE_DIR, "include/internal/tokenmap.h");

    private static final String TKL_TYPE = """
            
            struct token_literal {
                const char *literal;
                size_t tkl_type;
            };
            
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

    private static String formatLiterals() {
        StringBuilder sb = new StringBuilder();

        sb.append("static struct token_literal keywords[] = {\n");
        var keywords = Keyword.ALL_KEYWORDS;
        for (String keyword : keywords) {
            sb.append("    {\"");
            sb.append(keyword);
            sb.append("\",");
            sb.append(" ".repeat(9 - keyword.length()));
            sb.append("T_");
            sb.append(keyword.toUpperCase());
            sb.append("},\n");
        }
        sb.append("};\n\n");

        sb.append("static struct token_literal operators[] = {\n");

        var operators = Operator.values();
        for (Operator op : operators) {
            sb.append("    {\"");
            sb.append(op.getCode());
            sb.append("\",");
            sb.append(" ".repeat(4 - op.getCode().length()));
            sb.append("T_");
            sb.append(op.name());
            sb.append("},\n");
        }

        sb.append("};");
        sb.append("\n\n");
        return sb.toString();
    }

    private static String formatIndices() {
        StringBuilder sb = new StringBuilder();
        var tmap = GeneratorUtil.tokenMap;

        sb.append("\n\nstatic char *indices[] = {\n    \"\",\n");
        int i = 0;
        for (var tk : tmap.values()) {
            if (!tk.isLiteral()) {
                sb.append("    \"").append(tk.literalValue()).append("\",\n");
            } else {
                if (i % 5 == 0) {
                    sb.append("    ");
                }
                sb.append("\"").append(tk.literalValue()).append("\", ");
                if (i % 5 == 4) {
                    sb.append("\n");
                }
                i++;
            }
        }

        sb.append("\"\"\n};");
        sb.append("\n\n");
        return sb.toString();
    }

    private static String getTokenMap() {
        StringBuilder sb = new StringBuilder();
        for (var tk : GeneratorUtil.tokenMap.values()) {
            sb.append("#define T_")
                    .append(tk.snakeCase().toUpperCase())
                    .append(" ")
                    .append(tk.index())
                    .append("  // ")
                    .append(tk.literalValue())
                    .append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        GrammarSpec spec = RuleSetBuilder.generate(
                GeneratorUtil.readPreprocessed(USER_DIR, GRAMMAR_PATH),
                GeneratorUtil.tokenMap
        );
        GeneratorUtil.printStats(spec);

        String parser = CTransform.generateParser(spec);
        Files.writeString(C_PATH, parser.replace("\n", System.lineSeparator()));

        String tokenHeader = getTokenMap() + formatIndices() +
                TKL_TYPE + formatLiterals();

        String tokenMap = formatHeaderFile("TOKENMAP", tokenHeader);
        Files.writeString(TM_PATH, tokenMap);
    }
}
