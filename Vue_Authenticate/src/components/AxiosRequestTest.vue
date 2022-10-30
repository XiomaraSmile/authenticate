<template>
    <div>
        <h1> 一 用戶管理 </h1>
        <p>请输入用户名</p>
        <input type = "text" v-model = "username">

        <p>请输入密码</p>
        <input type = "text" v-model.lazy = "password">

        <p></p>
        <button @click="addUser">
        添加用戶
        </button>

        <button @click="deleteUser">
        刪除用戶
        </button>

        <h1> 二 角色管理 </h1>

        <p>请输入角色名称</p>
        <input type = "text" v-model = "roleName">
        <p></p>

        <button @click="addRole">
        添加角色
        </button>

        <button @click="deleteRole">
        删除角色
        </button>

        <h1> 三 权限管理 </h1>

        <h3> 3.1 给特定用户添加权限</h3>

        <p>权限名称</p>
        <input type = "text" v-model = "addRoleToUser_roleName">

        <p>用户名称</p>
        <input type = "text" v-model = "addRoleToUser_userName">
        <p></p>

        <button @click="addRoleToUser">
            添加目标权限至目标用户
        </button>

        <h3>3.2 获取用户token</h3>
        <p>用户名称</p>
        <input type = "text" v-model = "getTokenByPassword_userName">

        <p>用户密码</p>
        <input type = "text" v-model = "getTokenByPassword_password">
        <p></p>

        <button @click="getTokenByPassword">
            获取用户token
        </button>

        <h3>3.3 根据token获取权限列表</h3>
        <p>token取值</p>
        <input type = "text" v-model = "getRolesByToken_token">
        <p></p>

        <button @click="getRolesByToken">
            根据token 获取角色列表
        </button>

        

    </div>
</template>

<script>
import axios from "axios";
import querystring from "querystring"
export default {
    name: "AxiosRequestTest",
    props: {
        msg: String
    },
    data() {
            return {
                username: "",
                password: "",
                roleName: "",
                addRoleToUser_roleName: "",
                addRoleToUser_userName: "",
                getTokenByPassword_userName: "",
                getTokenByPassword_password: "",
                getRolesByToken_token:""
            }
        }, 
    methods: {
        addUser() {
            axios.post("/api/authenticate/mgnt/user/addUser", querystring.stringify({
                userName: this.username,
                password: this.password
            })).then (res => {
            alert(res.data)
    
        })
        }, deleteUser() {
            axios.post("/api/authenticate/mgnt/user/deleteUser", querystring.stringify({
                userName: this.username
            })).then (res => {
                alert(res.data)
    
        })
        },
        addRole() {
            axios.post("/api/authenticate/mgnt/role/addRole", querystring.stringify({
                roleName: this.roleName
            })).then (res => {
                alert(res.data)
    
        })
        }, deleteRole() {
            axios.post("/api/authenticate/mgnt/role/deleteRole", querystring.stringify({
                roleName: this.roleName
            })).then (res => {
                alert(res.data)
    
        })
    },
    addRoleToUser() {
        axios.post("/api/authenticate/mgnt/auth/addRoleToUser", querystring.stringify({
                userName: this.addRoleToUser_userName,
                roleName: this.addRoleToUser_roleName
            })).then (res => {
                alert(res.data)
    
        })

    },
    getTokenByPassword() {
        axios.post("/api/authenticate/mgnt/auth/getTokenByPassword", querystring.stringify({
                userName: this.getTokenByPassword_userName,
                password: this.getTokenByPassword_password
            })).then (res => {
                alert(res.data)
    
        })
    },
    getRolesByToken() {
        axios.post("/api/authenticate/mgnt/auth/getRolesByToken", querystring.stringify({
                token: this.getRolesByToken_token
            })).then (res => {
                alert(res.data)
    
        })
    },
    }

}
</script>