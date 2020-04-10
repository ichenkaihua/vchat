package com.chenkh.vchat.base.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.nio.channels.AsynchronousSocketChannel;
import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class ChatMsg  {
	private final int fromId;
	private final int toId;
	private final String content;
	private final Timestamp date;

}
