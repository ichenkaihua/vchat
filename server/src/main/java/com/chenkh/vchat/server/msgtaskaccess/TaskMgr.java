package com.chenkh.vchat.server.msgtaskaccess;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


import com.chenkh.vchat.server.msgtaskaccess.task.ParseTaskAccess;
import com.chenkh.vchat.server.msgtaskaccess.task.ReadTask;
import com.chenkh.vchat.server.msgtaskaccess.task.ReadTaskAccess;
import com.chenkh.vchat.server.msgtaskaccess.task.WriteTask;
import com.chenkh.vchat.server.msgtaskaccess.task.WriteTaskAccess;

public class TaskMgr implements TaskAccess, TaskAccessMgr {
	private BlockingQueue<ReadTaskAccess> readTasks = new ArrayBlockingQueue<ReadTaskAccess>(
			50);
	private BlockingQueue<ParseTaskAccess> parseTasks = new ArrayBlockingQueue<ParseTaskAccess>(
			50);
	private BlockingQueue<WriteTaskAccess> writeTasks = new ArrayBlockingQueue<WriteTaskAccess>(
			50);
	private MsgClientAccess msgMgr = null;
	private boolean start = false;
	private Thread parseThread = new Thread(new ParseTaskThread());
	private Thread writeThread = new Thread(new ParseWriteTaskThread());

	public TaskMgr() {
		msgMgr = new MsgMgr(this);
		start = true;
		parseThread.start();
		writeThread.start();
	}

	@Override
	public void addReadTask(ReadTaskAccess task) {
		System.out.println("test");

		try {
			readTasks.put(task);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

	}

	// 负责转换任务的线程
	private class ParseTaskThread implements Runnable {

		@Override
		public void run() {
			while (start) {
				try {
					
					ReadTaskAccess readTask = readTasks.take();
					ParseTaskAccess parseTask = readTask.parseTask();
					parseTasks.put(parseTask);
				} catch (InterruptedException e) {
					System.out.println("等待线程被打断!");
					e.printStackTrace();
				}

			}

		}
	}

	private class ParseWriteTaskThread implements Runnable {
		
		@Override
		public void run() {
			while (start) {
				try {
					ParseTaskAccess parseTask = parseTasks.take();
					msgMgr.parseMsg(parseTask.getMsg(), parseTask.getSocket());

				} catch (InterruptedException e) {

					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public WriteTaskAccess getWriteTask() {
		WriteTaskAccess writeTask = null; 
		try {
			writeTask = writeTasks.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return writeTask;
	}

	@Override
	public void addReadTask(ByteBuffer bf, AsynchronousSocketChannel socket) {
		ReadTaskAccess task = new ReadTask(bf,socket);
		try {
			readTasks.put(task);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		

	}

	@Override
	public void addWriteTask(WriteTaskAccess task) {
		try {
			writeTasks.put(task);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

	}

	@Override
	public void addWriteTask(ByteBuffer bf, AsynchronousSocketChannel socket) {
		WriteTaskAccess task = new WriteTask(bf,socket);
		writeTasks.add(task);
		
	}

	@Override
	public void reMoveClient(AsynchronousSocketChannel socket) {
		msgMgr.reMoveClient(socket);
		
	}

	

}
