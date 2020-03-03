package com.chenkh.vchat.server.exception;

/**
 * 用户注册失败时的产生的异常
 * @author Administrator
 *
 */
public class RegiserUserFailedException extends Exception {
	
	public RegiserUserFailedException(String msg){
		super(msg);
	}

}
