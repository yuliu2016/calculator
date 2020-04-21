package org.fugalang.core.object.type;

import static org.fugalang.core.object.type.FgTypeFunc.*;

public class FgMappingMethods {
    public final LenFunc mp_length;
    public final BinaryFunc mp_subscript;
    public final ObjObjProc mp_ass_subscript;

    public FgMappingMethods(LenFunc mp_length, BinaryFunc mp_subscript, ObjObjProc mp_ass_subscript) {
        this.mp_length = mp_length;
        this.mp_subscript = mp_subscript;
        this.mp_ass_subscript = mp_ass_subscript;
    }
}
