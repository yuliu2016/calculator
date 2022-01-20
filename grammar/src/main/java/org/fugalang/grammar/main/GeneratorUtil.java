package org.fugalang.grammar.main;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.token.Keyword;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.SimpleLexer;
import org.fugalang.core.token.TokenType;
import org.fugalang.grammar.common.TokenEntry;
import org.fugalang.grammar.peg.parser.MetaParser;
import org.fugalang.grammar.peg.wrapper.Grammar;
import org.fugalang.grammar.util.StringUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GeneratorUtil {

    public static Grammar readGrammar(String base, String grammarPath) throws IOException {
        return readGrammar(Files.readString(Paths.get(base, grammarPath)));
    }

    public static Grammar readPreprocessed(
            String base, String grammarPath) throws IOException {
        return readGrammar(preprocessGrammar(Files
                .readString(Paths.get(base, grammarPath))));
    }

    private static Grammar readGrammar(String data) {
        var visitor = LexingVisitor.of(data);
        var lexer = SimpleLexer.of(visitor);
        var context = LazyParserContext.of(lexer, visitor);
        var node = SimpleParseTree.parse(context, MetaParser::grammar);
        return new Grammar(node);
    }

    public static String preprocessGrammar(String s) {
        var fixedSep = s.replace(System.lineSeparator(), "\n");
        var lines = StringUtil.splitLines(fixedSep);
        List<String> newLines = new ArrayList<>();
        boolean inlineCode = false;
        for (var line : lines) {
            if (line.startsWith("```")) {
                inlineCode = !inlineCode;
                newLines.add("");
            } else {
                if (inlineCode) {
                    newLines.add(".code ('" + line + "')");
                } else newLines.add(line);
            }
        }
        return String.join("\n", newLines);
    }

    public static final Map<String, TokenEntry> tokenMap = tokenMap("");
    public static final Map<String, TokenEntry> classicTokenMap = tokenMap("");

    @SuppressWarnings("SameParameterValue")
    private static Map<String, TokenEntry> tokenMap(String operator_prefix) {
        Map<String, TokenEntry> tokenMap = new LinkedHashMap<>();
        List<String> nonLiteralTypes = TokenType
                .ELEMENT_TYPES
                .stream()
                .filter(x -> !x.isLiteral())
                .map(ElementType::name).toList();

        for (int i = 0; i < nonLiteralTypes.size(); i++) {
            var s = nonLiteralTypes.get(i);
            var idx = 1 + i;
            var e = new TokenEntry(idx, false, s.toLowerCase(), s);
            tokenMap.put(s, e);
        }

        List<Operator> operators = Arrays.asList(Operator.values());
        for (int i = 0; i < operators.size(); i++) {
            var op = operators.get(i);
            var opCode = op.getCode();
            var lower = op.name().toLowerCase();
            var idx = 1 + nonLiteralTypes.size() + i;
            var e = new TokenEntry(idx, true, operator_prefix + lower, opCode);
            tokenMap.put(opCode, e);
        }

        var keywords = Keyword.ALL_KEYWORDS;
        for (int i = 0; i < keywords.size(); i++) {
            var keyword = keywords.get(i);
            var lower = keyword.toLowerCase();
            var idx = 1 + nonLiteralTypes.size() + operators.size() + i;
            var e = new TokenEntry(idx, true, lower, keyword);
            tokenMap.put(keyword, e);
        }
        return tokenMap;
    }
}
