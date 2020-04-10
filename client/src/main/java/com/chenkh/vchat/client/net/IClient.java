package com.chenkh.vchat.client.net;

import com.chenkh.vchat.base.IDecoder;
import com.chenkh.vchat.base.IEncoder;
import com.chenkh.vchat.base.bean.MsgType;
import com.chenkh.vchat.base.bean.VState;
import com.chenkh.vchat.base.msg.ChatMsg;
import com.chenkh.vchat.base.msg.both.DeleteFriendMsg;
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg;
import com.chenkh.vchat.base.msg.client.AddFriendMsg;
import com.chenkh.vchat.base.msg.client.LoginMsg;
import com.chenkh.vchat.base.msg.client.QueryMsg;
import com.chenkh.vchat.base.msg.client.RegisterMsg;
import com.chenkh.vchat.client.net.aio.AioClient;
import com.chenkh.vchat.client.net.bio.BioClient;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Supplier;


public interface IClient  extends IMsgObservable,IConnectObservable{


    static IClient createAioClient(Supplier<IEncoder> encoderSupplier, Supplier<IDecoder> decoderSupplier){
        return new AioClient(encoderSupplier.get(),decoderSupplier.get());
    }

    static IClient createBioClient(Supplier<IEncoder> encoderSupplier, Supplier<IDecoder> decoderSupplier){
        return new BioClient(encoderSupplier.get(),decoderSupplier.get());
    }

    <T> Future<Integer>  sendMsg(MsgType.Client2Server msgType, T body);
     default Future<Integer> sendLoginMsg(int id, String password, VState state){
         return sendMsg(MsgType.Client2Server.LOGIN,new LoginMsg(id,password,state));
     }


     default Future<Integer> sendRegisterMsg(RegisterMsg registerMsg){
         return sendMsg(MsgType.Client2Server.REGISTER,registerMsg);
     }


     default Future<Integer> sendUserStateChangedMsg(UserStateChangeMsg userStateChangeMsg){
         return sendMsg(MsgType.Client2Server.USER_STATE_CHANGED,userStateChangeMsg);
     }

     default Future<Integer> sendDeleteFriendMsg(DeleteFriendMsg deleteFriendMsg){
         return sendMsg(MsgType.Client2Server.DELETE_FRIEND,deleteFriendMsg);
     }


     default Future<Integer> sendQueryMsg(QueryMsg queryMsg){
         return sendMsg(MsgType.Client2Server.QUERY,queryMsg);
     }


    default Future<Integer> sendAddFriendMsg(AddFriendMsg addFriendMsg){
        return sendMsg(MsgType.Client2Server.ADD_FRIEND,addFriendMsg);
    }

    default Future<Integer> sendChatMsg(ChatMsg chatMsg){
        return sendMsg(MsgType.Client2Server.CHAT,chatMsg);
    }



    boolean isOpen();

     void connectServer(String ip, int port) throws IOException;


     void close() throws IOException;





}
