package org.fugalang.grammar.cgen;

import java.util.ArrayList;
import java.util.List;

public class CpegRuleSet {
    private final CpegConfig config;
    private final List<CpegNamedRule> namedRules;

    public CpegRuleSet(CpegConfig config) {
        this.config = config;
        this.namedRules = new ArrayList<>();
    }

    public List<CpegNamedRule> getNamedRules() {
        return namedRules;
    }

    void writeToFiles() {

    }
}
