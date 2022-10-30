# 简介
本项目主要完成基本的权限配置和验证功能

# 包结构说明
+--- com.lxm.auth 本权限认证系统业务相关目录

|+---  bean 记录基本bean结构，包结返回结构等

|+---  controller 为此web程序入口。通过自定义MtControllerzh注解完成url匹配

|+---  dao 模拟实际项目场景，此处封装数据存入内存，从内存取出各个方法

|+---  service 具体业务实现。简单起见此处捕获相关异常，并根据相应异常返回不同结果码给Controller层

|+---  util 项目所需工具类

+--- com.lxm.common 项目公共能力相关

# 主要功能

本项目实现一个简单的权限管理系统，可供添加用户，添加权限，为用户赋权，以及token管理等涉及9个接口:

- 用户管理

用户管理涉及用户的添加，删除两个接口:
/authenticate/mgnt/user/addUser -添加用户 
/authenticate/mgnt/user/deleteUser -删除用户

- 角色管理

角色管理涉及角色的添加，删除两个操作。对应接口如下:
/authenticate/mgnt/role/addRole -添加角色
/authenticate/mgnt/role/deleteRole -删除角色

- 权限管理

权限管理涉及5个接口，列表如下:
/authenticate/mgnt/auth/addRoleToUser -给特定用户添加权限
/authenticate/mgnt/auth/getTokenByPassword  -根据用户名，密码，获取对应token
/authenticate/mgnt/auth/invalidateToken  -将目标token设置失效
/authenticate/mgnt/auth/checkRole -确认目标用户是否具有目标权限 
/authenticate/mgnt/auth/getRolesByToken -根据目标token获取对应用户的所有权限

# 实体分析
项目涉及到用户， 角色两个实体。
其中用户信息包括:
userName - 用户名称，唯一值
password -用户密码，内存中采用MD5哈希以保证安全
token - 用户当前最新token。同一时期最多只有一个
ttl - token生命周期
roleNames - 用户具有的权限

角色实体包括:
角色名称。为唯一值


# 框架分析

本项目采用基本Servlet+http请求。并利用反射机制完成url请求与实际方法的映射。为了简化业务逻辑， 本项目通过统一Servlet处理，并在Servlet里的service()方法中实现转发。
基本思路为:
## 自定义注解
为了简化url匹配逻辑，自定义两个注解
MyController 和MyRequestMapping，用于在Servlet初始化时候，通过注解标识识别bean自动创建，并采用map形式维护各例。
其中MyController用于标示url映射涉及到的bean。MyRequestMapping用于完成具体url取值和相关执行方法映射。

## 分层设计
为简化业务开发，采业界流行Controller+service+dao分层设计

## 数据存储设计
系统不做持久化处理，为即起即用产品。所有数据在内存中存储。涉及到的结构主要有三个:
userMap: 为系统主要数据存储内容。哈希方式存储。key为用户名称。为系统主要存储结构
roleSet: 存储当前系统中的角色列表。
tokenUserMap: 存储token与用户名称之间的映射。方便基于token存取数据

# 系统环境和验证结果
本系统验证基本功能正常。环境信息如下:
jdk: 1.8.0_301
IntellIj IDEA: 2021.1.3
Maven: apache-maven-3.8.1
Tomcat: 9.0.65
验证各用例如文件HSBC_HOMEWORK.postman_collection.json所示

# maven依赖
除基本java包外，本项目额外引用两个依赖:
1 fastjson 2.0.13 - 为对象转换向前端提供统一格式提供支持
2 javax.servlet -网络请求处理

# 待改进点
本项目为权限管理粗略版本。后续在bean的生命周期管理，多线程优化方向需要进一步精进

  