package org.fugalang.core.parser.simple;

import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.parser.SyntaxError;
import org.fugalang.core.token.TokenType;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class SimpleParser {
    public static Object parse(List<ParserElement> tokens) {
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
            var op = parseOperator(visitor, "+", "-");
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
            var op = parseOperator(visitor, "*", "/");
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

        if (token.getValue().equals("(")) {
            visitor.markLookahead();

            var arithmeticExpr = parseArithmeticExpr(visitor);
            var r_par = parseOperator(visitor, ")");

            if (arithmeticExpr == null || r_par == null) {
                visitor.abortLookahead();
                throw new SyntaxError("Unfinished Bracket");
            }

            visitor.commitLookahead();
            return new Atom(arithmeticExpr);
        }

        return null;
    }
}
