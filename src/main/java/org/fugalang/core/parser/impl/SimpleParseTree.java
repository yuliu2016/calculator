package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SimpleParseTree implements ParseTree {

    public static final int MAX_RECURSION_LEVEL = 300;

    private ParserContext context;
    private ParseTreeNode result_node;

    private int pos;
    private int max_reached_pos;

    private final Deque<Frame> frame_deque = new ArrayDeque<>();
    private final Map<Integer, Map<ParserRule, Memo>> memo = new HashMap<>();

    private SimpleParseTree() {
    }

    public <T> T parseImpl(
            ParserContext context,
            BiFunction<ParseTree, Integer, Boolean> start,
            Function<ParseTreeNode, T> converter
    ) {
        this.context = context;
        pos = 0;
        max_reached_pos = 0;

        frame_deque.clear();
        memo.clear();

        var result = start.apply(this, 0);

        if (!result) {
            context.errorForElem(max_reached_pos, "Invalid syntax");
        } else if (!context.didFinish(pos)) {
            context.errorForElem(max_reached_pos, "Invalid syntax");
        }

        return converter.apply(result_node);
    }

    @Override
    public Boolean enter(int level, ParserRule rule) {

        if (level > MAX_RECURSION_LEVEL) {
            if (!frame_deque.isEmpty()) {
                System.err.println("Max recursion level of 300 reached inside rule " + rule);
            }
            return false;
        }

        Map<ParserRule, Memo> memo_at_pos;
        if (memo.containsKey(pos)) {
            memo_at_pos = memo.get(pos);

            if (memo_at_pos.containsKey(rule)) {

                var value = memo_at_pos.get(rule);
                context.log("Memo hit for pos: " + pos + " and rule: " + rule + "; " + value);

                if (value.hasResult()) {
                    addNode(value.getResult());
                    pos = value.getEndPos();
                    return true;
                } else {
                    addNode(IndexNode.NULL);
                    // don't change the position
                    // because the frame would fail
                    if (pos != value.getEndPos()) throw new IllegalStateException();
                    return false;
                }
            }
        } else {
            // IdentityHashMap is used here because
            // it only makes sense to cache identical rules
            memo_at_pos = new IdentityHashMap<>();
            memo.put(pos, memo_at_pos);
        }

        var new_frame = new Frame(pos, level, rule, memo_at_pos);
        frame_deque.push(new_frame);

        if (pos > max_reached_pos) {
            max_reached_pos = pos;
        }

        if (context.isDebug()) {
            context.log("  ".repeat(level) + "Entering Frame: " + rule + " at level " + level);
        }
        return null;
    }

    @Override
    public void exit(boolean success) {
        var current_frame = frame_deque.pop();

        var memo_at_pos = current_frame.memo_at_pos;

        if (success) {
            if (context.isDebug()) {
                context.log("  ".repeat(current_frame.level) +
                        "Success in frame: " + current_frame.rule + " at level " +
                        current_frame.level + " and position " + pos);
            }
            var newNode = IndexNode.fromFrame(current_frame);
            addNode(newNode);
            memo_at_pos.put(current_frame.rule, new Memo(pos, newNode));
        } else {
            if (context.isDebug()) {
                context.log("  ".repeat(current_frame.level) +
                        "Failure in frame: " + current_frame.rule + " at level " + current_frame.level);
            }
            addNode(IndexNode.NULL);
            pos = current_frame.position;
            memo_at_pos.put(current_frame.rule, new Memo(pos, null));
        }
    }

    private void addNode(ParseTreeNode node) {
        if (frame_deque.isEmpty()) {
            result_node = node;
            return;
        }
        var current_frame = frame_deque.peek();
        if (current_frame.collection != null) {
            // collections should not contain a failed rule/node
            if (node.isPresent()) {
                current_frame.collection.add(node);
            }
        } else {
            current_frame.nodes.add(node);
        }
    }

    @Override
    public void enterLoop() {
        if (frame_deque.isEmpty()) {
            throw new ParserException("No frame on deque");
        }
        var current_frame = frame_deque.peek();
        if (current_frame.collection != null) {
            throw new ParserException("Collection already entered");
        }
        current_frame.collection = new ArrayList<>();
    }

    @Override
    public void exitLoop() {
        if (frame_deque.isEmpty()) {
            throw new ParserException("No frame on deque");
        }
        var current_frame = frame_deque.peek();
        if (current_frame.collection == null) {
            throw new ParserException("Mismatched exit collection");
        }

        var node = IndexNode.fromCollection(current_frame.collection);
        current_frame.collection = null;
        addNode(node);
    }

    @Override
    public boolean consume(ElementType type) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);

        if (frame_deque.isEmpty()) {
            throw new ParserException("No frame on deque");
        }
        var current_frame = frame_deque.peek();

        if (token.getType() == type) {
            if (type.isLiteral()) {
                throw new ParserException("The type " + type +
                        " can only be parsed with literals");
            }
            if (context.isDebug()) {
                context.log("  ".repeat(current_frame.level + 1) +
                        "Success in type " + type + ": " + token.getValue());
            }
            addNode(IndexNode.ofElement(token));
            pos++;
            return true;
        }

        if (context.isDebug()) {
            context.log("  ".repeat(current_frame.level + 1) +
                    "Failure in type " + type + ": " + token.getValue());
        }
        addNode(IndexNode.NULL);
        return false;
    }

    @Override
    public boolean consume(String literal) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);

        if (frame_deque.isEmpty()) {
            throw new ParserException("No frame on deque");
        }
        var current_frame = frame_deque.peek();

        if (token.getType().isLiteral() && token.getValue().equals(literal)) {

            if (context.isDebug()) {
                context.log("  ".repeat(current_frame.level + 1) +
                        "Success in literal " + literal + ": " + token.getValue());
            }

            addNode(IndexNode.ofElement(token));
            pos++;
            return true;
        }

        if (context.isDebug()) {
            context.log("  ".repeat(current_frame.level + 1) +
                    "Failure in literal " + literal + ": " + token.getValue());
        }

        addNode(IndexNode.NULL);
        return false;
    }

    @Override
    public int position() {
        return pos;
    }

    @Override
    public boolean loopGuard(int position) {
        if (position == this.pos) {
            throw new ParserException("Parsed an empty string");
        }
        return false;
    }

    @Override
    public String toString() {
        return "ParseTree";
    }

    private static final SimpleParseTree INSTANCE = new SimpleParseTree();

    public static <T> T parse(
            ParserContext context,
            BiFunction<ParseTree, Integer, Boolean> start,
            Function<ParseTreeNode, T> converter
    ) {
        return INSTANCE.parseImpl(context, start, converter);
    }
}
