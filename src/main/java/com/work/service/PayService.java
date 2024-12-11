package com.work.service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public interface PayService {
    String pay(String id, String price, String name) throws UnsupportedEncodingException;

    String query(String id);
}
