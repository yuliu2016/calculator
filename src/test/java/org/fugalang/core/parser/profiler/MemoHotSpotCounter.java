package org.fugalang.core.parser.profiler;

import org.fugalang.core.parser.*;

import java.util.IdentityHashMap;
import java.util.Map;

public class MemoHotSpotCounter implements Profiler {

    Map<ParserRule, Integer> countMap = new IdentityHashMap<>();

    @Override
    public void init(ParserContext context) {

    }

    @Override
    public void markEnterFrame(int level, ParserRule rule) {

    }

    @Override
    public void markMemoHit(int level, int pos, ParserRule rule, ParseTreeNode result) {
        if (!countMap.containsKey(rule)) {
            countMap.put(rule, 0);
        }
        countMap.put(rule, countMap.get(rule) + 1);
    }

    @Override
    public void markExitFrame(boolean success, int level, ParserRule rule, int pos) {

    }

    @Override
    public void markElement(boolean success, int level, ElementType expected, ParserElement e) {

    }

    @Override
    public void markLiteral(boolean success, int level, String expected, ParserElement e) {

    }

    public void printResults() {
        countMap
                .entrySet()
                .stream()
                .filter(e -> !e.getKey().isLeftRecursive() && e.getValue() > 0)
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .forEachOrdered(System.out::println);
    }
}
