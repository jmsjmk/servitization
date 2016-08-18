package com.servitization.commons.socket.spring.remote;

import com.servitization.commons.socket.client.RemoteClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

public class ClassPathRemoteScanner extends ClassPathBeanDefinitionScanner {


    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private RemoteClient remoteClient;
    private String remoteClientName;

    public ClassPathRemoteScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }


    public void setRemoteClientName(String remoteClientName) {
        this.remoteClientName = remoteClientName;
    }


    public void setRemoteClient(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    /**
     * Configures parent scanner to search for the right interfaces. It can
     * search for all interfaces or just for those that extends a
     * markerInterface or/and those annotated with the annotationClass
     */
    public void registerFilters() {
        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker
        // interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter(new TypeFilter() {
                public boolean match(MetadataReader metadataReader,
                                     MetadataReaderFactory metadataReaderFactory)
                        throws IOException {
                    return true;
                }
            });
        }

        // exclude package-info.java
        addExcludeFilter(new TypeFilter() {
            public boolean match(MetadataReader metadataReader,
                                 MetadataReaderFactory metadataReaderFactory)
                    throws IOException {
                String className = metadataReader.getClassMetadata()
                        .getClassName();
                return className.endsWith("package-info");
            }
        });
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * RemoteFactroyBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No emcf remote was found in '"
                    + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            for (BeanDefinitionHolder holder : beanDefinitions) {
                GenericBeanDefinition definition = (GenericBeanDefinition) holder
                        .getBeanDefinition();

                if (logger.isDebugEnabled()) {
                    logger.debug("Creating RemoteFactroyBean with name '"
                            + holder.getBeanName() + "' and '"
                            + definition.getBeanClassName()
                            + "' mapperInterface");
                }

                // the mapper interface is the original class of the bean
                // but, the actual class of the bean is RemoteFactroyBean
                definition.getPropertyValues().add("remoteInterface",
                        definition.getBeanClassName());
                definition.setBeanClass(RemoteFactroyBean.class);

                boolean explicitFactoryUsed = false;
                if (StringUtils.hasText(this.remoteClientName)) {
                    definition.getPropertyValues().add(
                            "remoteClient",
                            new RuntimeBeanReference(
                                    this.remoteClientName));
                    explicitFactoryUsed = true;
                } else if (this.remoteClient != null) {
                    definition.getPropertyValues().add("remoteClient",
                            this.remoteClient);
                    explicitFactoryUsed = true;
                }


                if (!explicitFactoryUsed) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Enabling autowire by type for RemoteFactroyBean with name '"
                                + holder.getBeanName() + "'.");
                    }
                    definition
                            .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                }
            }
        }

        return beanDefinitions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isCandidateComponent(
            AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() && beanDefinition
                .getMetadata().isIndependent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean checkCandidate(String beanName,
                                     BeanDefinition beanDefinition) throws IllegalStateException {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            logger.warn("Skipping RemoteFactroyBean with name '" + beanName
                    + "' and '" + beanDefinition.getBeanClassName()
                    + "' mapperInterface"
                    + ". Bean already defined with the same name!");
            return false;
        }
    }
}
