package org.fugalang.grammar.main;

import org.fugalang.grammar.cgen.CpegBuilder;
import org.fugalang.grammar.cgen.CpegConfig;

public class CFugaGenerator {
    private static final CpegConfig CONFIG =
            new CpegConfig("FNode", "parser", "include/", null);

    public static void main(String[] args) throws Exception{
        new CpegBuilder(
                GeneratorUtil.readGrammar("/", ""),
                GeneratorUtil.simpleConverter(),
                CONFIG
        ).generate(true);
    }
}
