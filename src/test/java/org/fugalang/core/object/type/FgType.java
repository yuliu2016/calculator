package org.fugalang.core.object.type;

import static org.fugalang.core.object.type.FgTypeFunc.*;

@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated
public class FgType {
    public final String tp_name;
    public final FgNumberMethods tp_number;
    public final FgSequenceMethods tp_sequence;
    public final FgMappingMethods tp_mapping;
    public final ReprFunc tp_repr;
    public final ReprFunc tp_str;
    public final Destructor tp_dealloc;
    public final TerneryFunc tp_call;

    public FgType(
            String tp_name,
            FgNumberMethods tp_number,
            FgSequenceMethods tp_sequence,
            FgMappingMethods tp_mapping,
            ReprFunc tp_repr,
            ReprFunc tp_str,
            Destructor tp_dealloc,
            TerneryFunc tp_call
    ) {
        this.tp_name = tp_name;
        this.tp_number = tp_number;
        this.tp_sequence = tp_sequence;
        this.tp_mapping = tp_mapping;
        this.tp_repr = tp_repr;
        this.tp_str = tp_str;
        this.tp_dealloc = tp_dealloc;
        this.tp_call = tp_call;
    }
}
