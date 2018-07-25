package com.zzy.def.log4j;

import org.apache.log4j.Logger;

public class LogUtilBase {

    /**
     * 获取类的name
     * @return class name
     */
    public static String makeLogTag(Class cls) {
        return cls.getName();
    }

    /**
     *
     * @param clz Class
     * @param fileName Log文件名称
     * @return Logger
     */
    public static Logger getLogger(Class clz, String fileName) {
        return Log4jMessage.configLog(clz, fileName);
    }

}
