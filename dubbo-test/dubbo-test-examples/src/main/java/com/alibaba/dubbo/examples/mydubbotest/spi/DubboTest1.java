package com.alibaba.dubbo.examples.mydubbotest.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import org.junit.jupiter.api.Test;

public class DubboTest1 {


    @Test
    public void testExtensionLoader() {
        ExtensionLoader<Protocol> protocolExtensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol adaptiveExtension = protocolExtensionLoader.getAdaptiveExtension();
        protocolExtensionLoader.getExtension("mockprotocol2");
    }

    @Test
    public void testDubboSpi() {
        ExtensionLoader<CarInterface> extensionLoader = ExtensionLoader.getExtensionLoader(CarInterface.class);
        CarInterface benz = extensionLoader.getExtension("black");
        benz.getColer();
    }
}
