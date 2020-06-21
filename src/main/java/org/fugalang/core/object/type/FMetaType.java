package org.fugalang.core.object.type;

import org.fugalang.core.object.call.FCallable;

public class FMetaType {
    String name;
    FCallable[] _binary_op;
    FCallable[] _unary_op;
    FCallable[] _compare_op;
    FCallable call;
}
