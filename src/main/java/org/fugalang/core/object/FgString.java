package org.fugalang.core.object;

import org.fugalang.core.object.type.FgType;

public class FgString extends FgObject {

    private final String value;

    public FgString(String value) {
        super(STR_TYPE);
        this.value = value;
    }

    public FgString() {
        this("");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    private String repr() {
        return "\"" + value + "\"";
    }

    public static FgString String_Repr(FgObject object) {
        return new FgString(((FgString) object).repr());
    }

    public static FgString String_Str(FgObject object) {
        return (FgString) object;
    }

    private static final FgType STR_TYPE = new FgType(
            "StringType",
            null,
            null,
            null,
            FgString::String_Repr,
            FgString::String_Str,
            null,
            null
    );
}
