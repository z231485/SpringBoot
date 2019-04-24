package com.py.utils.wxpay;

public class WxPayConfig {

    //AppID
    public final static String APP_ID = "";

    //AppSecret
    public final static String APP_SECRET = "";

    //商户号  微信商户平台(pay.weixin.qq.com)
    public final static String MCH_ID = "";

    //商户密钥  微信商户平台-->账户设置-->API安全-->密钥设置
    public final static String API_KEY = "";

    //加密方式
    public final static String SIGN_TYPE = "MD5";

    //编码方式
    public final static String CHART_ENCODING = "UTF-8";

    //统一下单接口地址
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //异步通知接口地址
    public final static String NOTIFY_URL = "http://15386nv005.iok.la/app/wechatpay/notify";

}
