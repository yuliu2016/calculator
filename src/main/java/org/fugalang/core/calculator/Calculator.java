package org.fugalang.core.calculator;

import org.fugalang.core.calculator.peg.*;
import org.fugalang.core.calculator.peg.parser.CalculatorParser;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.parser.impl.LazyParserContext;
import org.fugalang.core.parser.impl.LexingVisitor;
import org.fugalang.core.parser.impl.SimpleParseTree;
import org.fugalang.core.token.SimpleLexer;

import java.util.Scanner;

public class Calculator {

    private static double evalSum(Sum sum) {
        if (sum.hasSumPlusTerm()) {
            var expr = sum.sumPlusTerm();
            return evalSum(expr.sum()) + evalTerm(expr.term());
        } else if (sum.hasSumMinusTerm()) {
            var expr = sum.sumMinusTerm();
            return evalSum(expr.sum()) - evalTerm(expr.term());
        } else {
            return evalTerm(sum.term());
        }
    }

    private static double evalTerm(Term term) {
        if (term.hasTermTimesFactor()) {
            var expr = term.termTimesFactor();
            return evalTerm(expr.term()) * evalFactor(expr.factor());
        } else if (term.hasTermDivFactor()) {
            var expr = term.termDivFactor();
            return evalTerm(expr.term()) / evalFactor(expr.factor());
        } else if (term.hasTermModulusFactor()) {
            var expr = term.termModulusFactor();
            return evalTerm(expr.term()) % evalFactor(expr.factor());
        } else {
            return evalFactor(term.factor());
        }
    }

    private static double evalFactor(Factor factor) {

        if (factor.hasPlusFactor()) {
            return +evalFactor(factor.plusFactor().factor());
        } else if (factor.hasMinusFactor()) {
            return -evalFactor(factor.minusFactor().factor());
        } else if (factor.hasBitNotFactor()) {
            return ~((int) evalFactor(factor.bitNotFactor().factor()));
        } else {
            return evalPower(factor.power());
        }
    }

    private static double evalPower(Power power) {
        if (power.hasAtomPowerFactor()) {
            var exp = power.atomPowerFactor();
            return Math.pow(evalAtom(exp.atom()), evalFactor(exp.factor()));
        } else {
            return evalAtom(power.atom());
        }
    }

    private static double evalAtom(Atom atom) {

        if (atom.hasLparSumRpar()) {
            return evalSum(atom.lparSumRpar().sum());
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
                var tree = SimpleParseTree.parse(context, CalculatorParser::sum, Sum::new);

                var result = evalSum(tree);

                System.out.println(result);
            } catch (SyntaxError e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            System.out.print(">>> ");
        }
    }
}
