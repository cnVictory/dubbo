package com.alibaba.dubbo.examples.mydubbotest.spi;

public class BlackCar implements CarInterface{
    @Override
    public void getColer() {
        System.out.println("black car");
    }
}
