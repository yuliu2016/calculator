package org.fugalang.core;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.SimpleParseTree;
import org.fugalang.core.parser.context.LazyParserContext;
import org.fugalang.core.parser.context.LexingVisitor;
import org.fugalang.core.pgen.SingleInput;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.token.SimpleLexer;

import java.util.Scanner;

public class ParserRepl {
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
                var context = LazyParserContext.of(lexer, visitor, false);
                var tree = SimpleParseTree.parse(context, SingleInput::parse, SingleInput::of);
                System.out.println(tree.prettyFormat(2));
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
