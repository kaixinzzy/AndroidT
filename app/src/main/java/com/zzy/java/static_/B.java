package com.zzy.java.static_;

import java.io.File;

public class B {
    public static B b = new B("b");

    public static final String SDCARD_PATH = File.separator + "mnt"  +  File.separator + "sdcard";

    private B(String b){
        System.out.println(b + B.SDCARD_PATH);
        System.out.println(b + A.PATH);
    }

}
