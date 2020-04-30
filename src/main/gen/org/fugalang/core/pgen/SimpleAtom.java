package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// simple_atom: 'NAME' | 'NUMBER' | 'STRING' | 'None' | 'True' | 'False'
public final class SimpleAtom extends DisjunctionRule {
    public static final String RULE_NAME = "simple_atom";

    private final String name;
    private final Object number;
    private final String string;
    private final boolean isTokenNone;
    private final boolean isTokenTrue;
    private final boolean isTokenFalse;

    public SimpleAtom(
            String name,
            Object number,
            String string,
            boolean isTokenNone,
            boolean isTokenTrue,
            boolean isTokenFalse
    ) {
        this.name = name;
        this.number = number;
        this.string = string;
        this.isTokenNone = isTokenNone;
        this.isTokenTrue = isTokenTrue;
        this.isTokenFalse = isTokenFalse;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("name", name);
        addChoice("number", number);
        addChoice("string", string);
        addChoice("isTokenNone", isTokenNone);
        addChoice("isTokenTrue", isTokenTrue);
        addChoice("isTokenFalse", isTokenFalse);
    }

    public String name() {
        return name;
    }

    public Object number() {
        return number;
    }

    public String string() {
        return string;
    }

    public boolean isTokenNone() {
        return isTokenNone;
    }

    public boolean isTokenTrue() {
        return isTokenTrue;
    }

    public boolean isTokenFalse() {
        return isTokenFalse;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }
}
