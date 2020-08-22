package com.hong.msmservice.service;

import com.aliyuncs.exceptions.ClientException;

import java.util.Map;

public interface MsmService {
    boolean send(String PhoneNumbers,
                        Map<String,Object> param) throws ClientException;
}
