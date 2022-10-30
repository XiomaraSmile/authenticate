package com.lxm.auth.service.impl;

import com.lxm.auth.bean.FrontBaseResult;
import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.bean.UserBean;
import com.lxm.auth.dao.BaseDao;
import com.lxm.auth.service.AuthenticateService;
import com.lxm.auth.util.MyUtils;
import com.lxm.common.BeanDefinitionInfo;
import com.lxm.common.SystemInit;
import com.lxm.common.exception.BusinessException;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AuthenticateServiceImpl implements AuthenticateService {

    /**
     * baseDao help to store data into memory and get data
     */
    BaseDao baseDao;

    /**
     * add the target roleName to the target user
     * @param userName name of the target user
     * @param roleName name of the target role
     * @return operate resullt
     */
    public ReturnMsg addRoleToUser(String userName, String roleName) {
        UserBean userBean = null;
        try {
            userBean = baseDao.getUserBeanByName(userName);
        } catch (BusinessException e) {
            return ReturnMsg.NO_DATA_ERROR;
        }
        if (userBean.getRoleNames() == null) {
            userBean.setRoleNames(new ArrayList<String>());
        }
        if  (userBean.getRoleNames().contains(roleName)) {
            return ReturnMsg.SUCCESS;
        }
        if (!baseDao.roleExists(roleName)) {
            return ReturnMsg.NO_DATA_ERROR;
        }
        userBean.getRoleNames().add(roleName);
        return ReturnMsg.SUCCESS;
    }

    /**
     * identify the target user, and get new token back
     * @param userName user's name
     * @param password user's password
     * @return new token
     */
    public FrontBaseResult identifyAndGetToken(String userName, String password) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        ReturnMsg returnMsg = identfyUser(userName, password);
        if (!ReturnMsg.SUCCESS.equals(returnMsg)) {
            frontBaseResult.setReturnMsg(returnMsg);
            return frontBaseResult;
        }

        try {
            UserBean userBean  = baseDao.getUserBeanByName(userName);
            String newToken = MyUtils.getUserToken(MyUtils.RANDOM_LENGTH);
            userBean.setToken(newToken);
            Calendar c1 = Calendar.getInstance();
            userBean.setTtl(c1.getTimeInMillis());
            baseDao.validateToken(userName, newToken);
            frontBaseResult.setReturnMsg(ReturnMsg.SUCCESS);
            frontBaseResult.setFrontData(newToken);
            return frontBaseResult;
        } catch (BusinessException e) {
            frontBaseResult.setReturnMsg(ReturnMsg.SYSTEM_ERROR);
            return frontBaseResult;
        }
    }

    ReturnMsg identfyUser(String userName, String password) {
        UserBean userBean;
        try {
            userBean = baseDao.getUserBeanByName(userName);
        } catch (BusinessException e) {
            return ReturnMsg.NO_DATA_ERROR;
        }

        String encryptPassword = null;
        try {
            encryptPassword = MyUtils.getMdsStr(password);
        } catch (NoSuchAlgorithmException e) {
            return ReturnMsg.ENCRYPT_FAIL_ERROR;
        }
        if (MyUtils.isEmptyStr(encryptPassword)) {
            return ReturnMsg.ENCRYPT_FAIL_ERROR;
        }
        if (!encryptPassword.equals(userBean.getPassword())) {
            return ReturnMsg.AUTH_FAIL_ERROR;
        }
        return ReturnMsg.SUCCESS;
    }


    /**
     * invalidate the target token
     * @param token  token
     * @return the operate result
     */
    public boolean invalidateToken(String token) {
        return baseDao.invalidateToken(token);
    }

    /**
     * check when the user identified by token has the target role
     * @param token token value
     * @param roleName the roleName
     * @return true if the target role belonged to user, false else
     */
    public FrontBaseResult checkRole(String token, String roleName) {
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        String userName = null;
        ReturnMsg returnMsg;
        UserBean userBean;
        try {
            userName = baseDao.getUserNameByToken(token);
            userBean = baseDao.getUserBeanByName(userName);

        } catch (BusinessException e) {
            frontBaseResult.setReturnMsg(ReturnMsg.NO_DATA_ERROR);
            return frontBaseResult;
        }
        if (!MyUtils.isTokenValid(userBean.getTtl())) {
            baseDao.invalidateToken(token);
            frontBaseResult.setReturnMsg(ReturnMsg.AUTH_FAIL_ERROR);
            return frontBaseResult;
        }
        List<String> allRoles = userBean.getRoleNames();
        boolean result = allRoles != null && allRoles.contains(roleName);
        frontBaseResult.setFrontData(result);
        frontBaseResult.setReturnMsg(ReturnMsg.SUCCESS);
        return frontBaseResult;
    }

    /**
     * identify and get the userName by target token ,and get all roles belonging to te target user
     * @param token token value
     * @return all the roles belonging to te target user
     */
    public FrontBaseResult getAllRolesByToken(String token) {
        String userName = null;
        UserBean userBean;
        ReturnMsg returnMsg;
        FrontBaseResult frontBaseResult = new FrontBaseResult();
        try {
            userName = baseDao.getUserNameByToken(token);
            userBean = baseDao.getUserBeanByName(userName);
        } catch (BusinessException e) {
            returnMsg = ReturnMsg.NO_DATA_ERROR;
            frontBaseResult.setReturnMsg(returnMsg);
            return frontBaseResult;
        }

        if (!MyUtils.isTokenValid(userBean.getTtl())) {
            baseDao.invalidateToken(token);
            frontBaseResult.setReturnMsg(ReturnMsg.AUTH_FAIL_ERROR);
            return frontBaseResult;
        }

        frontBaseResult.setReturnMsg(ReturnMsg.SUCCESS);
        frontBaseResult.setFrontData(userBean.getRoleNames());
        return frontBaseResult;
    }

    /**
     * Constructor.
     *
     *  When instantiating, execute the corresponding instantiation for the dependent beans
     * In order to support the global singleton and correct data initialization sequence, simplify the processing here.
     */
    public AuthenticateServiceImpl() {
        if (SystemInit.beanList.get("baseDao") == null) {
            BeanDefinitionInfo beanDefinitionInfo = new BeanDefinitionInfo();
            beanDefinitionInfo.setBeanClass(BaseDao.class);
            beanDefinitionInfo.setBeanInstance(new BaseDao());
            SystemInit.beanList.put("baseDao", beanDefinitionInfo);
        }
        baseDao = (BaseDao)SystemInit.beanList.get("baseDao").getBeanInstance();
    }
}
