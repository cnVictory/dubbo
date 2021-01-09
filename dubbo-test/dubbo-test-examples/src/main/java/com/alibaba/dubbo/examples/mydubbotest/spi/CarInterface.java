package com.alibaba.dubbo.examples.mydubbotest.spi;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface CarInterface {

    void getColer();
}
