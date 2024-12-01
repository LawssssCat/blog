package org.example.common.utils.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    /**
     * 在所有Bean创建之前对{@link org.springframework.beans.factory.config.BeanDefinition}进行操作
     * 
     * @param beanFactory the bean factory used by the application context
     * @throws BeansException 异常
     * @see <A HREF="https://cloud.tencent.com/developer/article/2331237">Spring BeanFactoryPostProcessor 的作用时机详解</A>
     * @see org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory,
     *      List)
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * 在当前创建完bean后对执行操作
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws BeansException 异常
     * @see org.springframework.context.support.ApplicationContextAwareProcessor#invokeAwareInterfaces
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException 异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T)beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws BeansException 异常
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T)beanFactory.getBean(clz);
    }
}
