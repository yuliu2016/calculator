package org.fugalang.core.object;

public class FConst {
    public static final FObject None = new None();
    public static final FObject NotImplemented = new NotImplemented();
    public static final FBool True = new FBool.True();
    public static final FBool False = new FBool.False();

    public static class NotImplemented extends FObject {
        @Override
        public FObject __str__() {
            return FString.of("NotImplemented");
        }
    }

    public static class None extends FObject {
        @Override
        public FObject __str__() {
            return FString.of("None");
        }
    }
}
