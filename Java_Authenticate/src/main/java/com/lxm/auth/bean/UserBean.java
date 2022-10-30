package com.lxm.auth.bean;

import java.util.List;

/**
 *
 * UserBean
 * UserEntiy, to identyfy one user
 *
 * @author lixiaomiao
 * @since 2022-09-26
 */
public class UserBean {

    /**
     * userName ,may be duplicate
     */
    private String userName;

    /**
     * password, to verify the user
     */
    private String password;

    /**
     * token help user login in without password
     */
    private String token;

    /**
     * time to live
     */
    private long ttl;

    /**
     * roleId list, to identify one user's roles
     */
    private List<String> roleNames;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
