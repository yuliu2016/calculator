package org.fugalang.core.object;

import org.fugalang.core.object.type.FDescriptor;

public final class FConst {
    public static final FDescriptor MetaType = FDescriptor.INSTANCE;
    public static final FFloat FloatType = FFloat.INSTANCE;
    public static final FLong LongType = FLong.INSTANCE;
    public static final FTuple TupleType = FTuple.INSTANCE;
    public static final FString StringType = FString.INSTANCE;
    public static final FList ListType = FList.INSTANCE;
    public static final FMap MapType = FMap.INSTANCE;
    public static final FSet SetType = FSet.INSTANCE;
}
