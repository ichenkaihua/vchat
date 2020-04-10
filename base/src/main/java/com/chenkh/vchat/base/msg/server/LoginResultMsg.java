package com.chenkh.vchat.base.msg.server;

import com.chenkh.vchat.base.msg.server.enu.LoginResult_Type;
import com.chenkh.vchat.base.bean.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginResultMsg  {
	private final String reason;
	private final User user;
	private final boolean success;
}
