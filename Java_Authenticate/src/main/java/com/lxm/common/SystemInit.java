package com.lxm.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SystemInit {
    /**
     * record the url and bean map to support webt request
     */
    public static Map<String, UrlMapInfo> urlMappingMap = new HashMap<>();

    /**
     * record the beanList . all beans are single model
     */
    public static Map<String, BeanDefinitionInfo> beanList = new HashMap<>();

    /**
     * full name of classes to record in beanList.
     * to Simplify processing,  this is innit in static part manually
     */
    private static Set<String> fullClassNames = new HashSet<>();

    // Simplify processing here to save complicated file parsing logic
    static {
        fullClassNames.add("com.lxm.auth.controller.AuthenticateController");
        fullClassNames.add("com.lxm.auth.controller.RoleMngController");
        fullClassNames.add("com.lxm.auth.controller.UserMngController");
        fullClassNames.add("com.lxm.auth.service.impl.AuthenticateServiceImpl");
        fullClassNames.add("com.lxm.auth.service.impl.RoleServiceImpl");
        fullClassNames.add("com.lxm.auth.service.impl.UserServiceImpl");
        fullClassNames.add("com.lxm.auth.controller.AuthenticateController");

    }

    public static void init() {
        try {
            for (String name : fullClassNames) {
                Class currentClass = Class.forName(name);
                if (beanList.get(currentClass.getSimpleName()) != null) {
                    continue;
                }
                if (currentClass.isAnnotationPresent(MyController.class)) {
                    String baseUrl = "/authenticate";
                    if (currentClass.isAnnotationPresent(MyRequestMapping.class)) {
                        baseUrl = baseUrl + ((MyRequestMapping)currentClass.getAnnotation(MyRequestMapping.class)).urlMap();
                    }
                    Object currentInstance = currentClass.newInstance();
                    BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
                    beanDefinitionInfo.setBeanClass(currentClass);
                    beanDefinitionInfo.setBeanInstance(currentInstance);
                    beanList.put(currentClass.getSimpleName(), beanDefinitionInfo);
                    Method[] methodLists = currentClass.getMethods();
                    for (Method currentMethod : methodLists) {
                        if (currentMethod.isAnnotationPresent(MyRequestMapping.class)) {
                            String currentUrl = baseUrl + currentMethod.getAnnotation(MyRequestMapping.class).urlMap();
                            UrlMapInfo urlMapInfo = new UrlMapInfo();
                            urlMapInfo.setUrlMethod(currentMethod);
                            urlMapInfo.setBeanInstance(currentInstance);
                            urlMappingMap.put(currentUrl, urlMapInfo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * destroy clear unusage resources
     */
    public static void destroy() {
        fullClassNames.clear();
        urlMappingMap.clear();
        beanList.clear();
    }
}
