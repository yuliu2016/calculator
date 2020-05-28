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
        } else if (atom.hasAtom2()) {
            var call = atom.atom2();
            var name = call.name().toLowerCase();
            double[] resolved;
            if (call.hasParameters()) {
                var param = call.parameters();
                resolved = new double[param.commaSums().size() + 1];
                resolved[0] = evalSum(param.sum());
                int i = 1;
                for (var commaSum : param.commaSums()) {
                    resolved[i] = evalSum(commaSum.sum());
                    i++;
                }
            } else {
                resolved = new double[0];
            }
            return evalDispatch(name, resolved);
        } else if (atom.hasName()) {
            var name = atom.name();
            return evalName(name);
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

        throw new IllegalStateException();
    }

    private static double evalName(String name) {
        switch (name) {
            case "pi":
                return Math.PI;
            case "e":
                return Math.E;
            case "wau":
                return 1;
            case "phi":
                return (1 + Math.sqrt(5)) / 2;
            default:
                throw new SyntaxError("Constant " + name +
                        " not found. Did you mean " + name + "()?");
        }
    }

    private static double evalDispatch(String name, double[] params) {
        switch (name) {
            case "sum":
                return fnSum(params);
            case "max":
                return fnMax(params);
            case "min":
                return fnMin(params);
            case "hypot":
                return fnHypot(params);
            case "average":
            case "mean":
                return fnAvg(params);
            case "var":
                return fnVar(params);
            case "stddev":
                return Math.sqrt(fnVar(params));
            case "fib":
                return fnFib((int) firstParam(name, params));
            case "sin":
                return Math.sin(firstParam(name, params));
            case "cos":
                return Math.cos(firstParam(name, params));
            case "tan":
                return Math.tan(firstParam(name, params));
            case "sinh":
                return Math.sinh(firstParam(name, params));
            case "cosh":
                return Math.cosh(firstParam(name, params));
            case "tanh":
                return Math.tanh(firstParam(name, params));
            case "asin":
                return Math.asin(firstParam(name, params));
            case "acos":
                return Math.acos(firstParam(name, params));
            case "atan":
                return Math.atan(firstParam(name, params));
            case "abs":
                return Math.abs(firstParam(name, params));
            case "log":
                return Math.log(firstParam(name, params));
            case "sqrt":
                return Math.sqrt(firstParam(name, params));
            case "degrees":
                return Math.toDegrees(firstParam(name, params));
            case "radians":
                return Math.toRadians(firstParam(name, params));
            case "random":
                noParam(name, params);
                return Math.random();
            default:
                throw new SyntaxError("Illegal function: " + name);
        }
    }

    private static void noParam(String name, double[] params) {
        if (params.length != 0) {
            throw new SyntaxError("Function " + name +
                    " does not expect any parameters");
        }
    }

    private static double firstParam(String name, double[] params) {
        if (params.length != 1) throw new SyntaxError("Function " + name +
                " requires one and only one parameter");
        return params[0];
    }

    private static double fnSum(double[] params) {
        double sum = 0;
        for (double param : params) {
            sum += param;
        }
        return sum;
    }

    private static double fnMax(double[] params) {
        double max = Double.MIN_VALUE;
        for (double param : params) {
            if (param > max) max = param;
        }
        return max;
    }

    private static double fnMin(double[] params) {
        double min = Double.MAX_VALUE;
        for (double param : params) {
            if (param < min) min = param;
        }
        return min;
    }

    private static double fnHypot(double[] params) {
        double sum = 0;
        for (double param : params) {
            sum += param * param;
        }
        return Math.sqrt(sum);
    }

    private static double fnAvg(double[] params) {
        if (params.length == 0) return 0;
        return fnSum(params) / params.length;
    }

    private static double fnVar(double[] params) {
        if (params.length == 0) return 0;
        double mean = fnSum(params) / params.length;
        double var = 0;
        for (double param : params) {
            double e = param - mean;
            var += e * e;
        }
        return var / params.length;
    }

    private static double fnFib(int n) {
        if (n < 1) return Double.NaN;
        int a = 1;
        int b = 1;
        for (int i = 0; i < n - 2; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
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
