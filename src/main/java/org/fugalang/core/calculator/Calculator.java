package org.fugalang.core.calculator;

import org.fugalang.core.calculator.pgen.*;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.token.SimpleLexer;

import java.util.Scanner;

public class Calculator {

    private static double evaluate0(Sum sumExpr) {
        var sum = evaluate0(sumExpr.term());

        for (var opTerm : sumExpr.sum2List()) {
            var term = opTerm.term();
            var value = evaluate0(term);

            if (opTerm.plusOrMinus().isPlus()) {
                sum += value;
            } else if (opTerm.plusOrMinus().isMinus()) {
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

            if (opFactor.term21().isTimes()) {
                product *= value;
            } else if (opFactor.term21().isDiv()) {
                product /= value;
            }
            if (opFactor.term21().isModulus()) {
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
            if (factor1.factor11().isBitNot()) {
                return ~((int) evaluate0(factor1.factor()));
            }
            if (factor1.factor11().isMinus()) {
                return -evaluate0(factor1.factor());
            }
            if (factor1.factor11().isPlus()) {
                return +evaluate0(factor1.factor());
            }
        }
        throw new IllegalStateException("Illegal value");
    }

    private static double evaluate0(Power power) {

        var pow = evaluate0(power.atom());

        if (power.hasFactor()) {
            pow = Math.pow(pow, evaluate0(power.factor().factor()));
        }

        return pow;
    }

    private static double evaluate0(Atom atom) {

        if (atom.hasSum()) {
            return evaluate0(atom.sum().sum());
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
        var scanner = new Scanner(System.in);
        String input;
        System.out.print(">>> ");
        while (!(input = scanner.nextLine()).equals("exit")) {
            if (input.isBlank()) {
                System.out.print(">>> ");
                continue;
            }
            try {
                var visitor = LexingVisitor.of(input);
                var lexer = SimpleLexer.of(visitor);
                var context = LazyParserContext.of(lexer, visitor, false);
                var tree = SimpleParseTree.parse(context, Sum::parse, Sum::of);

                var result = evaluate0(tree);

                System.out.println(result);
            } catch (SyntaxError e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace(System.out);
//                System.out.println("Error: Not Implemented");
            }
            System.out.print(">>> ");
        }
    }
}
