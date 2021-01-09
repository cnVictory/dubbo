package com.alibaba.dubbo.examples.mydubbotest.spi;

public class CarWrapper2 implements CarInterface{

    private CarInterface carInterface;

    public CarWrapper2(CarInterface carInterface) {
        this.carInterface = carInterface;
    }


    @Override
    public void getColer() {
        System.out.println("before2");
        carInterface.getColer();
        System.out.println("after2");
    }
}
