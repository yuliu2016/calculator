package org.fugalang.core.pprint;

import org.fugalang.core.parser.TreeStringBuilder;
import org.fugalang.core.parser.TreeStringElem;

import java.util.ArrayList;
import java.util.List;

public class ParseTreePPrint implements TreeStringBuilder {
    private final List<String> elems = new ArrayList<>();
    private final int indent;
    private final int curr_indent;

    private String name;
    private boolean atLeastOneComp = false;
    private String openBracket = "(";
    private String closeBracket = ")";
    private String delimiter = "";
    private boolean isComplex = false;

    public ParseTreePPrint(TreeStringElem elem, int indent, int curr_indent) {
        this.name = null;
        this.indent = indent;
        this.curr_indent = indent < 0 ? 0 : curr_indent + indent;
        elem.buildString(this);
    }

    public String asString() {
        if (indent < 0 || !isComplex) {
            var maybeName = name == null ? "" : name + " ";
            return openBracket + maybeName +
                    String.join(delimiter + " ", elems) + closeBracket;
        }
        var maybeName = name == null ? "" : name;
        var sb = new StringBuilder();
        var idt = " ".repeat(curr_indent);
        sb.append(openBracket).append(maybeName).append('\n');
        for (int i = 0; i < elems.size(); i++) {
            String elem = elems.get(i);
            sb.append(idt).append(elem);
            if (i < elems.size() - 1) {
                sb.append(delimiter);
            }
            sb.append("\n");
        }
        sb.append(" ".repeat(curr_indent - indent)).append(closeBracket);
        return sb.toString();
    }

    @Override
    public TreeStringBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TreeStringBuilder setOpenBracket(String openBracket) {
        this.openBracket = openBracket;
        return this;
    }

    @Override
    public TreeStringBuilder setCloseBracket(String closeBracket) {
        this.closeBracket = closeBracket;
        return this;
    }

    @Override
    public TreeStringBuilder setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    @Override
    public TreeStringBuilder addString(String token) {
        elems.add("'" + token.replace(System.lineSeparator(), "\\n") + "'");
        return this;
    }

    @Override
    public TreeStringBuilder addElem(TreeStringElem elem) {
        var comp = new ParseTreePPrint(elem, indent, curr_indent);
        elems.add(comp.asString());
        isComplex = comp.isComplex || atLeastOneComp;
        atLeastOneComp = true;
        return this;
    }

    @Override
    public String toString() {
        return asString();
    }

    public static String format(TreeStringElem elem, int indent) {
        return new ParseTreePPrint(elem, indent, 0).asString();
    }
}
