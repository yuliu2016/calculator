package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.*;

import java.util.*;
import java.util.function.Function;

/**
 * PEG parse tree implementation
 * Source of implementation algorithms:
 * https://github.com/python/cpython/blob/master/Grammar/python.gram
 * https://medium.com/@gvanrossum_83706/left-recursive-peg-grammars-65dab3c580e1
 * https://www.python.org/dev/peps/pep-0617/
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

    private final Profiler profiler;

    private SimpleParseTree(
            ParserContext context,
            Profiler profiler,
            Function<ParseTree, Boolean> start) {
        this.context = context;
        this.profiler = profiler;

        if (profiler != null) {
            profiler.init(context);
        }

        // start parsing
        var result = start.apply(this);
        if (pos > max_reached_pos) {
            max_reached_pos = pos;
        }
        // report error
        if (!result || !context.didFinish(pos)) {
            context.errorForElem(max_reached_pos, "Invalid syntax");
        }
    }

    private ParseTreeNode getResult() {
        return result_node;
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
            System.err.println("Max recursion level of " + MAX_RECURSION_LEVEL +
                    " reached inside rule " + rule);
            level--;
            return false;
        }

        if (profiler != null) {
            profiler.markEnterFrame(level, rule);
        }

        Map<ParserRule, Memo> memo_at_pos;

        if (memo.containsKey(pos)) {
            memo_at_pos = memo.get(pos);

            if (memo_at_pos.containsKey(rule)) {
                var value = memo_at_pos.get(rule);

                if (profiler != null) {
                    profiler.markMemoHit(level, pos, rule, value.result());
                }

                // exit won't be called, so level must be decreased
                level--;
                if (value.hasResult()) {
                    addNode(value.result());
                    pos = value.endPos();
                    return true;
                } else {
                    addNode(IndexNode.NULL);
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

    private static void putMemo(Frame frame, int pos, ParseTreeNode result) {
        var memo = new Memo(pos, result);
        frame.memo_at_pos.put(frame.rule, memo);
    }

    @Override
    public void exit(boolean success) {
        var frame = frame_deque.pop();

        // can't use peekFrame() here because a frame has just been popped,
        // meaning that the deque could indeed be empty
        var parent_frame = frame_deque.peek();

        // add to the memo ONLY if the parent frame is not exepecting left-recursion
        // caching is handled by save instead
        var enable_memo = parent_frame == null ||
                parent_frame.left_recursion_nodes == null;

        if (profiler != null) {
            profiler.markExitFrame(success, level, frame.rule, pos);
        }

        if (success) {
            // there is a case when in test mode, the node object does not
            // need to be created from the frame. But realistically,
            // predicates are likely to be used so that they can be picked
            // up by another rule using memoization, so there's no need
            // to optimize this
            var node_from_frame = IndexNode.fromFrame(frame);

            if (parent_frame != null && parent_frame.isTest) {
                // already tested, changing the state back
                parent_frame.isTest = false;
                // tests do not advance the position, so the current
                // position needs to be rolled back to the start of the frame
                pos = frame.position;
            } else {
                addNode(node_from_frame);
            }
            if (enable_memo) {
                putMemo(frame, pos, node_from_frame);
            }
        } else {
            if (parent_frame != null && parent_frame.isTest) {
                // already tested, changing the state back
                parent_frame.isTest = false;
            } else {
                addNode(IndexNode.NULL);
            }
            pos = frame.position;
            if (enable_memo) {
                putMemo(frame, frame.position, null);
            }
        }
        level--;
    }

    @Override
    public void cache(boolean success) {
        var frame = peekFrame();

        if (success) {
            var node_from_frame = IndexNode.fromFrame(frame);
            putMemo(frame, pos, node_from_frame);
        } else {
            putMemo(frame, frame.position, null);
        }

        List<ParseTreeNode> recycled_list;
        if (frame.left_recursion_nodes != null) {
            recycled_list = frame.left_recursion_nodes;
            recycled_list.clear();
        } else {
            recycled_list = new ArrayList<>();
        }

        // remember the previous nodes, because when the left-recursion rule
        // cannot parse a longer string, it needs to be restored
        frame.left_recursion_nodes = frame.nodes;
        frame.nodes = recycled_list;
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
        peekFrame().collection = new ArrayList<>();
    }

    @Override
    public void exitLoop() {
        var frame = peekFrame();
        var node = IndexNode.fromCollection(frame.collection);
        frame.collection = null;
        addNode(node);
    }

    @Override
    public int position() {
        return pos;
    }

    @Override
    public void reset(int position) {
        pos = position;
    }

    @Override
    public ParseTree test() {
        peekFrame().isTest = true;
        return this;
    }

    private void consumeToken(boolean success, ParserElement token) {
        var frame = peekFrame();
        if (frame.isTest) {
            // already tested, changing the state back
            frame.isTest = false;
        } else {
            if (success) {
                addNode(IndexNode.ofElement(token));
                pos++;
            } else {
                addNode(IndexNode.NULL);
            }
        }
    }

    @Override
    public boolean consume(ElementType type) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);
        boolean success = token.getType() == type;
        if (profiler != null) {
            profiler.markElement(success, level, type, token);
        }
        consumeToken(success, token);
        return success;
    }

    @Override
    public boolean consume(String literal) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);
        var success = token.getType().isLiteral() &&
                token.getValue().equals(literal);
        if (profiler != null) {
            profiler.markLiteral(success, level, literal, token);
        }
        consumeToken(success, token);
        return success;
    }

    @Override
    public boolean skip(String literal) {
        if (context.didFinish(pos)) {
            return false;
        }
        var token = context.getElem(pos);
        var success = token.getType().isLiteral() &&
                token.getValue().equals(literal);
        if (profiler != null) {
            profiler.markLiteral(success, level, literal, token);
        }
        if (success) {
            pos++;
        }
        return success;
    }

    @Override
    public String toString() {
        return "ParseTree";
    }

    public static ParseTreeNode parse(
            ParserContext context,
            Function<ParseTree, Boolean> start
    ) {
        return parse(context, null, start);
    }

    public static ParseTreeNode parse(
            ParserContext context,
            Profiler profiler,
            Function<ParseTree, Boolean> start
    ) {
        return new SimpleParseTree(context, profiler, start).getResult();
    }
}
