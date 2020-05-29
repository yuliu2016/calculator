package org.fugalang.core.object.type;

import org.fugalang.core.object.FgObject;

@SuppressWarnings("unused")
@Deprecated
public interface FgTypeFunc {

    @FunctionalInterface
    interface UnaryFunc {
        FgObject call(FgObject object);
    }

    @FunctionalInterface
    interface BinaryFunc {
        FgObject call(FgObject o1, FgObject o2);
    }

    @FunctionalInterface
    interface TerneryFunc {
        FgObject call(FgObject o1, FgObject o2, FgObject o3);
    }

    @FunctionalInterface
    interface LenFunc {
        int call(FgObject object);
    }

    @FunctionalInterface
    interface InquiryFunc {
        int call(FgObject object);
    }

    @FunctionalInterface
    interface IntArgFunc {
        FgObject call(int num);
    }

    @FunctionalInterface
    interface IntTwoArgFunc {
        FgObject call(int num1, int num2);
    }

    @FunctionalInterface
    interface FreeFunc {
        void call();
    }

    @FunctionalInterface
    interface IntObjProc {
        int call(FgObject o1, int idx, FgObject o2);
    }

    @FunctionalInterface
    interface ObjObjProc {
        int call(FgObject o1, FgObject o2, FgObject o3);
    }

    @FunctionalInterface
    interface ReprFunc {
        FgObject call(FgObject object);
    }

    @FunctionalInterface
    interface Destructor {
        void call(FgObject object);
    }
}
