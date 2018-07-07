package com.zzy.okhttp;

import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by BCLZzy on 2018/6/21.
 */

public interface IRequestManager {

    /**
     * okHttp get同步请求
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    void requestGetBySyn(String actionUrl, HashMap<String, String> paramsMap);

    // 替换
    //void requestGetBYSyn();

    /**
     * okHttp get异步请求
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param callBack 请求返回数据回调
     * @param <T> 数据泛型
     * @return
     */
    <T> Call requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack);


    /**
     * okHttp post同步请求
     * @param actionUrl 接口地址
     * @param paramsMap 拼接字符串
     */
    void requestPostBySyn(String actionUrl, HashMap<String, String> paramsMap);

    /**
     * okHttp post同步请求
     * @param actionUrl 接口地址
     * @param paramsMap 表单
     */
    void requestPostBySynWithForm(String actionUrl, HashMap<String, String> paramsMap);

    /**
     * okHttp post异步请求
     * @param actionUrl 接口地址
     * @param paramsMap 拼接字符串
     * @param callBack 请求返回数据回调
     * @param <T> 数据泛型
     * @return
     */
    <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack);

    /**
     * okHttp post异步请求表单提交
     * @param actionUrl 接口地址
     * @param paramsMap 表单
     * @param callBack 请求返回数据回调
     * @param <T> 数据泛型
     * @return
     */
    <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack);
}
