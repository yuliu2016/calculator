package org.fugalang.core.eval;

import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.parser.profiler.MemoHotSpotCounter;
import org.fugalang.core.peg.parser.FugaParser;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.token.SimpleLexer;

import java.util.Scanner;

public class MemoHotSpotRepl {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        String s;
        System.out.print(">>> ");
        while (!(s = scanner.nextLine()).equals("exit")) {
            if (s.isBlank()) {
                System.out.print(">>> ");
                continue;
            }
            try {
                var visitor = LexingVisitor.of(s);
                var lexer = SimpleLexer.of(visitor);
                var context = LazyParserContext.of(lexer, visitor);
                var counter = new MemoHotSpotCounter();
                SimpleParseTree.parse(context, counter, FugaParser::single_input);
                counter.printResults();
                System.out.println();
            } catch (SyntaxError e) {
                System.out.print(ConsoleColor.RED);
                System.out.println(e.getMessage());
                System.out.print(ConsoleColor.END);
            } catch (Exception e) {
                System.out.print(ConsoleColor.RED);
                e.printStackTrace(System.out);
                System.out.print(ConsoleColor.END);
            }
            System.out.print(">>> ");
        }
    }
}
