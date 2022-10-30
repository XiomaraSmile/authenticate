package com.lxm.auth.dao;

import com.lxm.auth.bean.ReturnMsg;
import com.lxm.auth.bean.UserBean;
import com.lxm.common.exception.BusinessException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseDao {

    /**
     * userMap, store the userName and userInfo map in memory
     */
    private static Map<String, UserBean> userMap = new ConcurrentHashMap<>();

    /**
     * roleSet store different roles in memory. each roleName is unique
     */
    private static Set<String> roleSet = Collections.synchronizedSet(new HashSet<String>());

    /**
     * tokenUserMap, stores the map between token and user in memory
     */
    private static Map<String, String> tokenUserMap = new ConcurrentHashMap<>();


    /**
     * add one user to the userMap
     * @param userBean userInfo got from the front, each user   Name is unique
     * @return the operate result
     * @throws BusinessException when the current user  already exists
     */
    public boolean addUser(UserBean userBean) throws BusinessException {
        if (userMap.get(userBean.getUserName()) != null) {
            throw new BusinessException(ReturnMsg.DUPLICATE_DATA);
        }
        userMap.put(userBean.getUserName(), userBean);
        return true;
    }

    /**
     * delete one user by the name
     * @param userName user's name
     * @return the operate result
     * @throws BusinessException when the current user doesn't exist
     */
    public boolean deleteUser(String userName) throws BusinessException {
        if (userMap.get(userName) == null) {
            throw new BusinessException(ReturnMsg.NO_DATA_ERROR);
        }
        userMap.remove(userName);
        return true;
    }

    /**
     * add one role tp the role Info
     * @param roleName name of the role. each role is unique
     * @return the operate result
     * @throws BusinessException  when the current role  already exists
     */
    public boolean addRole(String roleName) throws BusinessException {
        if (roleSet.contains(roleName)) {
            throw new BusinessException(ReturnMsg.DUPLICATE_DATA);
        }
        roleSet.add(roleName);
        return true;
    }

    /**
     * delete one role by the name
     * @param roleName  roleName
     * @return  the operate result
     * @throws BusinessException when the roleName dosen't exist before
     */
    public boolean deleteRole(String roleName) throws BusinessException {
        if (!roleSet.contains(roleName)) {
            throw new BusinessException(ReturnMsg.NO_DATA_ERROR);
        }
        roleSet.remove(roleName);
        return true;
    }

    /**
     * judge whether one role exists in the memory
     * @param roleName  roleName
     * @return  true if target role exists, false otherwise
     */
    public boolean roleExists(String roleName) {
        return roleSet.contains(roleName);
    }

    /**
     * get one user's information by the name
     * @param userName  user's name
     * @return  the information of the target user
     * @throws BusinessException when the userName dosen't exist
     */
    public UserBean getUserBeanByName(String userName) throws BusinessException {
        UserBean userBean = userMap.get(userName);
        if (userBean == null) {
            throw new BusinessException(ReturnMsg.NO_DATA_ERROR);
        }
        return userBean;
    }

    /**
     * invalid one token by removing the token from tokenUserMap
     * @param token the target token
     * @return the operate result
     */
    public boolean invalidateToken(String token) {
        String userName = tokenUserMap.get(token);
        if (userName != null) {
            UserBean userBean = userMap.get(userName);
            userBean.setToken(null);
            userBean.setTtl(-1);
            tokenUserMap.remove(token);
        }
       return true;
    }

    /**
     * validate one token, namely add token to the tokenUserMap
     * @param userName user's name
     * @param token token
     * @return operate result
     */
    public boolean validateToken(String userName, String token) {
        tokenUserMap.put(token, userName);
        return true;
    }

    /**
     * getUserName from tokenUserMap by token
     * @param token  token value
     * @return  userName
     * @throws BusinessException when there is no suitable user found
     */
    public  String getUserNameByToken(String token) throws BusinessException {
        String userName = tokenUserMap.get(token);
        if (userName == null) {
            throw new BusinessException(ReturnMsg.NO_DATA_ERROR);
        }
        return userName;
    }
}
