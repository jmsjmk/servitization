package com.servitization.webms.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 注册观察者到抽象主题角色
 */
@Component
public class RegisterObserver implements BeanPostProcessor {
    ConcreteSubject subJect = ConcreteSubject.instances();

    /**
     * bean的前置处理器，当bean的类型是Observer的时候，也就是需要把观察者注册到具体的抽象主题里面
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Observer)
            subJect.addObserver(((Observer) bean).getHandlerName(), (Observer) bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

}
