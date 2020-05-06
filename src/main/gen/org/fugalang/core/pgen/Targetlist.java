package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * targetlist: 'target' (',' 'target')* [',']
 */
public final class Targetlist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("targetlist", RuleType.Conjunction, true);

    public static Targetlist of(ParseTreeNode node) {
        return new Targetlist(node);
    }

    private Targetlist(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<Targetlist2> targetlist2List;

    @Override
    protected void buildRule() {
        addRequired(target());
        addRequired(targetlist2List());
        addOptional(isTokenComma(), ",");
    }

    public Target target() {
        var element = getItem(0);
        element.failIfAbsent(Target.RULE);
        return Target.of(element);
    }

    public List<Targetlist2> targetlist2List() {
        if (targetlist2List != null) {
            return targetlist2List;
        }
        List<Targetlist2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Targetlist2.of(node));
        }
        targetlist2List = result == null ? Collections.emptyList() : result;
        return targetlist2List;
    }

    public boolean isTokenComma() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Target.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!Targetlist2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'target'
     */
    public static final class Targetlist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("targetlist:2", RuleType.Conjunction, false);

        public static Targetlist2 of(ParseTreeNode node) {
            return new Targetlist2(node);
        }

        private Targetlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenComma(), ",");
            addRequired(target());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Target target() {
            var element = getItem(1);
            element.failIfAbsent(Target.RULE);
            return Target.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Target.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
