package com.lxm.auth.service.impl;

import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.bean.UserBean;
import com.lxm.auth.dao.BaseDao;
import com.lxm.auth.service.UserService;
import com.lxm.auth.util.MyUtils;
import com.lxm.common.BeanDefinitionInfo;
import com.lxm.common.SystemInit;
import com.lxm.common.exception.BusinessException;

import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {

    /**
     * baseDao help to store data into memory and get data
     */
    BaseDao baseDao;

    /**
     * addUser
     * @param userName userName
     * @param password  password
     * @return operate result
     * @throws BusinessException when the user already exists
     */
    @Override
    public ReturnMsg addUser(String userName, String password) {
        UserBean userBean = new UserBean();
        userBean.setUserName(userName);
        String enPassword = null;
        try {
            enPassword = MyUtils.getMdsStr(password);
        } catch (NoSuchAlgorithmException e) {
            return ReturnMsg.ENCRYPT_FAIL_ERROR;
        }
        userBean.setPassword(enPassword);
        try {
            baseDao.addUser(userBean);
        } catch (BusinessException e) {
            return ReturnMsg.DUPLICATE_DATA;
        }
        return ReturnMsg.SUCCESS;
    }

    /**
     * deleteUser
     * @param userName userName
     * @return operate result
     * @throws BusinessException when the user doesn't exist
     */
    @Override
    public ReturnMsg deleteUser(String userName) {
        try {
            baseDao.deleteUser(userName);
            return ReturnMsg.SUCCESS;
        } catch (BusinessException e) {
            return ReturnMsg.NO_DATA_ERROR;
        }
    }

    /**
     * Constructor.
     *
     *  When instantiating, execute the corresponding instantiation for the dependent beans
     * In order to support the global singleton and correct data initialization sequence, simplify the processing here.
     */
    public UserServiceImpl() {
        if (SystemInit.beanList.get("baseDao") == null) {
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setBeanClass(BaseDao.class);
            beanDefinitionInfo.setBeanInstance(new BaseDao());
            SystemInit.beanList.put("baseDao", beanDefinitionInfo);
        }
        baseDao = (BaseDao)SystemInit.beanList.get("baseDao").getBeanInstance();
    }
}
