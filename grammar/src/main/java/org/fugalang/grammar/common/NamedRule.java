package org.fugalang.grammar.common;

import java.util.List;
import java.util.Map;

public record NamedRule(
        UnitRule root,
        List<UnitRule> components,
        Map<String, String> args
) {
}
