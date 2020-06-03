package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.*;

import java.util.*;
import java.util.function.Function;

/**
 * PEG parse tree implementation
 * Source of implementation algorithms:
 * https://github.com/python/cpython/blob/master/Grammar/python.gram
 * https://medium.com/@gvanrossum_83706/left-recursive-peg-grammars-65dab3c580e1
 * https://github.com/PhilippeSigaud/Pegged/wiki/Left-Recursion
 */
public class SimpleParseTree implements ParseTree {

    public static final int MAX_RECURSION_LEVEL = 300;

    private ParseTreeNode result_node;

    private int pos = 0;
    private int max_reached_pos = 0;
    private int level = 0;

    private final ParserContext context;
    private final Deque<Frame> frame_deque = new ArrayDeque<>();
    private final Map<Integer, Map<ParserRule, Memo>> memo = new HashMap<>();

    private SimpleParseTree(
            ParserContext context,
            Function<ParseTree, Boolean> start) {
        this.context = context;
        var result = start.apply(this);
        if (pos > max_reached_pos) {
            max_reached_pos = pos;
        }
        if (!result || !context.didFinish(pos)) {
            context.errorForElem(max_reached_pos, "Invalid syntax");
        }
    }

    private <T> T getResult(Function<ParseTreeNode, T> converter) {
        return converter.apply(result_node);
    }

    private Frame peekFrame() {
        if (frame_deque.isEmpty()) {
            throw new ParserException("No frame on deque");
        }
        return frame_deque.peek();
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
    public Boolean enter(ParserRule rule) {
        level++;
        if (level > MAX_RECURSION_LEVEL) {
            if (!frame_deque.isEmpty()) {
                // Don't throw an exception because a long stack trace isn't really needed
                System.err.println("Max recursion level of " + MAX_RECURSION_LEVEL +
                        " reached inside rule " + rule);
            }
            level--;
            return false;
        }

        if (context.isDebug()) {
            context.log("  ".repeat(level) + "Entering Frame: " + rule);
        }

        Map<ParserRule, Memo> memo_at_pos;
        if (memo.containsKey(pos)) {
            memo_at_pos = memo.get(pos);

            if (memo_at_pos.containsKey(rule)) {
                var value = memo_at_pos.get(rule);

                if (context.isDebug()) {
                    context.log("  ".repeat(level) + "Memo found for pos " + pos +
                            " and rule " + rule + "; " + value);
                }

                // exit won't be called, so level must be decreased
                level--;
                if (value.hasResult()) {
                    addNode(value.getResult());
                    pos = value.getEndPos();
                    return true;
                } else {
                    addNode(IndexNode.NULL);
                    // don't change the position
                    // because the frame would fail
                    if (pos != value.getEndPos()) throw new ParserException();
                    return false;
                }
            }
        } else {
            // IdentityHashMap is used here because
            // it only makes sense to cache identical rules
            memo_at_pos = new IdentityHashMap<>();
            memo.put(pos, memo_at_pos);
        }

        var new_frame = new Frame(pos, rule, memo_at_pos);
        frame_deque.push(new_frame);

        if (pos > max_reached_pos) {
            max_reached_pos = pos;
        }

        return null;
    }

    @Override
    public void exit(boolean success) {
        var frame = frame_deque.pop();

        // add to the memo ONLY if the parent frame is not exepecting left-recursion
        // caching is handled by save instead
        var enable_memo = frame_deque.isEmpty() ||
                frame_deque.peek().left_recursion_nodes == null;

        if (success) {
            if (context.isDebug()) {
                context.log("  ".repeat(level) +
                        "Success in frame: " + frame.rule + " at position " + pos);
            }
            var node_from_frame = IndexNode.fromFrame(frame);
            addNode(node_from_frame);
            if (enable_memo) {
                frame.memo_at_pos.put(frame.rule, new Memo(pos, node_from_frame));
            }
        } else {
            if (context.isDebug()) {
                context.log("  ".repeat(level) +
                        "Failure in frame: " + frame.rule);
            }
            addNode(IndexNode.NULL);
            pos = frame.position;
            if (enable_memo) {
                frame.memo_at_pos.put(frame.rule, new Memo(pos, null));
            }
        }
        level--;
    }

    @Override
    public void cache(boolean success) {
        var frame = peekFrame();

        if (success) {
            var node_from_frame = IndexNode.fromFrame(frame);
            frame.memo_at_pos.put(frame.rule, new Memo(pos, node_from_frame));
        } else {
            frame.memo_at_pos.put(frame.rule, new Memo(frame.position, null));
        }

        // remember the previous nodes, because when the left-recursion rule
        // cannot parse a longer string, it needs to be restored
        frame.left_recursion_nodes = frame.nodes;
        frame.nodes = new ArrayList<>();
        pos = frame.position;
    }

    @Override
    public void restore(int position) {
        var frame = peekFrame();
        frame.nodes = frame.left_recursion_nodes;
        frame.left_recursion_nodes = null;
        pos = position;
    }

    @Override
    public void enterLoop() {
        var frame = peekFrame();
        if (frame.collection != null) {
            throw new ParserException("Collection already entered");
        }
        frame.collection = new ArrayList<>();
    }

    @Override
    public void exitLoop() {
        var frame = peekFrame();
        if (frame.collection == null) {
            throw new ParserException("Mismatched exit collection");
        }
        var node = IndexNode.fromCollection(frame.collection);
        frame.collection = null;
        addNode(node);
    }

    @Override
    public int position() {
        return pos;
    }

    @Override
    public boolean loopGuard(int position) {
        if (position == this.pos) {
            System.err.println("Loop parsed an empty string");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean consume(ElementType type) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);

        if (token.getType() == type) {
            if (type.isLiteral()) {
                throw new ParserException("The type " + type +
                        " can only be parsed with literals");
            }
            if (context.isDebug()) {
                context.log("  ".repeat(level + 1) +
                        "Success in type " + type + ": Token is '" +
                        token.getValue().replace("\r\n", "\\n")
                                .replace("\n", "\\n") + "'");
            }
            addNode(IndexNode.ofElement(token));
            pos++;
            return true;
        } else {
            if (context.isDebug()) {
                context.log("  ".repeat(level + 1) +
                        "Failure in type " + type + ": Token is '" +
                        token.getValue().replace("\r", "\\r")
                                .replace("\n", "\\n") + "'");
            }
            addNode(IndexNode.NULL);
            return false;
        }
    }

    @Override
    public boolean consume(String literal) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);

        if (token.getType().isLiteral() && token.getValue().equals(literal)) {
            if (context.isDebug()) {
                context.log("  ".repeat(level + 1) +
                        "Success in literal '" + literal + "'");
            }
            addNode(IndexNode.ofElement(token));
            pos++;
            return true;
        } else {
            if (context.isDebug()) {
                context.log("  ".repeat(level + 1) +
                        "Failure in literal '" + literal + "': Token is '" +
                        token.getValue().replace("\r", "\\r")
                                .replace("\n", "\\n") + "'");
            }
            addNode(IndexNode.NULL);
            return false;
        }
    }

    @Override
    public boolean skip(String literal) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);

        if (token.getType().isLiteral() && token.getValue().equals(literal)) {
            if (context.isDebug()) {
                context.log("  ".repeat(level + 1) +
                        "Success in literal '" + literal + "'");
            }
            pos++;
            return true;
        } else {
            if (context.isDebug()) {
                context.log("  ".repeat(level + 1) +
                        "Failure in literal '" + literal + "': Token is '" +
                        token.getValue().replace("\r", "\\r")
                                .replace("\n", "\\n") + "'");
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return "ParseTree";
    }

    public static <T> T parse(
            ParserContext context,
            Function<ParseTree, Boolean> start,
            Function<ParseTreeNode, T> converter
    ) {
        return new SimpleParseTree(context, start).getResult(converter);
    }
}
