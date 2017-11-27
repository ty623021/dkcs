package com.rt.zgloan.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类转换初始化
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        //获得超类的泛型参数的实际类型
        Type type = o.getClass().getGenericSuperclass();
        if (type!=null){
            if (type instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) type;
//                返回表示此类型实际类型参数的 Type 对象的数组
                Class<T> params = (Class<T>) parameterizedType.getActualTypeArguments()[i];
                if (params!=null){
                    try {
                        return params.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        /*try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
