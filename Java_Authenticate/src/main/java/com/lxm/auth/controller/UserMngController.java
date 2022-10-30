package com.lxm.auth.controller;

import com.lxm.auth.bean.FrontBaseResult;
import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.service.UserService;
import com.lxm.auth.service.impl.UserServiceImpl;
import com.lxm.common.BeanDefinitionInfo;
import com.lxm.common.MyController;
import com.lxm.common.MyRequestMapping;
import com.lxm.common.SystemInit;
import com.lxm.common.exception.BusinessException;

import javax.servlet.ServletRequest;

@MyController
@MyRequestMapping(urlMap = "/mgnt/user")
public class UserMngController {

    /**
     * userService, complete the service logic related to role processing
     */
    UserService userService;

    /**
     * addNewUser add one new user to the memory
     * @param request the http request
     * @return  the operate result
     * @throws BusinessException  when the user already exists in memoty before
     */
    @MyRequestMapping(urlMap = "/addUser")
    public FrontBaseResult addNewUser(ServletRequest request) throws BusinessException {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String userName =request.getParameter("userName");
        String password = request.getParameter("password");
        ReturnMsg returnMsg = userService.addUser(userName, password);
        frontBaseResult.setReturnMsg(returnMsg);
        return frontBaseResult;
    }

    /**
     * deleteUser delete target user from memory
     * @param request httprequest
     * @return  operate result
     * @throws BusinessException  when the target dosen't exist in memory
     */
    @MyRequestMapping(urlMap = "/deleteUser")
    public FrontBaseResult deleteUser(ServletRequest request) throws BusinessException {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String userName = request.getParameter("userName");
        ReturnMsg returnMsg = userService.deleteUser(userName);
        frontBaseResult.setReturnMsg(returnMsg);
        return frontBaseResult;
    }

    /**
     * Constructor.
     *
     *  When instantiating, execute the corresponding instantiation for the dependent beans
     * In order to support the global singleton and correct data initialization sequence, simplify the processing here.
     */
    public UserMngController() {
        if (SystemInit.beanList.get("userServiceImpl") == null) {
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setBeanClass(UserServiceImpl.class);
            beanDefinitionInfo.setBeanInstance(new UserServiceImpl());
            SystemInit.beanList.put("userServiceImpl", beanDefinitionInfo);

        }
        this.userService = (UserService)SystemInit.beanList.get("userServiceImpl").getBeanInstance();
    }
}
