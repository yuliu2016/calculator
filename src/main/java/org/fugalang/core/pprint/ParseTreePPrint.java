package org.fugalang.core.pprint;

import java.util.ArrayList;
import java.util.List;

public class ParseTreePPrint implements TreeStringBuilder {
    String name;
    List<String> elems = new ArrayList<>();
    int indent;
    int curr_indent;
    boolean recur = false;

    public ParseTreePPrint(TreeStringElem elem, int indent, int curr_indent) {
        this.name = elem.getClass().getSimpleName();
        this.indent = indent;
        this.curr_indent = indent < 0 ? 0 : curr_indent + indent;
        elem.buildString(this);
    }

    public String asString() {
        if (indent < 0 || !recur) {
            return "(" + name + " " + String.join(" ", elems) + ")";
        }
        var sb = new StringBuilder();
        var idt = " ".repeat(curr_indent);
        sb.append("(").append(name).append('\n');
        for (var elem : elems) {
            sb.append(idt).append(elem).append("\n");
        }
        sb.append(" ".repeat(curr_indent - indent)).append(")");
        return sb.toString();
    }

    @Override
    public TreeStringBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public TreeStringBuilder addString(String token) {
        elems.add("'" + token + "'");
        return this;
    }

    @Override
    public TreeStringBuilder addElem(TreeStringElem elem) {
        recur = true;
        elems.add(new ParseTreePPrint(elem, indent, curr_indent).asString());
        return this;
    }

    @Override
    public TreeStringBuilder addElems(List<? extends TreeStringElem> elems) {
        for (var elem : elems) {
            addElem(elem);
        }
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
