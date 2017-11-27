/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rt.zgloan.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


public class ToastUtil {


    /**
     * toast对象
     */
    private static Toast toast=null;

	private static Context mContext;

	/**
	 * 程序入口注册
	 * @param context
     */
	public static void register(Context context){
		mContext=context.getApplicationContext();
	}
    /**
     * 描述：Toast提示文本.
     * @param text  文本
     */
	public static void showToast(String text) {
		if (toast==null) {
			toast= Toast.makeText(mContext,text, Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER,0,0);
		}else{
			toast.setText(text);
		}
		toast.show();
	}
	
	/**
     * 描述：Toast提示文本.
     * @param resId  文本的资源ID
     */
	public static void showToast(int resId) {
		showToast(mContext.getString(resId));
	}

}
