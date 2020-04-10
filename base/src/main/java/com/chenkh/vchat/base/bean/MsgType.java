package com.chenkh.vchat.base.bean;

public class MsgType {

    public enum Server2Client{
        /**
         * 登录
         */
        LOGIN,

        /**
         * 离线消息
         */
        OFFLINE_MSG,

        /**
         * 聊天信息
         */
        CHAT,

        /**
         * 陌生好友
         */
        STRAGER_MSG,

        /**
         * 好友状态发生改变
         */
        FRIEND_STATE_CHANGED,

        /**
         * 注册结果
         */
        REGISTER_RESULT,

        /**
         * 查询结果
         */
        QUERY_RESULT,

        /**
         * 添加好友结果
         */
        ADD_FRIEND_RESULT,

        /**
         * 删除好友结果
         */
        DELETE_FRIEND_RESULT

    }


    public enum Client2Server{
        /**
         * 登录
         */
        LOGIN,  /**
         * 注册
         */
        REGISTER,
        /**
         * 用户状态改变
         */
        USER_STATE_CHANGED,
        /**
         * 删除好友
         */
        DELETE_FRIEND,
        /**
         * 查找好友
         */
        QUERY,
        /**
         * 添加好友
         */
        ADD_FRIEND,
        /**
         * 聊天消息
         */
        CHAT,
    }

}


