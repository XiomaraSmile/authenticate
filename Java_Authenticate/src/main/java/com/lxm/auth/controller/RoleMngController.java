package com.lxm.auth.controller;

import com.lxm.auth.bean.FrontBaseResult;
import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.service.RoleService;
import com.lxm.auth.service.impl.RoleServiceImpl;
import com.lxm.common.BeanDefinitionInfo;
import com.lxm.common.MyController;
import com.lxm.common.MyRequestMapping;
import com.lxm.common.SystemInit;
import com.lxm.common.exception.BusinessException;

import javax.servlet.ServletRequest;

@MyController
@MyRequestMapping(urlMap = "/mgnt/role")
public class RoleMngController {

    /**
     * roleServive, complete the service logic related to user processing
     */
    RoleService roleService;

    /**
     * addOneNew to the memory
     * @param request http request
     * @return  the operate result
     * @throws BusinessException when target role already exists
     */
    @MyRequestMapping(urlMap = "/addRole")
    public FrontBaseResult addNewRole(ServletRequest request) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String roleName = request.getParameter("roleName");
        ReturnMsg returnMsg = roleService.addRole(roleName);
        frontBaseResult.setReturnMsg(returnMsg);
        return frontBaseResult;
    }

    /**
     * delete the target role from memory
     * @param request  http request
     * @return  the operate result
     * @throws BusinessException  when target role doesn't exists in memory before
     */
    @MyRequestMapping(urlMap = "/deleteRole")
    public FrontBaseResult deleteRole(ServletRequest request) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String roleName = request.getParameter("roleName");
        ReturnMsg returnMsg =  roleService.deleteRole(roleName);
        frontBaseResult.setReturnMsg(returnMsg);
        return frontBaseResult;
    }

    /**
     * Constructor.
     *  When instantiating, execute the corresponding instantiation for the dependent beans
     * In order to support the global singleton and correct data initialization sequence, simplify the processing here.
     *
     */
    public RoleMngController() {
        if (SystemInit.beanList.get("roleService") == null) {
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setBeanClass(RoleServiceImpl.class);
            beanDefinitionInfo.setBeanInstance(new RoleServiceImpl());
            SystemInit.beanList.put("roleService", beanDefinitionInfo);

        }
        this.roleService = (RoleService) SystemInit.beanList.get("roleService").getBeanInstance();
    }
}
