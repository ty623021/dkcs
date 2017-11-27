package com.rt.zgloan.bean;

import java.io.Serializable;

/**
 * 封装服务器返回数据
 */
public class BaseResponse<T> implements Serializable {
    public String code;
    public String msg;
    public T data;

    //    public String time;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }


    public boolean success() {
        return "0".equals(code);
        // return errorCode==0;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", message='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
//    @Override
//    public String toString() {
//        return "BaseResponse{" +
//                "code='" + code + '\'' +
//                ", message='" + msg + '\'' +
//                ", time='" + time + '\'' +
//                ", data=" + data +
//                '}';
//    }
//    @Override
//    public String toString() {
//        return "BaseResponse{" +
//                "code='" + code + '\'' +
//                ", message='" + message + '\'' +
//                ", data=" + data +
//                '}';
//    }
}
