package com.lxm.common;

public class BeanDefinitionInfo {

    /**
     * beanClass
     */
    private Class beanClass;

    /**
     * bean Object. all beans are in single-mode
     */
    private Object beanInstance;

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Object getBeanInstance() {
        return beanInstance;
    }

    public void setBeanInstance(Object beanInstance) {
        this.beanInstance = beanInstance;
    }
}
