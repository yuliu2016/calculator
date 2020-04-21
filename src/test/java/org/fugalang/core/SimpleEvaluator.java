package org.fugalang.core;

/* A Java program to evaluate a given expression where tokens are separated
   by space. 
   Test Cases: 
     "10 + 2 * 6"            ---> 22 
     "100 * 2 + 12"          ---> 212 
     "100 * ( 2 + 12 )"      ---> 1400 
     "100 * ( 2 + 12 ) / 14" ---> 100     
*/

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.Token;
import org.fugalang.core.token.TokenType;
import org.fugalang.core.token.Tokenizer;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

// https://www.geeksforgeeks.org/expression-evaluation/
public class SimpleEvaluator {
    public static int evaluate(List<Token> tokens) {

        // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<>();

        // Stack for Operators: 'ops' 
        Stack<Operator> ops = new Stack<>();

        for (Token token : tokens) {
            // Current token is a whitespace, skip it
            if (TokenType.DELIMITERS.contains(token.type)) {
                continue;
            }

            if (token.type == TokenType.INTEGER) {
                values.push((Integer) token.value);
            }

            // Current token is an opening brace, push it to 'ops'
            else if (token.value == Operator.LPAR)
                ops.push((Operator) token.value);

                // Closing brace encountered, solve entire brace
            else if (token.value == Operator.RPAR) {
                while (ops.peek() != Operator.LPAR) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }

            // Current token is an operator.
            else if (token.value == Operator.PLUS ||
                    token.value == Operator.MINUS ||
                    token.value == Operator.TIMES ||
                    token.value == Operator.DIV) {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence((Operator) token.value, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }

                // Push current token to 'ops'.
                ops.push((Operator) token.value);
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
    public static boolean hasPrecedence(Operator op1, Operator op2) {
        if (op2 == Operator.LPAR || op2 == Operator.RPAR)
            return false;
        //noinspection RedundantIfStatement
        if ((op1 == Operator.TIMES || op1 == Operator.DIV) && (op2 == Operator.PLUS || op2 == Operator.MINUS))
            return false;
        else
            return true;
    }

    // A utility method to apply an operator 'op' on operands 'a'  
    // and 'b'. Return the result. 
    public static int applyOp(Operator op, int b, int a) {
        switch (op) {
            case PLUS:
                return a + b;
            case MINUS:
                return a - b;
            case TIMES:
                return a * b;
            case DIV:
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