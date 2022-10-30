package com.lxm.auth.service.impl;

import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.dao.BaseDao;
import com.lxm.auth.service.RoleService;
import com.lxm.common.BeanDefinitionInfo;
import com.lxm.common.SystemInit;
import com.lxm.common.exception.BusinessException;

public class RoleServiceImpl implements RoleService {

    /**
     * baseDao help to store data into memory and get data
     */
    BaseDao baseDao;

    /**
     * addRole
     * @param roleName roleName
     * @return operate result
     * @throws BusinessException when the role already exists
     */
    @Override
    public ReturnMsg addRole(String roleName){
        try {
            baseDao.addRole(roleName);
            return ReturnMsg.SUCCESS;
        } catch (BusinessException e) {
            e.printStackTrace();
            return ReturnMsg.DUPLICATE_DATA;
        }
    }

    /**
     * deleteRole
     * @param roleName  roleName
     * @return operate result
     * @throws BusinessException when the role doesn't exist
     */
    @Override
    public ReturnMsg deleteRole(String roleName) {
        try {
            baseDao.deleteRole(roleName);
            return ReturnMsg.SUCCESS;
        } catch (BusinessException e) {
            e.printStackTrace();
            return ReturnMsg.NO_DATA_ERROR;
        }
    }

    /**
     * Constructor.
     *
     *  When instantiating, execute the corresponding instantiation for the dependent beans
     * In order to support the global singleton and correct data initialization sequence, simplify the processing here.
     */
    public RoleServiceImpl() {
        if (SystemInit.beanList.get("baseDao") == null) {
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setBeanClass(BaseDao.class);
            beanDefinitionInfo.setBeanInstance(new BaseDao());
            SystemInit.beanList.put("baseDao", beanDefinitionInfo);
        }
        baseDao = (BaseDao)SystemInit.beanList.get("baseDao").getBeanInstance();
    }
}
