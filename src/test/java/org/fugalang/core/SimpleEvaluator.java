package org.fugalang.core;

/* A Java program to evaluate a given expression where tokens are separated
   by space. 
   Test Cases: 
     "10 + 2 * 6"            ---> 22 
     "100 * 2 + 12"          ---> 212 
     "100 * ( 2 + 12 )"      ---> 1400 
     "100 * ( 2 + 12 ) / 14" ---> 100     
*/

import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.token.LexerTests;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.TokenType;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

// https://www.geeksforgeeks.org/expression-evaluation/
public class SimpleEvaluator {
    public static int evaluate(List<ParserElement> tokens) {

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<>();

        // Stack for Operators: 'ops' 
        Stack<String> ops = new Stack<>();

        for (ParserElement token : tokens) {
            // Current token is a whitespace, skip it
            if (token.getType() == TokenType.NEWLINE) {
                continue;
            }

            if (token.getType() == TokenType.NUMBER) {
                values.push(Integer.valueOf(token.getValue()));
            }

            // Current token is an opening brace, push it to 'ops'
            else if (token.valueEquals(Operator.LPAR.getCode()))
                ops.push(token.getValue());

                // Closing brace encountered, solve entire brace
            else if (token.valueEquals(Operator.RPAR.getCode())) {
                while (!ops.peek().equals(Operator.LPAR.getCode())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }

            // Current token is an operator.
            else if (token.valueEquals(Operator.PLUS.getCode()) ||
                    token.valueEquals(Operator.MINUS.getCode()) ||
                    token.valueEquals(Operator.TIMES.getCode()) ||
                    token.valueEquals(Operator.DIV.getCode())) {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(token.getValue(), ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }

                // Push current token to 'ops'.
                ops.push(token.getValue());
            }
        }

        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Returns true if 'op2' has higher or same precedence as 'op1', 
    // otherwise returns false. 
    public static boolean hasPrecedence(String op1, String op2) {
        if (op2.equals(Operator.LPAR.getCode()) || op2.equals(Operator.RPAR.getCode()))
            return false;
        //noinspection RedundantIfStatement
        if ((op1.equals(Operator.TIMES.getCode()) || op1.equals(Operator.DIV.getCode())) &&
                (op2.equals(Operator.PLUS.getCode()) || op2.equals(Operator.MINUS.getCode())))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'  
    // and 'b'. Return the result. 
    public static int applyOp(String op, int b, int a) {
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }

    // Driver method to test above methods 
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
                e.printStackTrace(System.out);
                System.out.print(ConsoleColor.END);
            }
            System.out.print(">>> ");
        }

    }
}