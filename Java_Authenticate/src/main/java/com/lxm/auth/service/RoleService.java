package com.lxm.auth.service;

import com.lxm.auth.bean.ReturnMsg;

public interface RoleService {

    /**
     * addRole
     * @param roleName roleName
     * @return operate result
     */
    ReturnMsg addRole(String roleName);

    /**
     * deleteRole
     * @param roleName  roleName
     * @return operate result
     */
    ReturnMsg deleteRole(String roleName);
}
