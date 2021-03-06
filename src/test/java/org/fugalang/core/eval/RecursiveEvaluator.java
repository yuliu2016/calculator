package org.fugalang.core.eval;

import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.parser.simple.ArithmeticExpr;
import org.fugalang.core.parser.simple.Atom;
import org.fugalang.core.parser.simple.SimpleParser;
import org.fugalang.core.parser.simple.Term;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.token.LexerTests;

import java.util.List;
import java.util.Scanner;

@Deprecated
public class RecursiveEvaluator {

    private static double evaluate(List<ParserElement> tokens) {
        var expr = ((ArithmeticExpr) SimpleParser.parse(tokens));
        return evaluate0(expr);
    }

    private static double evaluate0(ArithmeticExpr expr) {
        var sum = evaluate0(expr.getFirstTerm());

        for (int i = 0; i < expr.getOps().size(); i++) {
            var op = expr.getOps().get(i);
            var term = expr.getTerms().get(i);
            var value = evaluate0(term);

            if (op.equals("+")) {
                sum += value;
            } else if (op.equals("-")) {
                sum -= value;
            } else {
                throw new IllegalStateException("Illegal op: " + op);
            }
        }

        return sum;
    }

    private static double evaluate0(Term term) {
        var product = evaluate0(term.getFirstAtom());

        for (int i = 0; i < term.getOps().size(); i++) {
            var op = term.getOps().get(i);
            var atom = term.getAtoms().get(i);
            var value = evaluate0(atom);

            if (op.equals("*")) {
                product *= value;
            } else if (op.equals("/")) {
                product /= value;
            } else {
                throw new IllegalStateException("Illegal Op: " + op);
            }
        }

        return product;
    }

    private static double evaluate0(Atom atom) {
        var val = atom.getVal();
        if (val instanceof String) {
            var valStr = ((String) val)
                    .replace("_", "")
                    .replace("j", "");

            if (valStr.startsWith("0x")) {
                return Integer.parseInt(valStr.substring(2), 16);
            }
            if (valStr.startsWith("0b")) {
                return Integer.parseInt(valStr.substring(2), 2);
            }
            if (valStr.startsWith("0o")) {
                return Integer.parseInt(valStr.substring(2), 8);
            }
            return Double.parseDouble(valStr);
        } else if (val instanceof ArithmeticExpr) {
            return evaluate0((ArithmeticExpr) val);
        }
        throw new IllegalStateException("Illegal value: " + val);
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
                var tokens = LexerTests.tokenize(s);
                var result = "" + evaluate(tokens);
                System.out.println(ConsoleColor.wrap("\033[34;1m", result));
            } catch (SyntaxError e) {
                System.out.print(ConsoleColor.RED);
                System.out.println("Syntax Error: " + e.getMessage());
                System.out.print(ConsoleColor.END);
            } catch (Exception e) {
                System.out.print(ConsoleColor.RED);
                System.out.println("Syntax Error: Not Implemented");
                System.out.print(ConsoleColor.END);
            }
            System.out.print(">>> ");
        }
    }
}
