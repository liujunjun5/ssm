package com.work.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {

    // 应用ID
    public static String app_id = "9021000142653347";

    // 应用私钥
    public static String app_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0zeYBbwJtaVnZFFsPEnazqCVI8v8+nXUyjrvwdiWdAibkxxD9Wn9REOQlEZmtKu+qnbfuwXm7RvloWCFNUg0FmZP07AL3WLetcJmQevpA67P5hk8m09ZKPvxCJRXspWuF/oC2JIWFp+TU0upR9mxIcWr2K4iU/KgGTJS1qBgDynjKKGQaD3p2O5poxHpdw2KGhGXdwak3Y/Xc8pPw+QNWq7IDeLn9IHGH7uHRI3pTAzUrAT9qYhCw/gSIB3yxX0TJ8StQAmiDZj050dVcGBgmmaKq2GTIESnyqgqi4cwZeeDhFwwNKXckKSJysTasne8SQO7snO0yI3hDqj5+TWy1AgMBAAECggEBAIE3nihsI/n6DyKb77BH69neVDdco5smgXoOlLb4WU1rGkzrEHNhWBihz8vGVmRyiTMQ4UGbuNplkR5HzhUqjO/xoNKsUjpgh3VHQhkGKxB24/tzR+JiB4asmTDUvGSSnNP43hhO5bZuFZcc3iaRzTXBCUbCOizwnU1TMfzaE7+wozjWb7kP4p/S8ZTnS+04x9KCVMWEhdr2L9DZZVfM2C4KcKjJrhQpCqWsDrbIXXEzChUCQnuznj98UCIgQg/juZ3X3A1yLUPyih9zxoaOkOBFIoPdjk6t8M0AIa6rzrBIeTgePJVDNNCZrv9jjOG3dWRp9xlhAA0+BF5KMYkpBskCgYEA2GKqVBPA4t/10YKNDvCQvco26WoDe9zRwpcFOjzQqBHrWm/S0zYOBTjTfkwHU/GdezzXji0ZoMZn1flauasYLoo1HzDpWPNBAlCh9cKuDhw0UX1Aoap426uNSFITN6iB5tDArDp4phTp1AkJQ1AyXK6bGS2yO5FDYNItFCt5iN8CgYEA1eenHhL3s3PUmC3o+N0elvzlFdR3goAer17YCIhHRTRRdk8QMVGp6l1j7T0fGaMPHFKZBPkrZJt8F0g+DHMAYtBpwdNEKLRRcmMjGEmC7ATJt31TRtNTjP8UuIcbAQhDKjwb+bo6RC2W/9UsXCQ4dkODZ25D393RI8LejZ20OOsCgYBSkdPNIXyJFk0HFXoeKU4SHge2Ke4KZcjlKBumjW5RtATLDcC1N/tBot2RZBGnxpsBW8YlSrBs2UjmLbChCNjBaZu7uz5P4wL7jyk+Tnt8huav8cun5RUZPe07m5YZtwNawG3FSsqfFXvknU4bkY6mLzqf8QACGvxLBeiNuck2oQKBgQCW3Bs8FaqG8MFWVsLBicnCOTgkTkW/TzbU/nIkimChiUWOyDImZm8M8LLCJeJiRWMp2YM8Q7N78EDzdqr0QsNZ7Z046xReFNaAczTUMzxX0wSlGYl7IR4DCgAGKJs9/iLIjb45QUNIxFKDukff8Fe16OWrM9RA9gNDUnN9s2Tg4wKBgHn0jKy0hN/arMQuuZVmFSzw7qRrS872MmlwZ48jmG2KsHKi7co70bA1N8fcdkrIZvZBubhjSwDuMKjib4CrS6RpaVtIi2il2p2e8/brjnPMMJNkVQ8cjBoG1kQ7MM727DfjFUeG2j/aK8MM12NQXD96tUm5+9q1bb0eh7sQVCM2";
    //支付宝公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArpdCluEH5Y5F5knDXxr0ly+6TI/+vpFselZ8x9TDS/WPlzyRGRdkFIQveBU7VlEj+xtLbMq3UxBP6tdJE+30mG4xv9ufQx8qck+w7qNaYgwpM8LPnlmrXcD3GL7Oz3IAuRewlEQhBKQCb3LJOyvKIN1RV6qSwAZLccgELPvxCwZZGeJkf0lriNqLh5MEqVksehHJcP4hK27UgK/rN9oKBDnkbE2RzUyZE8j4es00+F8H3d9EmxTM1fZQjAKmIKYDdSnSmRakB5XPCSUuha14iV0cKaeYveXregjQrwdwrE9iXSgEUyJkfwpZztPBXO6yBTM9skJCtmw04dm5aWs6DQIDAQAB";

    // 服务器异步通知页面路径，
    public static String notify_url = "http://i6icuu.natappfree.cc/work/index.html";

    // 页面跳转同步通知页面路径， 成功跳转
    public static String return_url = "http://localhost:7071/work/index.html";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

}

