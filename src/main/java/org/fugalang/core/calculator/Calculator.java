package org.fugalang.core.calculator;

import org.fugalang.core.calculator.pgen.*;
import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.ParseTreeImpl;
import org.fugalang.core.token.Tokenizer;

import java.util.Scanner;

public class Calculator {

    private static double evaluate0(Sum sumExpr) {
        var sum = evaluate0(sumExpr.term());

        for (var opTerm : sumExpr.sum2List()) {
            var term = opTerm.term();
            var value = evaluate0(term);

            if (opTerm.sum21().isTokenPlus()) {
                sum += value;
            } else if (opTerm.sum21().isTokenMinus()) {
                sum -= value;
            }
        }

        return sum;
    }

    private static double evaluate0(Term term) {
        var product = evaluate0(term.factor());

        for (var opFactor : term.term2List()) {
            var factor = opFactor.factor();
            var value = evaluate0(factor);

            if (opFactor.term21().isTokenTimes()) {
                product *= value;
            } else if (opFactor.term21().isTokenDiv()) {
                product /= value;
            }
            if (opFactor.term21().isTokenModulus()) {
                product %= value;
            }
        }

        return product;
    }

    private static double evaluate0(Factor factor) {

        if (factor.hasPower()) {
            return evaluate0(factor.power());
        } else if (factor.hasFactor1()) {
            var factor1 = factor.factor1();
            if (factor1.factor11().isTokenBitNot()) {
                return ~((int) evaluate0(factor1.factor()));
            }
            if (factor1.factor11().isTokenMinus()) {
                return -evaluate0(factor1.factor());
            }
            if (factor1.factor11().isTokenPlus()) {
                return +evaluate0(factor1.factor());
            }
        }
        throw new IllegalStateException("Illegal value");
    }

    private static double evaluate0(Power power) {

        var pow = evaluate0(power.atom());

        if (power.hasPower2()) {
            pow = Math.pow(pow, evaluate0(power.power2().factor()));
        }

        return pow;
    }

    private static double evaluate0(Atom atom) {

        if (atom.hasAtom1()) {
            return evaluate0(atom.atom1().sum());
        } else if (atom.hasNumber()) {
            var valStr = atom.number()
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
        }

        throw new IllegalStateException("Illegal value");
    }

    public static void main(String[] args) {
        var parser = new ParseTreeImpl();
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
                var cst = parser.parse(tokens, Sum::parse, Sum::of);
                var result = evaluate0(cst);
                System.out.println(result);
            } catch (SyntaxError e) {
                System.out.println("Syntax Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Syntax Error: Not Implemented");
            }
            System.out.print(">>> ");
        }
    }
}
