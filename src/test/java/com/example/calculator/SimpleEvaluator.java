package com.example.calculator;

/* A Java program to evaluate a given expression where tokens are separated
   by space. 
   Test Cases: 
     "10 + 2 * 6"            ---> 22 
     "100 * 2 + 12"          ---> 212 
     "100 * ( 2 + 12 )"      ---> 1400 
     "100 * ( 2 + 12 ) / 14" ---> 100     
*/

import com.example.calculator.grammar.SyntaxError;
import com.example.calculator.pprint.ConsoleColor;
import com.example.calculator.token.Operator;
import com.example.calculator.token.Token;
import com.example.calculator.token.TokenType;
import com.example.calculator.token.Tokenizer;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import static com.example.calculator.token.Operator.*;

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
            else if (token.value == OPEN_ROUND)
                ops.push((Operator) token.value);

                // Closing brace encountered, solve entire brace
            else if (token.value == CLOSE_ROUND) {
                while (ops.peek() != OPEN_ROUND) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
            }

            // Current token is an operator.
            else if (token.value == PLUS ||
                    token.value == MINUS ||
                    token.value == TIMES ||
                    token.value == DIV) {
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
        if (op2 == OPEN_ROUND || op2 == CLOSE_ROUND)
            return false;
        //noinspection RedundantIfStatement
        if ((op1 == TIMES || op1 == DIV) && (op2 == PLUS || op2 == MINUS))
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