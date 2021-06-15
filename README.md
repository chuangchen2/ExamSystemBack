# ExamSystemBack
Java程序设计期末大作业，一个C/S做题系统后端
## 如何使用
使用maven加载依赖后直接使用，使用8888端口
## API
命令格式错误返回`FE`
### 用户信息
#### 登录
* 命令格式`login 用户名 密码`
* 成功返回`L1 {groupName=, groupID=, userName=, userID=}`
* 用户名或密码错误返回`L2`
#### 注册
* 命令格式`register 用户名 密码 [用户组ID]`
* 成功返回`R1 UserID`
* 用户名重复返回`R2`
* 其他错误返回`R3`
#### 更改密码
* 命令格式`updatepassword 旧密码 新密码`
* 成功返回`UP1`
* 旧密码错误返回`UP2`
* 其他错误返回`UP3`
### 课程信息
#### 查询登录用户的课程信息
* 命令格式`getcourses`
* 成功返回`GC1`+数据，格式格式为
```javascript
[
    {
        "courseid":"课程ID",
        "coursename":"课程名称",//(String),
        "finished":"是否完成",//(String),
        "score":"分数"//(String),
    }
]
```
* 错误返回`GC2`
#### 获取题目
* 命令格式`getquestions 题库id`
* 返回`GQ1`+数据，数据格式为
```javascript
[
    {
        "type":"string",//0选择题， 1选择题
        "items":"List<List<String>>"//
    }
]
```
* 错误返回`GQ2`
#### 提交分数
* 命令格式`postscore 题库id 分数`
* 成功返回IS1
* 失败返回IS2
#### 新建班级
* 命令格式`newgroup groupname`
* 成功返回`NG1`
* 失败返回`NG2`
#### 修改班级名称
* 命令格式`changegroupname oldGroupID newGroupName`
* 成功返回`CG1`
* 失败返回`CG2`
#### 获取班级里的用户
* 命令格式`getusers groupid`
* 成功返回`GU1 + list<user>(josn)`
* 失败返回`GU2`
#### 获取全部班级信息
* 命令格式`getgroups`
* 成功返回`GG1 + list<group>(json)`
* 失败返回`GG2`
