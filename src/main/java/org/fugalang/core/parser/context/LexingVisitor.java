package org.fugalang.core.parser.context;

import org.fugalang.core.grammar.SyntaxError;
import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;
import org.fugalang.core.token.CharTest;

import java.util.ArrayList;
import java.util.List;

public class LexingVisitor implements LexingContext {

    private final String code;

    private int index;

    private final int length;

    // used to lookup newline characters from the last i value
    // -1 because the first token might be from 0
    private int last_end_index = 0;

    // index is line number
    // value is the index of the string at the beginning of the line
    private final List<Integer> line_to_index;

    private LexingVisitor(String code, int index, boolean hasTokenSequence) {
        length = code.length();
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        this.code = code;
        this.index = index;
        line_to_index = hasTokenSequence ? new ArrayList<>(List.of(0)) : null;
    }

    public static LexingContext of(String code) {
        return new LexingVisitor(code, 0, true);
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        if (index < this.index) {
            throw new IllegalStateException("Cannot decrement index from " +
                    this.index + " to " + index);
        }
        this.index = index;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        return code.charAt(index);
    }

    @Override
    public String substring(int beginIndex, int endIndex) {
        return code.substring(beginIndex, endIndex);
    }

    @Override
    public LexingContext copyAndAdvance(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot advance by a negative number");
        }
        return new LexingVisitor(code, index + n, false);
    }

    @Override
    public String peekOrThrow(int n) {
        return code.substring(index, index + n);
    }

    @Override
    public void syntaxError(String message, int offset) {
        var lineno = line_to_index.size() - 1;
        var line = getLine(line_to_index.size() - 1);
        var col = index() - line_to_index.get(lineno);
        throw new SyntaxError(ErrorFormatter.format(message, lineno, line, col + offset));
    }

    @Override
    public ParserElement createElement(ElementType elementType, int beginIndex, int endIndex) {
        if (line_to_index == null) {
            return null;
        }

        if (beginIndex < 0 || beginIndex >= endIndex || endIndex > length) {
            syntaxError("Out of bounds: " +
                    "beginIndex=" + beginIndex + ", endIndex=" + endIndex, 0);
        }

        // Fix: token being empty means that it would equal to last_end_index
        if (beginIndex < last_end_index) {
            syntaxError("Cannot add token: beginIndex=" +
                    beginIndex + ", lastIndex=" + last_end_index, 0);
        }

        // store 0-indexed numbers for lines and cols
        var line_start = line_to_index.size() - 1;

        var col_start = beginIndex - line_to_index.get(line_start);


        for (int i = last_end_index; i < endIndex; i++) {
            var ch1 = code.charAt(i);
            var ch2 = i < length - 1 ? code.charAt(i + 1) : 0;
            if (CharTest.isCRLF(ch1, ch2)) {
                i++;
                line_to_index.add(i + 2);
            } else if (CharTest.isNewline(code.charAt(i))) {
                line_to_index.add(i + 1);
            }
        }

        var line_end = line_to_index.size() - 1;
        var col_end = endIndex - line_to_index.get(line_end);


        var token = new TokenElement(
                elementType,
                substring(beginIndex, endIndex),
                last_end_index,
                line_start,
                line_end,
                col_start,
                col_end
        );

        last_end_index = endIndex;
        return token;
    }

    @Override
    public int lines() {
        return line_to_index.size();
    }

    @Override
    public String getLine(int line) {
        var a = line_to_index.get(line);
        var b = line == line_to_index.size() - 1 ?
                length : line_to_index.get(line + 1);
        return code.substring(a, b);
    }
}
