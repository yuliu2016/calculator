package org.fugalang.core.parser;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.Token;
import org.fugalang.core.token.TokenType;
import org.fugalang.core.token.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static Object parse(List<Token> tokens) {
        // top down - has to be an arithmetic expr
        return parseArithmeticExpr(new TokenVisitor(tokens));
    }

    public static ArithmeticExpr parseArithmeticExpr(TokenVisitor visitor) {
        var first = parseTerm(visitor);
        if (first == null) {
            return null;
        }
        List<Operator> operators = new ArrayList<>();
        List<Term> terms = new ArrayList<>();
        while (true) {
            visitor.markLookahead();
            var op = parseOperator(visitor, Operator.PLUS, Operator.MINUS);
            var term = parseTerm(visitor);
            if (op == null) {
                // no more items that matches this rule
                visitor.abortLookahead();
                break;
            }
            if (term != null) {
                visitor.commitLookahead();
                operators.add(op);
                terms.add(term);
            } else {
                visitor.abortLookahead();
                throw new SyntaxError("Unexpected ending with operator " + op);
            }
        }
        return new ArithmeticExpr(first, operators, terms);
    }

    public static Term parseTerm(TokenVisitor visitor) {
        var first = parseAtom(visitor);
        if (first == null) {
            return null;
        }
        List<Operator> operators = new ArrayList<>();
        List<Atom> atoms = new ArrayList<>();
        while (true) {
            visitor.markLookahead();
            var op = parseOperator(visitor, Operator.TIMES, Operator.DIV);
            var term = parseAtom(visitor);
            if (op == null) {
                // no more items that matches this rule
                visitor.abortLookahead();
                break;
            }
            if (term != null) {
                operators.add(op);
                atoms.add(term);
                visitor.commitLookahead();
            } else {
                visitor.abortLookahead();
                throw new SyntaxError("Unexpected ending with operator " + op);
            }
        }
        return new Term(first, operators, atoms);
    }

    public static Operator parseOperator(TokenVisitor visitor, Operator... operators) {
        var token = visitor.getAndAdd();
        if (token == null) {
            return null;
        }
        for (var operator : operators) {
            if (token.type == TokenType.OPERATOR && token.value == operator) {
                return operator;
            }
        }
        return null;
    }

    public static Atom parseAtom(TokenVisitor visitor) {
        var token = visitor.getAndAdd();
        if (token == null) {
            return null;
        }

        if (token.type == TokenType.INTEGER) {
            return new Atom(token.value);
        }

        if (token.value == Operator.LPAR) {
            visitor.markLookahead();

            var arithmeticExpr = parseArithmeticExpr(visitor);
            var r_par = parseOperator(visitor, Operator.RPAR);

            if (arithmeticExpr == null || r_par == null) {
                visitor.abortLookahead();
                throw new SyntaxError("Unfinished Bracket");
            }

            visitor.commitLookahead();
            return new Atom(arithmeticExpr);
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(parse(new Tokenizer("(2 + 3) * 5").tokenizeAll()));
        System.out.println(parse(new Tokenizer("2 + 3 * 5").tokenizeAll()));
    }
}