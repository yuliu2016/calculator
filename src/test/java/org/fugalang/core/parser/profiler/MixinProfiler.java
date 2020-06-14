package org.fugalang.core.parser.profiler;

import org.fugalang.core.parser.*;

public class MixinProfiler implements Profiler {

    private final Profiler[] profilers;

    public MixinProfiler(Profiler[] profilers) {
        this.profilers = profilers;
    }

    @Override
    public void init(ParserContext context) {
        for (Profiler profiler : profilers) {
            profiler.init(context);
        }
    }

    @Override
    public void markEnterFrame(int level, ParserRule rule) {
        for (Profiler profiler : profilers) {
            profiler.markEnterFrame(level, rule);
        }
    }

    @Override
    public void markMemoHit(int level, int pos, ParserRule rule, ParseTreeNode result) {
        for (Profiler profiler : profilers) {
            profiler.markMemoHit(level, pos, rule, result);
        }
    }

    @Override
    public void markExitFrame(boolean success, int level, ParserRule rule, int pos) {
        for (Profiler profiler : profilers) {
            profiler.markExitFrame(success, level, rule, pos);
        }
    }

    @Override
    public void markElement(boolean success, int level, ElementType expected, ParserElement e) {
        for (Profiler profiler : profilers) {
            profiler.markElement(success, level, expected, e);
        }
    }

    @Override
    public void markLiteral(boolean success, int level, String expected, ParserElement e) {
        for (Profiler profiler : profilers) {
            profiler.markLiteral(success, level, expected, e);
        }
    }
}
