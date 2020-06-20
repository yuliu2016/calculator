package org.fugalang.core.eval;

import java.util.ArrayDeque;
import java.util.Deque;

public class FVirtualFrame {
    FVirtualFrame parent_frame = null;
    Deque<Object> stack = new ArrayDeque<>();
}
