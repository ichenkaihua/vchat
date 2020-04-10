package com.chenkh.vchat.base;

import com.chenkh.vchat.base.bean.MsgType;
import com.chenkh.vchat.base.msg.Msg;
import com.chenkh.vchat.base.msg.ServerMsg;

public interface IDecoder {



     boolean support(byte[] bytes);

     /**
      * 将一个byte数组转换成对象
      * @param bytes
      * @return
      */
     Object decode(byte[] bytes);

}
