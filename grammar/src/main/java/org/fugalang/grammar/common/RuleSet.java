package org.fugalang.grammar.common;

import java.util.List;
import java.util.Map;

public record RuleSet(
        List<NamedRule> namedRules,
        Map<String, TokenEntry> tokenMap
) {
}
