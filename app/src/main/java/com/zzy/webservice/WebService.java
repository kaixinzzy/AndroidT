package com.zzy.webservice;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by BCLZzy on 2017/9/27.
 * 公开接口：
 * 获得国内手机号码归属地省份、地区和手机卡类型信息
 *      http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?op=getMobileCodeInfo
 * 更多接口
 *      http://www.webxml.com.cn/zh_cn/index.aspx
 */

/*
有关WSDL格式的说明：
    通过java jdk 自带的一个命令wsimport 根据服务端说明书（wsdl）生成本地的java代码,我们直接操作这些java 代码，就可以调用webservice
    命令格式：
        wsimport -d . +服务说明书（wsdl）的地址 生成本地的class文件
        wsimport -s . +服务说明书（wsdl）的地址 生成本地的class文件与java文件
        wsimport -s . -p(包名)+服务说明书（wsdl）的地址 生成本地的class文件与java文件
    eg:
        wsimport -s . http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl
    ----------------------- 请求部分start ----------------------------
    WebService需要的字段读取：
    endPoint :
        由Host: ws.webxml.com.cn中的ws.webxml.com.cn 和 POST /WebServices/MobileCodeWS.asmx HTTP/1.1中的/WebServices/MobileCodeWS.asmx 拼接而成
        拼接后为：ws.webxml.com.cn/WebServices/MobileCodeWS.asmx
    nameSpace（命名空间）:
        从<getMobileCodeInfo xmlns="http://WebXml.com.cn/">中获取
        获取后为：http://WebXml.com.cn/
    methodName（调用方法名）:
        从<getMobileCodeInfo xmlns="http://WebXml.com.cn/">中获取
        获取后为：getMobileCodeInfo
    soapAction(SOAP Action):
        有上面的nameSpace + methodName拼接而成
        拼接后为：http://WebXml.com.cn/getMobileCodeInfo
    传入参数：
        mobileCode 和 userID
    ---------------------- 以下为WSDL格式范本
    POST /WebServices/MobileCodeWS.asmx HTTP/1.1
    Host: ws.webxml.com.cn
    Content-Type: application/soap+xml; charset=utf-8
    Content-Length: length

    <?xml version="1.0" encoding="utf-8"?>
    <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
      <soap12:Body>
        <getMobileCodeInfo xmlns="http://WebXml.com.cn/">
          <mobileCode>string</mobileCode>
          <userID>string</userID>
        </getMobileCodeInfo>
      </soap12:Body>
    </soap12:Envelope>

    ----------------------- 接收部分start ----------------------------
    HTTP/1.1 200 OK
    Content-Type: application/soap+xml; charset=utf-8
    Content-Length: length

    <?xml version="1.0" encoding="utf-8"?>
    <soap12:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://www.w3.org/2003/05/soap-envelope">
      <soap12:Body>
        <getMobileCodeInfoResponse xmlns="http://WebXml.com.cn/">
          <getMobileCodeInfoResult>string</getMobileCodeInfoResult>
        </getMobileCodeInfoResponse>
      </soap12:Body>
    </soap12:Envelope>
 */
public class WebService {
    private final String TAG = this.getClass().getSimpleName();

    /**
     * 手机号段归属地查询
     *
     * @param phoneSec 手机号段
     * @param timeOut 请求超时时间（秒）
     */
    public String getRemoteInfo(String phoneSec, int timeOut) {
        String endPoint = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx";// EndPoint
        String nameSpace = "http://WebXml.com.cn/";// 命名空间
        String methodName = "getMobileCodeInfo";// 调用的方法名称
        String soapAction = "http://WebXml.com.cn/getMobileCodeInfo";// SOAP Action

        // 创建一个信封  并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        // 得到一张信纸 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 添加请求的参数
        rpc.addProperty("mobileCode", phoneSec);
        rpc.addProperty("userID", "");

        envelope.bodyOut = rpc;
        envelope.dotNet = true;// 设置是否调用的是dotNet开发的WebService
        envelope.setOutputSoapObject(rpc);// 等价于envelope.bodyOut = rpc;

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeOut * 1000);
        String result = null;
        try {
            transport.call(soapAction, envelope);// web service请求

            if (envelope.getResponse() != null) {// 等待对方回信
                SoapObject object = (SoapObject) envelope.bodyIn;// 得到信中的内容
                result = object.getProperty(0).toString();// 获取返回的结果
                Log.d(TAG, "result：" + result);
                System.out.println("result is " + result);
            } else {
                result = "";
                Log.d(TAG, "result is null");
                System.out.println("result is null");
            }
        }  catch (SoapFault e) {
            Log.d(TAG, "SoapFault");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.d(TAG, "XmlPullParserException");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询会员卡余额
     * @param SN 设备SN号
     * @param cardNo 要查询的卡号
     * @param timeout 超时
     * @return
     */
    public String getRemainingSum(String SN, String cardNo, int timeout){
        // todo 测试时*替换公司域名
        String baseURL = "http://ytr.bc*.com.cn:80/AutomatSys/";
        String endPoint = baseURL+"InfoQuery.ws";// EndPoint
        String nameSpace = "http://webservice.cinemamanagesys.bcl.com.cn";// 命名空间
        String methodName = "searchYtrCardInfo";// 调用的方法名称
        String soapAction = nameSpace + "/" + methodName;// SOAP Action

        // 创建一个信封  并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        // 得到一张信纸 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        // 添加请求的参数
        rpc.addProperty("terminalSN", SN);
        rpc.addProperty("cardNo", cardNo);

        envelope.bodyOut = rpc;
        envelope.dotNet = true;// 设置是否调用的是dotNet开发的WebService
        envelope.setOutputSoapObject(rpc);// 等价于envelope.bodyOut = rpc;

        HttpTransportSE transport = new HttpTransportSE(endPoint, timeout * 1000);
        String result = null;
        try {
            transport.call(soapAction, envelope);// web service请求

            if (envelope.getResponse() != null && envelope.getResponse() != "") {// 等待对方回信
                SoapObject object = (SoapObject) envelope.bodyIn;// 得到信中的内容
                result = object.getProperty(0).toString();// 获取返回的结果
                if (result.contains("anyType")) {
                    result = "";
                }
                Log.d(TAG, "result：" + result);
                System.out.println("result is " + result);
            } else {
                result = "";
                Log.d(TAG, "result is null");
                System.out.println("result is null");
            }
        }  catch (SoapFault e) {
            Log.d(TAG, "SoapFault");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException");
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.d(TAG, "XmlPullParserException");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
