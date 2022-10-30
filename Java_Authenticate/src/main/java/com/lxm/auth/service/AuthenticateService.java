package com.lxm.auth.service;

import com.lxm.auth.bean.FrontBaseResult;
import com.lxm.auth.bean.ReturnMsg;

public interface AuthenticateService {

    /**
     * add the target roleName to the target user
     * @param userName name of the target user
     * @param roleName name of the target role
     * @return operate resullt
     */
    ReturnMsg addRoleToUser(String userName, String roleName);

    /**
     * identify the target user, and get new token back
     * @param userName user's name
     * @param password user's password
     * @return new token
     */
    FrontBaseResult identifyAndGetToken(String userName, String password);

    /**
     * invalidate the target token
     * @param token  token
     * @return the operate result
     */
    boolean invalidateToken(String token);

    /**
     * check when the user identified by token has the target role
     * @param token token value
     * @param roleName the roleName
     * @return true if the target role belonged to user, false else
     */
    FrontBaseResult checkRole(String token, String roleName);

    /**
     * identify and get the userName by target token ,and get all roles belonging to te target user
     * @param token token value
     * @return all the roles belonging to te target user
     */
    FrontBaseResult getAllRolesByToken(String token);

}
