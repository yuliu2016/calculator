package com.example.calculator;

import com.example.calculator.grammar.SyntaxError;
import com.example.calculator.pprint.ConsoleColor;
import com.example.calculator.pprint.TokenPPrint;
import com.example.calculator.token.Tokenizer;

import java.util.Scanner;

public class Main {
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
                var result = new Tokenizer(s.replace("\\n", "\n")).tokenizeAll();
                System.out.print(TokenPPrint.format(result));
            } catch (SyntaxError e) {
                System.out.print(ConsoleColor.RED);
                System.out.println("Syntax Error: " + e.getMessage());
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
