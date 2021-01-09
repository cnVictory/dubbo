package com.alibaba.dubbo.examples.mydubbotest.spi;

public class BenzCar implements CarInterface {

    @Override
    public void getColer() {
        System.out.println(" benz car");
    }
}
