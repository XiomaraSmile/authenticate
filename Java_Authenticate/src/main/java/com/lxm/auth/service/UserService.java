package com.lxm.auth.service;

import com.lxm.auth.bean.ReturnMsg;
import com.lxm.common.exception.BusinessException;

public interface UserService {

    /**
     * addUser
     * @param userName userName
     * @param password  password
     * @return operate result
     */
    ReturnMsg addUser(String userName, String password);

    /**
     * deleteUser
     * @param userName userName
     * @return operate result
     */
    ReturnMsg deleteUser(String userName) throws  BusinessException;
}
