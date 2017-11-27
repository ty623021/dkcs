package com.rt.zgloan.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.rt.zgloan.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class Tool
{
	/** 是否大于2.3，大于2.3才让adapter用自己的onClickListener */
	public final static boolean versionUpGingerbreadMr1;
	
	static {
		versionUpGingerbreadMr1 = Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1;
	}
	// unicode转中文
	public static String decode(String unicodeStr) {
		if (unicodeStr == null) {
			return null;
		}
		
		StringBuffer retBuf = new StringBuffer();
		int maxLoop = unicodeStr.length();
		for (int i = 0; i < maxLoop; i++) {
			if (unicodeStr.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
								.charAt(i + 1) == 'U')))
					try {
						retBuf.append((char) Integer.parseInt(
								unicodeStr.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(unicodeStr.charAt(i));
					}
				else
					retBuf.append(unicodeStr.charAt(i));
			} else {
				retBuf.append(unicodeStr.charAt(i));
			}
		}
		return retBuf.toString();
	}

	/**
	 * utf-8 转unicode
	 * 
	 * @param
	 * @return String
	 */
	public static String toUnicode(String str) {
		char[] arChar = str.toCharArray();
		int iValue = 0;
		String uStr = "";
		for (int i = 0; i < arChar.length; i++) {
			iValue = (int) str.charAt(i);
			char sValue = str.charAt(i);
			if (iValue <= 256) {
				// uStr+="& "+Integer.toHexString(iValue)+";";
				// uStr+="\\"+Integer.toHexString(iValue);
				uStr += sValue;
			} else {
				// uStr+="&#x"+Integer.toHexString(iValue)+";";
				uStr += "\\u" + Integer.toHexString(iValue);
			}
		}
		return uStr;
	}

	// 转32位大写MD5
	public final static String get32MD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			Log.e("Tool", "NoSuchAlgorithmException:" + e.toString());
		} catch (UnsupportedEncodingException e) {
			Log.e("Tool", "UnsupportedEncodingException:" + e.toString());
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}

	// 判断是否是手机号码
	public static boolean isMobileNO(String mobiles) {
		if (isBlank(mobiles))
			return false;
		Pattern p = Pattern.compile("^1[0-9]{10}$");
		// ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// 判断是否是邮箱地址
	public static boolean isEmail(String email) {
		if (isBlank(email))
			return false;
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// 电话号码部分加*号
	public static String changeMobile(String telephone) {
		if (isBlank(telephone)) {
			return "";
		}
		if (!isMobileNO(telephone))
			return telephone;
		return telephone.substring(0, 3) + "****"
				+ telephone.substring(7, telephone.length());
	}

	// 姓名加*号
	public static String changeName(String name) {
		if (isBlank(name)) {
			return "";
		}
		StringBuilder sb=new StringBuilder();
		for (int i = 0; i < name.length(); i++) {
			if (i==0){
				sb.append(name.substring(i,i+1));
			}else{
				sb.append("*");
			}
		}
		return sb.toString();
	}

	// 身份证号加*号
	public static String changeIdentity(String identity) {
		if (isBlank(identity)) {
			return "";
		}
		if (identity.length() != 15 && identity.length() != 18) {
			return "身份证号码异常";
		}
		return identity.substring(0, 4) + "************"
				+ identity.substring(identity.length() - 2, identity.length());
	}

	// 小数进位
	public static double carryAigit(float number) {
		return Math.ceil(number);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	/***
	 * sp转px
	 * @param
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (spValue * fontScale + 0.5f);  
    } 
	// 判断是否为数字
	public static boolean isNumeric(String str) {
		if (Tool.isBlank(str))
			return false;
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// 名字为汉字
	public static boolean isWord(String str) {
		if (Tool.isBlank(str) || str.trim().length() < 2) {
			return false;
		}
		// int n = 0,count=0;
		//
		// for (int i = 0; i < str.length(); i++) {
		// n = (int) str.charAt(i);
		// if ((19968 <= n && n <= 171941)) {
		// count++;
		// }
		// }
		// if(count==str.length())
		// return true;
		// else {
		// return false;
		// }
		return true;
	}

	// 检查银行卡号
	public static boolean checkBankCard(String cardId) {
		char bit = getBankCardCheckCode(cardId
				.substring(0, cardId.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return cardId.charAt(cardId.length() - 1) == bit;
	}

	public static char getBankCardCheckCode(String nonCheckCodeCardId) {
		if (nonCheckCodeCardId == null
				|| nonCheckCodeCardId.trim().length() == 0
				|| !nonCheckCodeCardId.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeCardId.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	

	// 微信分享使用
	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	// 金额由10000分转为100.00元
	public static String toDeciDouble(String num) {
		if (isBlank(num)) {
			return "0.00";
		}
		if(num.contains(".")){
			num = num.substring(0, num.indexOf("."));
		}
		int len = num.length();
		if (len==2 && num.startsWith("-")) {
			return num.substring(0, 1)  +"0.0"+ num.substring(len - 1);
		}else if (len==3 && num.startsWith("-")) {
			return num.substring(0, 1)  +"0."+ num.substring(len - 2);
		}else if (len == 1) {
			return "0.0" + num;
		} else if (len == 2) {
			return "0." + num;
		} else {
			return num.substring(0, len - 2) + "." + num.substring(len - 2);
		}
	}

    /**
     * 转两位小数 单位：元
     * @param moneyInY
     * @return
     */
    public static String toDeciDouble2(double moneyInY) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(moneyInY);
    }



	/**
	 * 转两位小数 四舍5入 单位：元
	 * @param moneyInY
	 * @return
	 */
	public static String toDeciDoubleHalf(double moneyInY) {
		DecimalFormat df = new DecimalFormat("#0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(moneyInY);
	}
    /***
     * 转一位小数（留下一位小数，但是不会四舍五入）
     * @param moneyInY
     * @return
     */
    public static float toDeciDouble1(float moneyInY) {
    	DecimalFormat df = new DecimalFormat("#0.0");
    	float val = 0.0f;
    	try {
			
    		val = Float.parseFloat(df.format(moneyInY));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return val; 
    }
    

	// 金额由10000分转为100元
	public static String toIntAccount(String num) {
		if (isBlank(num)) {
			return "0";
		}
		return "" + Long.parseLong(num) / 100;
	}
	
	// 金额由10000分转为100元
	public static long toIntAccountLong(String num) {
		if (isBlank(num)) {
			return 0;
		}
		return Long.parseLong(num) / 100;
	}

	/** 金额由1000000分转为10,000元 */
	public static String toDivAccount(String num) {
		if (isBlank(num)) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(Long.parseLong(num) / 100);
	}

	// 金额由10000.00转为10,000.00元
	public static String toDivAccount2(String num) {
		if (isBlank(num)) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#,###.00");
		return df.format(Double.parseDouble(num));
	}
	
	// 可以适配00的情况
	public static String toDivAccount3(String num) {
		if (isBlank(num)) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(Double.parseDouble(num));
	}

	public static String toFFDouble(double num) {
		if (Double.isNaN(num)|| Double.isInfinite(num)) {
			return "0.00";
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setGroupingUsed(false);
		BigDecimal bd = new BigDecimal(num);
		num = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return toDeciDouble(nf.format(num * 100));
	}

	// 将输入的数字转为0.00格式的double
	public static String toForDouble(String num) {
		if (Tool.isBlank(num)) {
			return "0.00";
		}
		return (new DecimalFormat("0.00")).format(Double.parseDouble(num));
	}

	// 将输入的数字转为0.00格式的double
	public static Double toForDouble2(String num) {
		if (Tool.isBlank(num)) {
			return 0.00;
		}
		return Double.parseDouble((new DecimalFormat("0.00")).format(Double.parseDouble(num)));
	}

	public static String trimHeadZero(String num) {
		if (isBlank(num)) {
			return "0";
		} else {
			return num.replaceFirst("^0*", "");
		}
	}

	// 判断字符串对象为null或者""
	public static boolean isBlank(String str) {
		return (str == null || str.isEmpty() || "null".equals(str));
	}

	public static List<String> toList(JSONArray arr) {
		List<String> str_list = new ArrayList<String>();
		for (int i = 0; i < arr.length(); i++) {
			try {
				str_list.add(arr.getString(i));
			} catch (JSONException e) {
				Log.e("Tool", "Failed To List");
				return str_list;
			}
		}
		return str_list;
	}
	/**
	 * 得到状态栏的高度
	 * @param context 上下文
	 * @return 状态栏的高度
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}
	/**
	 * 获取屏幕宽度
	 *
	 * @param context
	 */
	public static int getWindowWith(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int width = windowManager.getDefaultDisplay().getWidth();
		return width;
	}

	/**
	 * 获取屏幕高度
	 *
	 * @param context
	 * @return yinsujun 2015-8-20 下午5:34:18
	 */
	public static int getWindowHeight(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int height = windowManager.getDefaultDisplay().getHeight();
		return height;
	}
	/**
	 * 判断是否连续点击
	 *
	 *  对于 startActivity 设置 singletop 无效果
	 *  则这样 防止 连续点击跳重复界面
	 */
	private static long lastClickTime;
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 500) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	public static void showLog(String tag, String result){
		int start;
		int end;
		int length=4000;
		if (result.length()<length){
			Log.e(tag,result);
		}else{
			int multiple = result.length()/length;
			for (int i = 0; i < multiple; i++) {
				start=i*length;
				end=i*length+length;
				Log.e(tag,result.substring(start,end));
			}
			Log.e(tag,result.substring(result.length()-result.length()%length,result.length()));
		}
	}
	/**
	 * 添加日历事件、日程
	 *
	 * @param mActivity     上下文
	 */
	public static void  closeKeyboard( Activity mActivity){
		InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
	}

	/**
	 *
	 * 检测判断上下文是否存在
	 * @param  mActivity 上下文
	 * */
	public static boolean checkSD(Activity mActivity) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			Toast.makeText(mActivity, "SD卡不存在！", Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	/********
	 * 判断相机是否可用
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean isCamareAviable(Context context)
	{
		boolean flag = false;
		try {
			Camera camera = null;
			camera = Camera.open();
			camera.release();
			flag = true;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}



	/**
	 *
	 * 加载网络图片
	 * */
	public static void loadImage(Activity mActivity,String url, ImageView imageView) {
		Glide.with(mActivity).load(url)//设置图片并且去除换成防止下次加载显示上张图片禁用磁盘缓存
				.placeholder(R.drawable.image_default)//设置图片并且去除换成防止下次加载显示上张图片禁用磁盘缓存
				.error(R.drawable.image_default)
				.centerCrop()
				.into(imageView);
	}
	public static void loadImage(Activity mActivity,File url, ImageView imageView) {
		Glide.with(mActivity).load(url)
				.placeholder(R.drawable.image_default)//设置图片并且去除换成防止下次加载显示上张图片禁用磁盘缓存
				.error(R.drawable.image_default)
				.centerCrop()
				.into(imageView);
	}

	/**
	 * 开启倒计时
	 *
	 * @param time 倒计时时间  单位秒
	 */
	public static Subscription countTime(final int time,Subscriber<Integer> subscriber) {
		//第一个参数延时时间，第二个参数间隔时间，第三个参数时间单位
		Subscription subscription= Observable.interval(0, 1, TimeUnit.SECONDS) //从0开始，每间隔1秒+1
				.observeOn(AndroidSchedulers.mainThread())
				.map(new Func1<Long, Integer>() {
					@Override
					public Integer call(Long aLong) {
						return time - aLong.intValue();//通过操作符变成从60秒，每秒减去返回的值；
					}
				}).take(time+1)//取前60个数字，1-60；
				.subscribe(subscriber);
		return subscription;
	}
	/**
	 *
	 * 字体颜色 位置 字体大小 动态修改
	 *  开始 到 结束
	 * **/
	public static void TextSpannable(Context context, TextView textView, String text, String color, int start, int end){

		SpannableStringBuilder style = new SpannableStringBuilder(text);
		style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //颜色
		textView.setText(style);
	}
	/**
	 * 是否为字母跟数字
	 * @param str
	 * @return
	 * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$
	 * (?!^\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{6,}（11）
	 */
	public static boolean isDigitalAlphabet(String str){
		boolean isPassword=false;
		String expr="(^[0-9A-Za-z]{6,}$)";
		if (str.matches(expr)) {
			isPassword=true;
		}
		return isPassword;
	}


}
