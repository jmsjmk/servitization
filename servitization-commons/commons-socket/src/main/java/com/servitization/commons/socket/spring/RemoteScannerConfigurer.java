package com.servitization.commons.socket.spring;

import com.servitization.commons.socket.client.RemoteClient;
import com.servitization.commons.socket.spring.remote.ClassPathRemoteScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

public class RemoteScannerConfigurer implements
        BeanDefinitionRegistryPostProcessor, InitializingBean,
        ApplicationContextAware, BeanNameAware {
    private String basePackage;
    private String beanName;
    private ApplicationContext applicationContext;
    private Class<? extends Annotation> annotationClass;
    private boolean processPropertyPlaceHolders;

    private RemoteClient remoteClient;
    private String remoteClientName;
    private Class<?> markerInterface;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
            throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(
            BeanDefinitionRegistry registry) throws BeansException {
        if (this.processPropertyPlaceHolders) {
            processPropertyPlaceHolders();
        }
        ClassPathRemoteScanner scanner = new ClassPathRemoteScanner(registry);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setMarkerInterface(this.markerInterface);
        scanner.setRemoteClient(this.remoteClient);
        scanner.setRemoteClientName(this.remoteClientName);
        scanner.setResourceLoader(this.applicationContext);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    private void processPropertyPlaceHolders() {
        Map<String, PropertyResourceConfigurer> prcs = applicationContext
                .getBeansOfType(PropertyResourceConfigurer.class);

        if (!prcs.isEmpty()
                && applicationContext instanceof GenericApplicationContext) {
            BeanDefinition mapperScannerBean = ((GenericApplicationContext) applicationContext)
                    .getBeanFactory().getBeanDefinition(beanName);

            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
            factory.registerBeanDefinition(beanName, mapperScannerBean);

            for (PropertyResourceConfigurer prc : prcs.values()) {
                prc.postProcessBeanFactory(factory);
            }

            PropertyValues values = mapperScannerBean.getPropertyValues();

            this.basePackage = updatePropertyValue("basePackage", values);
        }
    }

    private String updatePropertyValue(String propertyName,
                                       PropertyValues values) {
        PropertyValue property = values.getPropertyValue(propertyName);

        if (property == null) {
            return null;
        }

        Object value = property.getValue();

        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return value.toString();
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            return null;
        }
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");

    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public boolean isProcessPropertyPlaceHolders() {
        return processPropertyPlaceHolders;
    }

    public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
    }

    public String getBeanName() {
        return beanName;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setRemoteClient(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public void setRemoteClientName(String remoteClientName) {
        this.remoteClientName = remoteClientName;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }


}
