package com.rt.zgloan.util;

import java.util.Collection;

/**
 * Created by Administrator on 2017/7/5.
 */

public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection){
        return collection==null||collection.isEmpty()||collection.size()==0;
    }
}
