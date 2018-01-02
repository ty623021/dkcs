package com.rt.zgloan.activity.cityActivity;

import java.util.ArrayList;

public class CityData {

	// 汉字返回拼音，字母原样返回，都转换为小写
	public static String getPinYin(String input){
		if(StrUtils.isNull(input)){
			return "";
		}
		ArrayList<HanziToPinyin3.Token> tokens = HanziToPinyin3.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0){
			for (HanziToPinyin3.Token token : tokens){
				if (HanziToPinyin3.Token.PINYIN == token.type){
					sb.append(token.target);
				} else{
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}
	
}
