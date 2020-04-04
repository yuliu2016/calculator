package com.example.calculator;

import com.example.calculator.pprint.TokenPPrint;
import com.example.calculator.token.Tokenizer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        String s;
        System.out.print(">>> ");
        while (!(s = scanner.nextLine()).equals("exit")) {
            var result = new Tokenizer(s).tokenizeAll();
            System.out.print(TokenPPrint.format(result));
            System.out.print(">>> ");
        }
    }
}
