package com.warm.entity;

public interface ResultCode {
    int OK = 0;//成功
    int ERROR = 1;//失败
    int LOGIN_ERROR = 20002;//用户名或密码错误
    int ACCESS_ERROR = 20003;//权限不足
    int REMOTE_ERROR = 20004;//远程调用失败
    int REPEAT_ERROR = 20005;//重复操作
}
