package com.chenkh.vchat.server.exception;

/**
 * 密码不正确的异常
 * @author Administrator
 *
 */
public class PasswordNotCorrect extends Exception {
	
	public PasswordNotCorrect(String msg){
		super(msg);
	}

}
