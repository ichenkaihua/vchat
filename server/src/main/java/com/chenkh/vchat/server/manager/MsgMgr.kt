package com.chenkh.vchat.server.manager

import com.chenkh.vchat.base.bean.MsgType.Client2Server
import com.chenkh.vchat.base.bean.User
import com.chenkh.vchat.base.bean.VState
import com.chenkh.vchat.base.msg.*
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg
import com.chenkh.vchat.base.msg.client.AddFriendMsg
import com.chenkh.vchat.base.msg.client.LoginMsg
import com.chenkh.vchat.base.msg.client.QueryMsg
import com.chenkh.vchat.base.msg.client.RegisterMsg
import com.chenkh.vchat.base.msg.server.*
import com.chenkh.vchat.server.dao.GroupDAO
import com.chenkh.vchat.server.dao.MsgDAO
import com.chenkh.vchat.server.dao.Mysql_GroupDAO
import com.chenkh.vchat.server.dao.Mysql_MsgDAO
import com.chenkh.vchat.server.exception.PasswordNotCorrect
import com.chenkh.vchat.server.exception.RegiserUserFailedException
import com.chenkh.vchat.server.exception.UserNotFoundException
import com.chenkh.vchat.server.net.IChannel
import com.chenkh.vchat.server.net.IChannelConnectListener
import com.chenkh.vchat.server.net.IMsgListener
import com.chenkh.vchat.server.net.IServer
import java.nio.channels.AsynchronousSocketChannel
import java.util.*


class MsgMgr(server: IServer) : IMsgListener, IChannelConnectListener {
    private val userMgr: UserAccess
    private val clientMgr: IClientManager

    //private TaskAccessMgr taskMgr;
    private val msgDao: MsgDAO = Mysql_MsgDAO()
    private val groupDao: GroupDAO = Mysql_GroupDAO()

    //@Override
    fun parseLoginMsg(msg: LoginMsg, channel: IChannel?) {
        val id = msg.id
        val password = msg.password

        //ServerMsg<LoginResultMsg> serverMsg=null;
        var loginResultMsg: LoginResultMsg? = null
        var offlineMsgs: List<ContenMsg?>? = null
        var offlineMsg: OfflineMsg? = null
        try {
            var u: User? = null
            u = userMgr.getUser(id, password)

            //serverMsg = new LoginResultMsg(LoginResult_Type.SUCCESS, u);
            val friendIds: MutableSet<Int> = HashSet()
            for (group in u!!.groups) {
                for (friend in group.friends) {
                    if (clientMgr.isContain(friend.id)) {
                        val mstate = clientMgr.getState(friend.id)
                        friend.state = mstate
                    }
                    friendIds.add(friend.id)
                }
            }
            val state = msg.state
            println("state:$state")
            clientMgr.addClient(id, channel, state, friendIds)
            this.parseMsg(UserStateChangeMsg(id, state), channel)
            offlineMsgs = msgDao.getOfflineMsg(u.id)
            if (offlineMsgs != null && offlineMsgs.size > 0) {
                offlineMsg = OfflineMsg(u.id, offlineMsgs)
                //offlineMsg = Msg.buildServerMsg(MsgType.Server2Client.OFFLINE_MSG,);
            }
            loginResultMsg = LoginResultMsg(null, u, true)
        } catch (e: UserNotFoundException) {
            loginResultMsg = LoginResultMsg("用户未找到", null, false)
            //serverMsg = Msg.buildServerMsg(MsgType.Server2Client.LOGIN,false,"用户未找到",null);
        } catch (e: PasswordNotCorrect) {
            loginResultMsg = LoginResultMsg("密码不正确", null, false)
            //serverMsg = Msg.buildServerMsg(MsgType.Server2Client.LOGIN,false,"密码不正确",null);
        } finally {
            channel!!.sendLoginResultMsg(loginResultMsg)
            if (offlineMsg != null) {
                channel.sendOfflineMsg(offlineMsg)
            }
        }
    }

    fun parseMsg(msg: ChatMsg, channel: IChannel?) {
        val fromId = msg.fromId
        val toId = msg.toId
        // 如果存在，则发送消息，否则，转存为离线消息
        if (clientMgr.isContain(toId)) {
            val client = clientMgr.getClient(toId)
            if (clientMgr.isFriend(toId, fromId)) {
                channel!!.sendChatMsg(msg)
            } else {
                val stranger = userMgr.getStrangerById(fromId)
                val conten = msg.content
                val smsg = StrangerMsg(stranger, conten, toId)
                channel!!.sendStrangerMsg(smsg)
            }
        } else {
            msgDao.addOfflineMsg(fromId, toId, msg.content, msg.date)
        }
    }

    /*	private ByteBuffer object2ByteArrayByObjSeria(Object msg) {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ByteBuffer bf = null;

		try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(msg);
			byte[] bytes = bos.toByteArray();
			bf = ByteBuffer.wrap(bytes);

		} catch (IOException e) {
			System.out.println("转换失败！");
			e.printStackTrace();
		}

		return bf;
	}*/
    fun parseMsg(msg: VerifyMsg?, socket: AsynchronousSocketChannel?) {
        // TODO Auto-generated method stub
        println("test")
    }

    fun parseMsg(msg: UserStateChangeMsg,
                 socket: IChannel?) {
        val id = msg.fromId
        if (clientMgr.isContain(id)) {
            val c = clientMgr.getClient(id)
            c!!.state = msg.state
        }
        val friends = clientMgr.getFriends(id)
        for (friendid in friends!!) {
            if (clientMgr.isContain(friendid!!)) {
                val c = clientMgr.getClient(friendid)
                c!!.socket!!.sendFriendStateChanged(msg)
            }
        }
    }

    /*	@Override
	public void reMoveClient(IChannel socket) {
		int id = clientMgr.removeCient(socket);
		if (id != -1) {
			this.parseMsg(new UserStateChangeMsg(id, VState.offline), socket);
		}

	}*/
    fun parseMsg(msg: RegisterMsg, channel: IChannel?) {
        val u = msg.user
        var id = -1
        try {
            id = userMgr.addUser(u)
            if (id != -1) {
                val resultMsg = RegisterSucessMsg(id)
                channel!!.sendRegisterSuccessMsg(resultMsg)
            }
        } catch (e: RegiserUserFailedException) {
        }
    }

    fun parseMsg(msg: QueryMsg, channel: IChannel?) {
        val toId = msg.queryId
        val keyword = msg.keyword
        val strangers = userMgr.queryUser(toId, keyword)
        val smsg = QueryResultMsg(toId, strangers)
        channel!!.sendQueryResultMsg(smsg)
    }

    fun parseMsg(msg: AddFriendMsg, channel: IChannel?) {
        val fromId = msg.myId
        val friendId = msg.friendId
        val noteName = msg.noteName
        val groupId = msg.groupId
        val friend = userMgr.addFriend(fromId, friendId, groupId, noteName)
        if (friend != null) {
            if (clientMgr.isContain(friendId)) {
                val friendClient = clientMgr.getClient(friendId)
                friend.state = friendClient!!.state
            }
            if (clientMgr.isContain(fromId)) {
                clientMgr.addFriend(fromId, friendId)
                val suceMsg = AddFriendSucess(friend, groupId)
                channel!!.sendAddFriendResultMsg(suceMsg)
            }
        }
    }

    fun parseMsg(msg: DeleteFriendMsg, channel: IChannel?) {
        val myId = msg.myId
        val friendId = msg.friendId
        val groupId = msg.groupId
        val result = userMgr.deleteFriend(myId, friendId, groupId)
        if (result) {
            channel!!.sendDeleteFriendResultMsg(msg)
        }
    }

    override fun onReceivedMsg(channel: IChannel, serverMsg: ClientMsg<*>) {
        val body = serverMsg.body
        val msgType = serverMsg.msgType
        when (msgType) {
            Client2Server.LOGIN -> parseLoginMsg(body as LoginMsg, channel)
            Client2Server.QUERY -> parseMsg(body as QueryMsg, channel)
            Client2Server.REGISTER -> parseMsg(body as RegisterMsg, channel)
            Client2Server.ADD_FRIEND -> parseMsg(body as AddFriendMsg, channel)
            Client2Server.DELETE_FRIEND -> parseMsg(body as DeleteFriendMsg, channel)
            Client2Server.USER_STATE_CHANGED -> {
                parseMsg(body as UserStateChangeMsg, channel)
                parseMsg(body as ChatMsg, channel)
            }
            Client2Server.CHAT -> parseMsg(body as ChatMsg, channel)
        }
    }

    override fun channelConnected(channel: IChannel) {}
    override fun channelDisconnect(channel: IChannel, exc: Throwable?) {
        val id = clientMgr.removeCient(channel)
        if (id != -1) {
            this.parseMsg(UserStateChangeMsg(id, VState.offline), channel)
        }
    }

    init {
        userMgr = UserMgr()
        clientMgr = ClientMgr()
     /*   server.addMsgListener(IMsgListener { channel: IChannel?, serverMsg: ClientMsg<*> -> this.onReceivedMsg(channel, serverMsg) })
        server.addMsgListener(object : IMsgListener {
            override fun onReceivedMsg(channel: IChannel, serverMsg: ClientMsg<*>) {

            }
        });
        */
        server.addMsgListener(object : IMsgListener{
            override fun onReceivedMsg(channel: IChannel, serverMsg: ClientMsg<*>) {
                this.onReceivedMsg(channel, serverMsg)
            }
        })
        server.addConnectListener(this)
    }


}