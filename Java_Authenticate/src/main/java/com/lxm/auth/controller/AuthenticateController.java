package com.lxm.auth.controller;

import com.lxm.auth.bean.FrontBaseResult;
import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.service.AuthenticateService;
import com.lxm.auth.service.impl.AuthenticateServiceImpl;
import com.lxm.auth.util.MyUtils;
import com.lxm.common.BeanDefinitionInfo;
import com.lxm.common.MyController;
import com.lxm.common.MyRequestMapping;
import com.lxm.common.SystemInit;

import javax.servlet.ServletRequest;

@MyController
@MyRequestMapping(urlMap = "/mgnt/auth")
public class AuthenticateController {

    /**
     * authenticateService, complete the service logic related to authenticate processing
     */
    AuthenticateService authenticateService;

    /**
     * add the target roleName to the target user
     * @param request  http request
     * @return operateresult
     */
    @MyRequestMapping(urlMap = "/addRoleToUser")
    public FrontBaseResult addRoleToUser(ServletRequest request) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String roleName = request.getParameter("roleName");
        String userName = request.getParameter("userName");
        ReturnMsg returnMsg = authenticateService.addRoleToUser(userName, roleName);
        frontBaseResult.setReturnMsg(returnMsg);
        return frontBaseResult;
    }

    /**
     * getTokenByPassword
     * @param request http request
     * @return operateresult
     */
    @MyRequestMapping(urlMap = "/getTokenByPassword")
    public FrontBaseResult getTokenByPassword(ServletRequest request){
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (MyUtils.isEmptyStr(userName) || MyUtils.isEmptyStr(password)) {
            FrontBaseResult frontBaseResult = new FrontBaseResult();
            frontBaseResult.setReturnMsg(ReturnMsg.PARAMETER_ERROR);
            return frontBaseResult;
        }
        return authenticateService.identifyAndGetToken(userName, password);
    }

    /**
     * invalidateToken
     * @param request  http request
     * @return operateresult
     */
    @MyRequestMapping(urlMap = "/invalidateToken")
    public FrontBaseResult invalidateToken(ServletRequest request) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String token = request.getParameter("token");
        authenticateService.invalidateToken(token);
        frontBaseResult.setReturnMsg(ReturnMsg.SUCCESS);
        return frontBaseResult;
    }

    /**
     * check whether current roleName belongs to target user
     * @param request http request
     * @return frontData is true is current roleName belongs to target user
     */
    @MyRequestMapping(urlMap = "/checkRole")
    public FrontBaseResult checkRole (ServletRequest request){
        String token = request.getParameter("token");
        String role = request.getParameter("roleName");
        return authenticateService.checkRole(token, role);
    }

    /**
     * getRolesLiist by target Token
     * @param request http request
     * @return roleList of current user related to the token
     */
    @MyRequestMapping(urlMap = "/getRolesByToken")
    public FrontBaseResult getAllRolesByToken (ServletRequest request) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String token = request.getParameter("token");
        return authenticateService.getAllRolesByToken(token);

    }

    /**
     * Constructor.
     *
     *  When instantiating, execute the corresponding instantiation for the dependent beans
     * In order to support the global singleton and correct data initialization sequence, simplify the processing here.
     */
    public AuthenticateController() {
        if (SystemInit.beanList.get("authenticateService") == null) {
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setBeanClass(AuthenticateServiceImpl.class);
            beanDefinitionInfo.setBeanInstance(new AuthenticateServiceImpl());
            SystemInit.beanList.put("authenticateService", beanDefinitionInfo);

        }
        this.authenticateService = (AuthenticateService)SystemInit.beanList.get("authenticateService").getBeanInstance();
    }
}
