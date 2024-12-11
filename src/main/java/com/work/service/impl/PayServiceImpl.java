package com.work.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.work.config.AlipayConfig;
import com.work.service.PayService;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class PayServiceImpl implements PayService {
        public String pay(String id, String price, String name) throws UnsupportedEncodingException {
        //获得初始化的AlipayClient,负责调用支付宝接口
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.app_private_key, "json", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数

        AlipayTradePagePayRequest alipayRequest = getAlipayTradePagePayRequest(id, price, name);
        //请求
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(alipayRequest);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        String form = "";
        form = response.getBody();
        System.out.println(form);
        if (response.isSuccess()){
            System.out.println("success");
        }else {
            System.out.println("false");
        }
        return form;
    }


    public String query(String id) {
        //获得初始化的AlipayClient,负责调用支付宝接口
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.app_private_key, "json", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", id);//订单号
        request.setBizContent(bizContent.toString());
        //请求
        AlipayTradeQueryResponse response = null;
        String body = null;
        try {
            response = alipayClient.execute(request);
            body = response.getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        if (response.isSuccess()){
            System.out.println("success");
        }else {
            System.out.println("false");
        }
        return body;
    }

    private static AlipayTradePagePayRequest getAlipayTradePagePayRequest(String id, String price, String name) throws UnsupportedEncodingException {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //异步通知
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        //支付成功跳转
        alipayRequest.setReturnUrl("http://localhost:7071/work/index.html");
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", id);//订单号
        bizContent.put("total_amount", price);//交易金额
        bizContent.put("subject", URLEncoder.encode(name, "UTF-8"));//商品名
        bizContent.put("product_code","FAST_INSTANT_TRADE_PAY");//固定配置
        alipayRequest.setBizContent(bizContent.toString());
        return alipayRequest;
    }
}
