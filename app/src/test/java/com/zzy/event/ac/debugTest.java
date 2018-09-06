package com.zzy.event.ac;

import org.junit.Test;

/**
 * debug调试相关
 */
public class debugTest {

    @Test
    public void debug() {
        debugMultiValue(1);
    }

    public void debugMultiValue(int i) {
        if (i == 0) {
            System.out.print("debugMultiValue " + i);
        }
        if (i == 1) {
            System.out.print("debugMultiValue " + i);
        }
        if (i ==2) {
            System.out.print("debugMultiValue " + i);
        }
    }

}
