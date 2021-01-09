package com.alibaba.dubbo.examples.mydubbotest.demo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.examples.mydubbotest.demo.provider.DubboProvider;

public class DubboConsumerImpl implements DubboConsumer {

    @Reference
    private DubboProvider dubboProvider;

    @Override
    public void invokeSayHello(String str) {
        dubboProvider.sayHello(str);
    }

    @Override
    public void invokeSum(int a, int b) {
        int sum = dubboProvider.sum(a, b);
        System.out.println("sum = " + sum);
    }
}
