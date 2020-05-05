package org.fugalang.core.parser;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.pgen.SingleInput;
import org.fugalang.core.pprint.ConsoleColor;
import org.fugalang.core.pprint.TokenPPrint;
import org.fugalang.core.token.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

class ParseTreeImpl implements ParseTree {

    private List<ParserElement> tokens;
    private int position;
    ParseTreeFrame currentFrame;
    ParseTreeNode resultNode;

    public <T> T parse(
            List<ParserElement> tokens,
            BiFunction<ParseTree, Integer, Boolean> start,
            Function<ParseTreeNode, T> converter
    ) {
        this.tokens = tokens;
        currentFrame = null; //new ParseTreeFrame(null, 0, 0, baseRule);
        position = 0;

        var result = start.apply(this, 0);

        if (!result || position < tokens.size()) {

            var pos = position < tokens.size() - 1 ? position + 1 : position;

            var tok = tokens.get(pos);
            throw new SyntaxError("Invalid syntax at token " + pos + ": line " +
                    tok.getLineStart() + " and columns from " +
                    tok.getColumnStart() + " to " + tok.getColumnEnd());
        }
        return converter.apply(resultNode);
    }

    @Override
    public ParseTreeMarker enter(int level, ParserRule rule) {
        currentFrame = new ParseTreeFrame(currentFrame, position, level, rule);
//        System.out.println("  ".repeat(level) + "Entering Frame: " + rule + " at level " + level);
        return currentFrame;
    }

    @Override
    public void exit(int level, ParseTreeMarker marker, boolean success) {
        if (marker != currentFrame) {
            throw new ParserException("Mismatched frame reference");
        }
        if (level != currentFrame.getLevel()) {
            throw new ParserException("Mismatched frame level");
        }

        var childFrame = currentFrame;
        currentFrame = childFrame.getParentFrame();

//        var idt = "  ".repeat(childFrame.getLevel());

        if (success) {
//            System.out.println(idt +
//                    "Success in frame: " + childFrame.getRule() + " at level " +
//                    childFrame.getLevel() + " and position " + position);
            addNode(ofRule(childFrame));
        } else {
//            System.out.println(idt +
//                    "Failure in frame: " + childFrame.getRule() + " at level " + childFrame.getLevel());
            addNode(ofFailedRule(childFrame));
            // rollback to the start
            if (currentFrame != null) {
//                System.out.println(idt + "Rollback from " + position + " to " + childFrame.position());
                position = childFrame.position();
            } else {
                position = 0;
            }
        }
    }

    private static ParseTreeNode ofRule(ParseTreeFrame frame) {
        return new IndexNode(frame.getNodes(), true,
                false, frame.getRule(), null);
    }

    private static ParseTreeNode ofFailedRule(ParseTreeFrame frame) {
        return new IndexNode(null, false,
                false, frame.getRule(), null);
    }

    private void addNode(ParseTreeNode node) {
        if (currentFrame == null) {
            resultNode = node;
            return;
        }
        if (currentFrame.collection != null) {
            // as opposed to a rule, collections should not
            // contain a failed rule/node
            if (node.isPresent()) {
                currentFrame.collection.add(node);
            }
        } else {
            currentFrame.getNodes().add(node);
        }
    }

    @Override
    public void enterCollection() {
        if (currentFrame.collection != null) {
            throw new ParserException("Collection already entered");
        }
        currentFrame.collection = new ArrayList<>();
    }

    private static ParseTreeNode ofCollection(List<ParseTreeNode> collection) {
        return new IndexNode(collection, true, true, null, null);
    }

    @Override
    public void exitCollection() {
        if (currentFrame.collection == null) {
            throw new ParserException("Mismatched exit collection");
        }
        var node = ofCollection(currentFrame.collection);
        currentFrame.collection = null;
        addNode(node);
    }

    private static ParseTreeNode ofElement(ParserElement element) {
        return new IndexNode(null, true, false, null, element);
    }

    @Override
    public boolean consumeToken(ElementType type) {
        if (position >= tokens.size()) {
            return false;
        }
        var token = tokens.get(position);
        if (token.getType() == type) {
//            System.out.println("  ".repeat(currentFrame.getLevel()) +
//                    "Success in type " + type + ": " + token.getValue());
            addNode(ofElement(token));
            position++;
            return true;
        }

//        System.out.println("  ".repeat(currentFrame.getLevel()) +
//                "Failure in type " + type + ": " + token.getValue());
        addNode(IndexNode.NULL);
        return false;
    }

    @Override
    public boolean consumeToken(String literal) {
        if (position >= tokens.size()) {
            return false;
        }
        var token = tokens.get(position);
        if (token.getValue().equals(literal)) {
//            System.out.println("  ".repeat(currentFrame.getLevel()) +
//                    "Success in literal " + literal + ": " + token.getValue());
            addNode(ofElement(token));
            position++;
            return true;
        }
//
//        System.out.println("  ".repeat(currentFrame.getLevel()) +
//                "Failure in literal " + literal + ": " + token.getValue());
        addNode(IndexNode.NULL);
        return false;
    }

    @Override
    public int position() {
        return position;
    }

    @Override
    public boolean guardLoopExit(int position) {
        if (position == this.position) {
            throw new ParserException("Loop parsed an empty string");
        }
        return false;
    }

    @Override
    public String toString() {
        return "ParseTree";
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
                var result = new Tokenizer(s.replace("\\n", "\n")).tokenizeAll();

                System.out.print(TokenPPrint.format(result));

                var cst = parser.parse(result, SingleInput::parse, SingleInput::of);
                System.out.print(cst == null ? "null" : cst.prettyFormat(2));
                System.out.println();
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
