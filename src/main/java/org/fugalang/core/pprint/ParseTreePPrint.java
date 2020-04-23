package org.fugalang.core.pprint;

import java.util.List;

public class ParseTreePPrint implements CSTPrintBuilder {
    String name;
    List<String> elems;
    int indent;
    int curr_indent;

    public ParseTreePPrint(CSTPrintElem elem, int indent, int curr_indent) {
        this.name = elem.getClass().getSimpleName();
        this.indent = indent;
        this.curr_indent = curr_indent;
        elem.buildString(this);
    }

    public String asString() {
        if (indent < 0) {
            return "(" + name + " " + String.join(" ", elems) + ")";
        }
        var sb = new StringBuilder();
        var idt = " ".repeat(curr_indent);
        sb.append("(").append(name).append('\n');
        for (var elem : elems) {
            sb.append(idt).append(elem);
        }
        sb.append(idt).append(")").append("\n");
        return sb.toString();
    }

    @Override
    public CSTPrintBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public CSTPrintBuilder addString(String token) {
        elems.add(token);
        return this;
    }

    @Override
    public CSTPrintBuilder addElem(CSTPrintElem elem) {
        var new_indent = indent < 0 ? 0 : curr_indent + indent;
        elems.add(new ParseTreePPrint(elem, indent, new_indent).asString());
        return this;
    }

    @Override
    public CSTPrintBuilder addElems(List<? extends CSTPrintElem> elems) {
        for (var elem : elems) {
            addElem(elem);
        }
        return this;
    }

    @Override
    public String toString() {
        return asString();
    }

    public static String format(CSTPrintElem elem, int indent) {
        return new ParseTreePPrint(elem, indent, 0).asString();
    }
}
