package com.chenkh.vchat.server.exception

/**
 * 用户没有找到得异常，在处理用户登录时可能跑出
 * @author Administrator
 */
class UserNotFoundException(msg: String?) : Exception(msg)