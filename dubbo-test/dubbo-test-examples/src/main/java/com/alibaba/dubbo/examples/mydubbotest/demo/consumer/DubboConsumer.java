package com.alibaba.dubbo.examples.mydubbotest.demo.consumer;

public interface DubboConsumer {

    void invokeSayHello(String str);

    void invokeSum(int a , int b);
}
