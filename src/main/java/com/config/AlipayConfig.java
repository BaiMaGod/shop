package com.config;

public class AlipayConfig {
    // 商户appid
    public static String APP_ID = "2016102200737885";
    // 私钥 pkcs8格式的
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCj1ykxjnYgTR7WQCVhhdJpJ9dfwc3U0TQ2Dle9X2C5kMg/ttQUwh80jmNLdKOZH0dtovNlvJUWPwOn2BoMcijP8bAy1o2YZDp/1tQL7Oavt6teOU8mdHasKfROPkR3ASirRe7z0jE07twFNpRNJmVmwWUw8jd+fGYwR3CaOihjCwzAJQLtMssPwEGVfXv+E2JdoRY1CyMcx0Sh1ItL+4Lg809R8ZSwHWKnIdd/8xy+oa0+FgucrdAhjauzmJqnUsrf10/QpzylNB/2E99uxKNjDoIiEUihvmtHN0C61yercsH6CcpgG5DKTWECSxWcFt6ljh5YEzvc84vBfJIPDVLHAgMBAAECggEAKMtkCGDObNCHAxXJuPSXJXO0HrWkyH6mn2cG1bIv5UR1d9fQCrMjDfT+KEWMjo3JPTU7oXNJM9bQ1DPsmHi8TLqDAsWFUBJ0tmwJNEbyUYJL+xQN7ydEfS2dFPZQGcmCFNM+4hrOAb4ProSefG85BFLXHU7SHn5e+1puYRhmAfLSTMK4adxbP0uSGObVlGb8ps70moQEsoWAlQiBwp4UafG/8VYzE295jP+HcYaXF43LNyPCslrgNN/+U7TixW1c2LE/ApNgukyOLP2eX4sXw63OTkTd9MZBPeElzm8tFa/XQSI58S4gPdFoNk+lR9figfkpIUOPrQ9bezT7UNQUwQKBgQDWNOcMAHS4R5LO599DtKXPLNwSn7y7MYevwbTe/n0nGockFJ/cNT6C+oCzunFx7bt6df5T3xKWkArCmkZbn9wMIe3OwgH3ZCUUM8JlNYIbKWwGMuLfd23K957aJQWST1jQDED0uEjhdYUgLie+k2XliYnHc9h/jM3WL/vNjaBdtwKBgQDDzpdWZN7gLWKL3bQU5/7UxTesLUilIDVl9Zl2ub6h7q0GkymtiQtJh+bx/fO9/BcyO8shah7XDOld8wXmYsxl+idB7kJR/35Mlv2eSc26im+HcsFn2mVdokwhL+yTHlaMqXRyU8O6ODw4THf/+PQU6UFRXNRW9SO5WybdtCuzcQKBgBlah6JfB3GsXw8IWN+9qQZ9i8rUh1Usxp+h8NOYEZ4oQKifnFBHKQ/9sHAVeEoA1njnMGQi+iNCB1QDSKKdqEDzXx9izTLH8q+SdMbf73dhjrypFztV+nPUnUO6sA8iPyPY33NXwKCW9Vvauq+O6LejcOBfsNdgBhj88vPgeIO/AoGBALN+dXLIIxtj31uRz9v+U7deVsup81cTsH+X5adVXHB7J/fH/cPLxiIQx997ye2V4vm0oGAwNLc0adUqy3THitHMwCILNJaQXIn/O9zXkRT4hIao5qy6IoP0HE+r6/VtMS/ewxVuvhS+Kk39o19uKunkbXEVZL2WGLp42acQ26KxAoGAO9qZmXUJK424mwY1lWuQmcR6keGqH5D9RziCHbfhwypchl9m5hM8PXje64x5qqScg8OYmZ5/vsc2Jn/9E0QX9C9CbryxgHjKDEr4UI76dHv3YEbgO3JeZkA4u3Io1npL0U804OCOt7EsExuxA6338a6oz4wPEJbjAU4pkZ/VT2s=";

    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    public static String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhgVb20dnifcMFQKJTafq4SskCGKT/RKdyMXFB9EGhnelpXW11ieYO63YmBYca3QXchAHdGclqaAF8umThdeysb+1vOjy7W20tABWyDZyX+J4bcMQjtttAQJlmECX5pXpFcW0vJy+b4kSZEEBkobeXhi878CdyYNgLbJbOid0kMURZz3dhYm377+xBmUcxtG/6YZNSqcShFcapD9ZuhoAQnbjTD6/PSSTHhW2BcTbDGCXPlbwYrTmGZ7qShE+Xc6nSp5xAi5Iq7FeQxkhFIbZ+FVRwMi0t+BPXEflRT8iSoOdLURrH2rmoXuj8sci1y0Nh+P0k4Kzjo1xynZ66AD7ZQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGN_TYPE = "RSA2";


    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
//    public static String NOTIFY_URL = "http://192.168.0.103:8080/shop/json/alipay/callback";
    public static String NOTIFY_URL = "http://120.24.186.190:8080/shop/json/alipay/callback";

    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
//    public static String RETURN_URL = "http://localhost:8080/shop/json/alipay/returnPage";
    public static String RETURN_URL = "http://120.24.186.190:8080/shop/json/alipay/returnPage";


//    public static String RETURN_PAGE = "http://localhost:8080/shop/static/index.html#/order";
    public static String RETURN_PAGE = "http://120.24.186.190:8080/shop/static/index.html#/order";
}
