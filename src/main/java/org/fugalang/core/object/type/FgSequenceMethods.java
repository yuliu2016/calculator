package org.fugalang.core.object.type;

import static org.fugalang.core.object.type.FgTypeFunc.*;

public class FgSequenceMethods {
    public final LenFunc sq_length;
    public final BinaryFunc sq_concat;
    public final IntArgFunc sq_repeat;
    public final IntArgFunc sq_item;
    public final IntObjProc sq_ass_item;
    public final ObjObjProc sq_contains;

    public final BinaryFunc sq_inplace_concat;
    public final IntArgFunc sq_inplace_repeat;

    public FgSequenceMethods(
            LenFunc sq_length,
            BinaryFunc sq_concat,
            IntArgFunc sq_repeat,
            IntArgFunc sq_item,
            IntObjProc sq_ass_item,
            ObjObjProc sq_contains,
            BinaryFunc sq_inplace_concat,
            IntArgFunc sq_inplace_repeat
    ) {
        this.sq_length = sq_length;
        this.sq_concat = sq_concat;
        this.sq_repeat = sq_repeat;
        this.sq_item = sq_item;
        this.sq_ass_item = sq_ass_item;
        this.sq_contains = sq_contains;
        this.sq_inplace_concat = sq_inplace_concat;
        this.sq_inplace_repeat = sq_inplace_repeat;
    }
}
