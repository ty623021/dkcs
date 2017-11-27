package com.rt.zgloan.weight;

import android.content.Context;


import com.rt.zgloan.R;
import com.rt.zgloan.util.AbViewUtil;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by geek on 2016/10/25.
 */
public class MyShareSdk implements ShareContentCustomizeCallback {

    private Context context;
    private String title;
    private String url;
    private String content;

    public MyShareSdk(Context context) {
        this.context = context;
    }

    public void showShare(String platform, String title, String url, String content) {
        this.title = title;
        this.url = url;
        this.content = content;
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(content);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);
        //app icon服务器地址
//        oks.setImageUrl("https://api.xzgjf.com/" + "icon.png");
        oks.setImagePath(AbViewUtil.getImage(context));
//        oks.setImagePath(AbViewUtil.getImage(this));
        //是否直接分享
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //通过OneKeyShareCallback来修改不同平台分享的内容
        oks.setShareContentCustomizeCallback(this);
        // 启动分享GUI
        oks.show(context);
    }



    @Override
    public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
        if (SinaWeibo.NAME.equals(platform.getName())) {
            paramsToShare.setText(content + "\n" + url);
//            paramsToShare.setImageUrl("");
//            paramsToShare.setImagePath(AbViewUtil.getImage(context));
        }
    }


    //  OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(title);
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(url);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(content);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(url);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment(content);
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(context.getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl(url);
//        //app icon服务器地址
//        oks.setImageUrl("https://api.xzgjf.com/" + "icon.png");
//        oks.setImagePath(AbViewUtil.getImage(this));
//        //是否直接分享
//        if (platform != null) {
//            oks.setPlatform(platform);
//        }
//        //通过OneKeyShareCallback来修改不同平台分享的内容
//        oks.setShareContentCustomizeCallback(this);
//        // 启动分享GUI
//        oks.show(context);
}
