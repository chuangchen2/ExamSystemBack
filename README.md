# ExamSystemBack
Java程序设计期末大作业，一个C/S做题系统后端
## 如何使用
使用maven加载依赖后直接使用，使用8888端口
## API
命令格式错误返回`FE`
### 用户信息
#### 登录
* 登录命令`login 用户名 密码`
* 登录成功返回`L1 UserID`
* 用户名或密码错误返回`L2`
#### 注册
* 注册命令`register 用户名 密码 [用户组ID]`
* 登录成功返回`R1 UserID`
* 用户名重复返回`R2`
* 其他错误返回`R3`
#### 更改密码
* 更改密码`updatepassword 旧密码 新密码`
* 修改成功返回`UP1`
* 旧密码错误返回`UP2`
* 其他错误返回`UP3`
### 课程信息
#### 查询登录用户的课程信息
* `getcourses`
* 返回json格式的list\<map>
```javascript
[
    {
        "coursename":"课程名称"//(String),
        "finished":"是否完成"//(String),
        "score":"分数"//(String),
    }
]
```
## TODO
* 管理员功能
* 发放题目
* 提交分数
* 修改信息
* 考试时间
* 查看错题
