package org.fugalang.core.grammar.token;

import java.util.Objects;

public class MetaToken {
    public final MetaTokenType type;
    public final String value;

    public MetaToken(MetaTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaToken metaToken = (MetaToken) o;
        return type == metaToken.type &&
                Objects.equals(value, metaToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        if (type == MetaTokenType.NEWLINE) {
            return type.name() + "\n";
        }
        if (value == null) {
            return type.name() + " ";
        }
        return "'" + value + "' ";
    }
}
