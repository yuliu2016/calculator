package org.fugalang.grammar.token;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;

import java.util.Objects;

@Deprecated
public class MetaToken implements ParserElement {
    public final ElementType type;
    public final String value;

    public MetaToken(ElementType type, String value) {
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

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public int getLineStart() {
        return 0;
    }

    @Override
    public int getLineEnd() {
        return 0;
    }

    @Override
    public int getColumnStart() {
        return 0;
    }

    @Override
    public int getColumnEnd() {
        return 0;
    }

    @Override
    public String getValue() {
        return value;
    }
}
