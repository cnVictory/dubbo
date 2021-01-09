package com.alibaba.dubbo.examples.mydubbotest.demo.provider;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class DubboProviderImpl implements DubboProvider {
    @Override
    public void sayHello(String str) {
        System.out.println("just say hello");
    }

    @Override
    public int sum(int a, int b) {
        System.out.println("a = " + a + " , b = " + b);
        return a + b;
    }

}
