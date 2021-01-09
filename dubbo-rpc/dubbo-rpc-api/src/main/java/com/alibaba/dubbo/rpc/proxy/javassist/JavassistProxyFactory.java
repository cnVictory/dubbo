/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.proxy.javassist;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.bytecode.Proxy;
import com.alibaba.dubbo.common.bytecode.Wrapper;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.proxy.AbstractProxyFactory;
import com.alibaba.dubbo.rpc.proxy.AbstractProxyInvoker;
import com.alibaba.dubbo.rpc.proxy.InvokerInvocationHandler;

/**
 * JavaassistRpcProxyFactory
 */
public class JavassistProxyFactory extends AbstractProxyFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {

        // 注意这里的InvokerInvocationHandler， 服务调用的逻辑，就是从这里开始的，调用这个InvokerInvocationHandler的invoke方法
        // 会调用他所引用的invoker的invoke方法
        return (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));
    }

    /*
        Invoker 是实体域，它是 Dubbo 的核心模型，其它模型都向它靠扰，或转换成它，它代表一个可执行体，
        可向它发起 invoke 调用，它有可能是一个本地的实现，也可能是一个远程的实现，也可能一个集群实现。
     */
    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {

        // Wrapper 类似spring的 BeanWrapper，就是包装了一个接口或一个类，可以通过wrapper对实例对象进行赋值和取值，以及制定方法的调用
        /*
            Wrapper.getWrapper 的目的是为这个proxy对象（proxy是类type的一个实现类对象） 生成一个代理类，用来包装proxy
            生成的wrapper的invokeMethod的方法是下面的样子， 当执行Invoker的doInvoke方法时，执行wrapper的invokeMethod方法

            public Object invokeMethod(Object proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments)
                                                            throws NoSuchMethodException, InvocationTargetException {

                DemoServiceImpl demoServiceImpl;
                try{
                    demoServiceImpl = (DemoServiceImpl) proxy;
                } catch (Throwable throwable) {
                    throw new IllegalArgumentException(throwable);
                }

                try{
                    if("sayHello".equals(methodName) && parameterTypes.length == 1) {
                        // 调用DemoServiceImpl实例对象的sayHello方法，并将结果返回
                        return demoServiceImpl.sayHello((String)arguments[0]);
                    }
                } catch (Throwable throwable) {
                    throw new InvocationTargetException(throwable);
                }

                throw new NoSuchMethodException(new StringBuffer().append("Not found method \""))
                                                                  .append(methodName)
                                                                  .append("\" in class com.xxx.DemoServiceImpl.")
                                                                  .toString();

         */
        // TODO Wrapper cannot handle this scenario correctly: the classname contains '$'
        final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);

        // 生成一个AbstractProxyInvoker
        // Invoker 是一个可执行的对象，能够根据方法的名称，参数得到相应的执行结果 有3中类型的Invoker：
        //      1.本地执行的Invoker  2.远程通信的Invoker  3.多个远程通信的Invoker的聚合
        // Invoker的invoke方法很重要 Result invoke(Invocation invocation) throws RpcException;
        // invocation 是包含了需要执行的方法和参数等重要信息，目前它只有2个实现类  RpcInvocation和MockInvocation
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName,
                                      Class<?>[] parameterTypes,
                                      Object[] arguments) throws Throwable {
                return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
            }
        };
    }

}
