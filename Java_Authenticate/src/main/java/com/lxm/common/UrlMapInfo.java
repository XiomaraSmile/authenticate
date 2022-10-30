package com.lxm.common;

import java.lang.reflect.Method;

public class UrlMapInfo {

    /**
     * urlMethod ， reflexted method. used to unify request
     */
    private Method urlMethod;


    /**
     * beanClass, reflexted instance. used to support request
     */
    private Object beanInstance;

    public Method getUrlMethod() {
        return urlMethod;
    }

    public void setUrlMethod(Method urlMethod) {
        this.urlMethod = urlMethod;
    }

    public Object getBeanInstance() {
        return beanInstance;
    }

    public void setBeanInstance(Object beanInstance) {
        this.beanInstance = beanInstance;
    }
}
