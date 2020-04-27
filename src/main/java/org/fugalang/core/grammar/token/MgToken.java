package org.fugalang.core.grammar.token;

import java.util.Objects;

public class MgToken {
    public final MgTokenType type;
    public final String value;

    public MgToken(MgTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MgToken mgToken = (MgToken) o;
        return type == mgToken.type &&
                Objects.equals(value, mgToken.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        if (type == MgTokenType.NEWLINE) {
            return type.name() + "\n";
        }
        if (value == null) {
            return type.name() + " ";
        }
        return "'" + value + "' ";
    }
}
