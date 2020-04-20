package com.example.calculator.eval;

import com.example.calculator.grammar.SyntaxError;
import com.example.calculator.parser.ArithmeticExpr;
import com.example.calculator.parser.Atom;
import com.example.calculator.parser.Parser;
import com.example.calculator.parser.Term;
import com.example.calculator.pprint.ConsoleColor;
import com.example.calculator.token.Operator;
import com.example.calculator.token.Token;
import com.example.calculator.token.Tokenizer;

import java.util.List;
import java.util.Scanner;

public class RecursiveEvaluator {

    private static int evaluate(List<Token> tokens) {
        var expr = ((ArithmeticExpr) Parser.parse(tokens));
        return evaluate0(expr);
    }

    private static int evaluate0(ArithmeticExpr expr) {
        var sum = evaluate0(expr.getFirstTerm());

        for (int i = 0; i < expr.getOps().size(); i++) {
            var op = expr.getOps().get(i);
            var term = expr.getTerms().get(i);
            var value = evaluate0(term);

            if (op == Operator.PLUS) {
                sum += value;
            } else if (op == Operator.MINUS) {
                sum -= value;
            } else {
                throw new IllegalStateException();
            }
        }

        return sum;
    }

    private static int evaluate0(Term term) {
        var product = evaluate0(term.getFirstAtom());

        for (int i = 0; i < term.getOps().size(); i++) {
            var op = term.getOps().get(i);
            var atom = term.getAtoms().get(i);
            var value = evaluate0(atom);

            if (op == Operator.TIMES) {
                product *= value;
            } else if (op == Operator.DIV) {
                product /= value;
            } else {
                throw new IllegalStateException();
            }
        }

        return product;
    }

    private static int evaluate0(Atom atom) {
        var val = atom.getVal();
        if (val instanceof Integer) {
            return (int) val;
        } else if (val instanceof ArithmeticExpr) {
            return evaluate0((ArithmeticExpr) val);
        }
        throw new IllegalStateException();
    }

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
                var tokens = new Tokenizer(s).tokenizeAll();
                var result = "" + evaluate(tokens);
                System.out.println(ConsoleColor.wrap("\033[34;1m", result));
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
