package com.rt.zgloan.util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertUtil {

	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getHeight();
	}

	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}

	
	/*********
	 * 首字母大写
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
       return  name;
      
    }

	/************
	 * json与bean对象互转
	 */
	public static String toJsonString(Object object){
			
			String result = "";
			
			try {
				
				result = JSONObject.toJSONString(object);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return result;
		}
		
	public static <T> T toObject(String json, Class<T> clazz){
		
		T instance_class = null;
		
		try {
			instance_class = JSONObject.parseObject(json, clazz);
		} catch (Exception e) {
			Logger.e("ConvertUtil:toObject:"+e);
		}
		
		return instance_class; 
	}
	
	/***********
	 * 压缩原始图片
	 * @param srcFile
	 * @return
	 */
	public static File getCompressImageFile(File srcFile)
	{
		String fileName=srcFile.getName();
		String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
		File desFile = new File(ViewUtil.getSdCardPath()+"/path/");
		if(!desFile.exists()){
			desFile.mkdirs();
		}
		desFile = new File(desFile.getPath()+"/scale."+prefix);
		try {
			
			//获取压缩过的图片
			Bitmap compressedImage = getCompressedBmp(srcFile.getAbsolutePath());
			saveBitmap(desFile.getPath(),compressedImage);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return desFile;
	}

    /**
     * 压缩图片大小
     * @param image
     * @return
     */
	private static Bitmap compressImage(Bitmap image) {
		//图片允许最大空间   单位：KB
		double maxSize =1000d;
		//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG,100, baos);
		byte[] b = baos.toByteArray();
		//将字节换成KB
		double mid = b.length/1024;
		//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			//获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			image = zoomImage(image, image.getWidth() / Math.sqrt(i), image.getHeight() / Math.sqrt(i));
		}
		return image;
	}
    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
								   double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}



	public static void close(Closeable close){
		try {
			close.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/***********
	 * 获取图片压缩后的bitmap
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getCompressedBmp(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
          
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是1920*1080分辨率，所以高和宽我们设置为
        float hh = 1920f;//这里设置高度为1920f
        float ww = 1080f;//这里设置宽度为1080f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }  
	
	 /***********
	  * 图片保存文件
	  * @param filePath
	  * @param bitmap
	  */
	 public static void saveBitmap(String filePath, Bitmap bitmap) {
	  
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	 }
	
	
	 /**
	  * 读取图片的旋转的角度
	  *
	  * @param path
	  *            图片绝对路径
	  * @return 图片的旋转角度
	  */
	 public static int getBitmapDegree(String path) {
	     int degree = 0;
	     try {
	         // 从指定路径下读取图片，并获取其EXIF信息
	         ExifInterface exifInterface = new ExifInterface(path);
	         // 获取图片的旋转信息
	         int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	                 ExifInterface.ORIENTATION_NORMAL);
	         switch (orientation) {
	         case ExifInterface.ORIENTATION_ROTATE_90:
	             degree = 90;
	             break;
	         case ExifInterface.ORIENTATION_ROTATE_180:
	             degree = 180;
	             break;
	         case ExifInterface.ORIENTATION_ROTATE_270:
	             degree = 270;
	             break;
	         }
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     return degree;
	 }
	 
	 
	 /**
	  * 将图片按照某个角度进行旋转
	  *
	  * @param bm
	  *            需要旋转的图片
	  * @param degree
	  *            旋转角度
	  * @return 旋转后的图片
	  */
	 public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
	     Bitmap returnBm = null;
	   
	     // 根据旋转角度，生成旋转矩阵
	     Matrix matrix = new Matrix();
	     matrix.postRotate(degree);
	     try {
	         // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
	         returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	     } catch (OutOfMemoryError e) {
	     }
	     if (returnBm == null) {
	         returnBm = bm;
	     }
	     if (bm != returnBm) {
	         bm.recycle();
	     }
	     return returnBm;
	 }
	 
	 
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
    
    /** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param context
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity） 
     * @return 
     */  
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;  
        return (int) (pxValue / fontScale + 0.5f);  
    }

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param context
	 * @param spValue
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
     * 定义分割常量 （#在集合中的含义是每个元素的分割，|主要用于map类型的集合用于key与value中的分割） 
     */  
    private static final String SEP1 = "#";
    private static final String SEP2 = "|";
    /** 
     * List转换String 
     *  
     * @param list 
     *            :需要转换的List 
     * @return String转换后的字符串 
     */  
    public static String ListToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {  
            for (int i = 0; i < list.size(); i++) {  
                if (list.get(i) == null || list.get(i) == "") {  
                    continue;  
                }  
                // 如果值是list类型则调用自己  
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i)));
                    sb.append(SEP1);  
                } else if (list.get(i) instanceof Map) {
                    sb.append(MapToString((Map<?, ?>) list.get(i)));
                    sb.append(SEP1);  
                } else {  
                    sb.append(list.get(i));  
                    sb.append(SEP1);  
                }  
            }  
        }  
        return "L" + Base64.getBase64(sb.toString());  
    }
    /** 
     * String转换List 
     *  
     * @param listText 
     *            :需要转换的文本 
     * @return List<?> 
     */  
    public static List<Object> StringToList(String listText) {
        if (TextUtils.isEmpty(listText)) {
            return null;  
        }  
        listText = listText.substring(1);  
  
        listText = Base64.getFromBase64(listText);  
  
        List<Object> list = new ArrayList<Object>();
        String[] text = listText.split(SEP1);
        for (String str : text) {
            if (str.charAt(0) == 'M') {  
                Map<?, ?> map = StringToMap(str);
                list.add(map);  
            } else if (str.charAt(0) == 'L') {  
                List<?> lists = StringToList(str);
                list.add(lists);  
            } else {  
                list.add(str);  
            }  
        }  
        return list;  
    }  
    /** 
     * Map转换String 
     *  
     * @param map 
     *            :需要转换的Map 
     * @return String转换后的字符串 
     */  
    public static String MapToString(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        // 遍历map  
        for (Object obj : map.keySet()) {
            if (obj == null) {  
                continue;  
            }  
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {
                sb.append(key.toString() + SEP1 + ListToString((List<?>) value));
                sb.append(SEP2);  
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString() + SEP1  
                        + MapToString((Map<?, ?>) value));
                sb.append(SEP2);  
            } else {  
                sb.append(key.toString() + SEP1 + value.toString());  
                sb.append(SEP2);  
            }  
        }  
        return "M" + Base64.getBase64(sb.toString());  
    }  
  
    /** 
     * String转换Map 
     *  
     * @param mapText 
     *            :需要转换的字符串
     * @return Map<?,?> 
     */  
    public static Map<String, Object> StringToMap(String mapText) {
  
        if (mapText == null || mapText.equals("")) {  
            return null;  
        }  
        mapText = mapText.substring(1);  
  
        mapText = Base64.getFromBase64(mapText);  
  
        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split("\\" + SEP2); // 转换为数组
        for (String str : text) {
            String[] keyText = str.split(SEP1); // 转换key与value的数组
            if (keyText.length < 1) {  
                continue;  
            }  
            String key = keyText[0]; // key
            String value = keyText[1]; // value
            if (value.charAt(0) == 'M') {  
                Map<?, ?> map1 = StringToMap(value);
                map.put(key, map1);  
            } else if (value.charAt(0) == 'L') {  
                List<?> list = StringToList(value);
                map.put(key, list);  
            } else {  
                map.put(key, value);  
            }  
        }  
        return map;  
    }  
	
    
    
    
    
    
    
    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {
 
        final boolean isKitKat = AppUtil.isVersionKitkat();
 
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
 
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
 
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
 
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
 
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
 
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
 
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
 
        return null;
    }
 
    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     * 
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
									   String[] selectionArgs) {
 
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
 
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
 
    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
 
    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
 
    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    
    
    /************
     * uri转file路径
     * @param uri
     * @return
     */
    public static String getUriString(Activity activity, Uri uri)
	{
		if(uri==null)
			return "";
    	
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			
			return getRealPathFromURI(activity,uri);
		}
		
		String[] proj = { MediaStore.Images.Media.DATA };

		Cursor actualimagecursor = activity.managedQuery(uri,proj,null,null,null);

		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		actualimagecursor.moveToFirst();

		String img_path = actualimagecursor.getString(actual_image_column_index);
		
		return img_path;
	}
	
	/********
	 * 4.2以上用这个
	 * @param contentUri
	 * @return
	 */
	public static String getRealPathFromURI(Activity activity, Uri contentUri) {
	    String res = null;
	    String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
	    if(cursor.moveToFirst()){;
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       res = cursor.getString(column_index);
	    }
	    cursor.close();
	    return res;
	}
}
