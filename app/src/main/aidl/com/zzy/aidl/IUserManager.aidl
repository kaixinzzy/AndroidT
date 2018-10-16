package com.zzy.aidl;

// 导入所有需要使用非基础数据类型的包
import com.zzy.aidl.User;

interface IUserManager {

    //所有返回值前都不需要加任何东西，不管什么数据类型
    List<User> getUsers();
//    User getUser();

    //传参时除了基本数据类型、String、CharSequence外，都需要在前面加上定向tag，具体加什么量需而定
    void setUser(in User user);
//    void setUser(out User user);
//    void setUser(inout User user);
//    void setUserName(String name);

}
