package com.zzy.def.log4j;

import org.apache.log4j.Logger;

public class LogUtil extends LogUtilBase {

    public static Logger getLogger(Class clz) {
        return getLogger(clz, "LibraryMulti");
    }

}
