package org.fugalang.core;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.pprint.TokenPPrint;
import org.fugalang.core.token.LexerTests;

import java.util.Scanner;

public class LexerRepl {
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
                var result = LexerTests.tokenize(s);
                System.out.print(TokenPPrint.format(result));
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
