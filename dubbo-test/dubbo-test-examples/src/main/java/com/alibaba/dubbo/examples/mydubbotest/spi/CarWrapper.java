package com.alibaba.dubbo.examples.mydubbotest.spi;

public class CarWrapper implements CarInterface{

    private CarInterface carInterface;

    public CarWrapper(CarInterface carInterface) {
        this.carInterface = carInterface;
    }


    @Override
    public void getColer() {
        System.out.println("before");
        carInterface.getColer();
        System.out.println("after");
    }
}
