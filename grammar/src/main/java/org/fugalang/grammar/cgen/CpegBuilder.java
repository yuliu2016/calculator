package org.fugalang.grammar.cgen;

import org.fugalang.grammar.gen.PEGBuilder;
import org.fugalang.grammar.gen.TokenConverter;
import org.fugalang.grammar.peg.wrapper.Grammar;
import org.fugalang.grammar.peg.wrapper.Rule;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The C version of {@link PEGBuilder}
 */
public class CpegBuilder {
    private final List<Rule> gRules;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final CpegRuleSet ruleSet;

    public CpegBuilder(
            Grammar grammar,
            TokenConverter converter,
            CpegConfig config) {
        this.gRules = grammar.rules();
        this.converter = converter;
        this.ruleSet = new CpegRuleSet(config);
    }

    public void generate(boolean toFiles) {

    }
}
