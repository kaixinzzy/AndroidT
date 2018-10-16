package com.zzy.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * AIDL传递的对象必须序列化
 */
public class User implements Parcelable {

    private String name;
    private int age;
    private String time;

    /**
     * 定向tag out or inout时用
     * @param dest dest
     */
    public void readFromParcel(Parcel dest) {
        // 注意：此处的读值顺序应当和writeToParcel方法中一致
        name = dest.readString();
        age = dest.readInt();
        time = dest.readString();
    }

    // 自动生成方法----------------------------------------------------------------------------------
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeString(this.time);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //get set方法-----------------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
