package com.chenkh.vchat.client;

import com.chenkh.vchat.base.bean.VState;
import com.chenkh.vchat.client.frame.msgMgr.MsgMgrO;
import com.chenkh.vchat.client.listener.TrayObserverable;

import java.awt.*;

public interface ITray  extends TrayObserverable {

     void setPopupMenu(PopupMenu popup);


     void startLogin();

     void loginLose();

    void setOnlineMsgMgr(MsgMgrO msgMgr);

    void loginSucced(VState imonline);
}
