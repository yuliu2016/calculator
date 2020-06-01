package org.fugalang.core.object;

public class FString extends FObject {

    public static FString of(String s) {
        return new FString();
    }

    @Override
    public FObject __str__() {
        return null;
    }

    public String getString() {
        return null;
    }
}
