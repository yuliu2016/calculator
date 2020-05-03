package org.fugalang.core.parser.simple;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.token.Operator;
import org.fugalang.core.token.Token;
import org.fugalang.core.token.TokenType;
import org.fugalang.core.token.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class SimpleParser {
    public static Object parse(List<Token> tokens) {
        // top down - has to be an arithmetic expr
        return parseArithmeticExpr(new TokenVisitor(tokens));
    }

    public static ArithmeticExpr parseArithmeticExpr(TokenVisitor visitor) {
        var first = parseTerm(visitor);
        if (first == null) {
            return null;
        }
        List<String> operators = new ArrayList<>();
        List<Term> terms = new ArrayList<>();
        while (true) {
            visitor.markLookahead();
            var op = parseOperator(visitor, Operator.PLUS.getCode(), Operator.MINUS.getCode());
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
        List<String> operators = new ArrayList<>();
        List<Atom> atoms = new ArrayList<>();
        while (true) {
            visitor.markLookahead();
            var op = parseOperator(visitor, Operator.TIMES.getCode(), Operator.DIV.getCode());
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

    public static String parseOperator(TokenVisitor visitor, String... operators) {
        var token = visitor.getAndAdd();
        if (token == null) {
            return null;
        }
        for (var operator : operators) {
            if (token.getType() == TokenType.OPERATOR && token.getValue().equals(operator)) {
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

        if (token.getType() == TokenType.NUMBER) {
            return new Atom(token.getValue());
        }

        if (token.getValue().equals(Operator.LPAR.getCode())) {
            visitor.markLookahead();

            var arithmeticExpr = parseArithmeticExpr(visitor);
            var r_par = parseOperator(visitor, Operator.RPAR.getCode());

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
