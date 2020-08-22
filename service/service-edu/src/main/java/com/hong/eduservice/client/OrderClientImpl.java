package com.hong.eduservice.client;

import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean isBuyCourse(String memberId, String id) {
        return false;
    }
}
