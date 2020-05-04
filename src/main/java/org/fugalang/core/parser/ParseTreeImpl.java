package org.fugalang.core.parser;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.pgen.Atom;
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

    public <T extends NodeWrapper> T parse(
            List<ParserElement> tokens,
            BiFunction<ParseTree, Integer, Boolean> start,
            Function<ParseTreeNode, T> converter
    ) {
        this.tokens = tokens;
        currentFrame = null; //new ParseTreeFrame(null, 0, 0, baseRule);
        position = 0;

        var result = start.apply(this, 0);

        if (result) {
            return converter.apply(resultNode);
        } else {
            return null;
        }
    }

    @Override
    public ParseTreeMarker enter(int level, ParserRule rule) {
        currentFrame = new ParseTreeFrame(currentFrame, position, level, rule);
        System.out.println("Entering Frame: " + rule + " at level " + level);
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

        if (success) {
            System.out.println("Success in frame: " + childFrame.getRule() + " at level " + childFrame.getLevel());
            addNode(ofRule(childFrame));
        } else {
            System.out.println("Failure in frame: " + childFrame.getRule() + " at level " + childFrame.getLevel());
            addNode(ofFailedRule(childFrame));
            // rollback to the start
            if (currentFrame != null) {
                position = currentFrame.position();
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
            currentFrame.collection.add(node);
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
        System.out.println("type: " + token.getValue());
        if (token.getType() == type) {
            addNode(ofElement(token));
            position++;
            return true;
        }

        addNode(IndexNode.NULL);
        return false;
    }

    @Override
    public boolean consumeToken(String literal) {
        if (position >= tokens.size()) {
            return false;
        }
        var token = tokens.get(position);
        System.out.println("literal: " + token.getValue());
        if (token.getValue().equals(literal)) {
            addNode(ofElement(token));
            position++;
            return true;
        }
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

                var cst = parser.parse(result, Atom::parse, Atom::of);

                System.out.print(ConsoleColor.MAGENTA);
                System.out.print(cst);
                System.out.println(ConsoleColor.END);
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
