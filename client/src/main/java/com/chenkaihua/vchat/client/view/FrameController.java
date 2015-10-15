package com.chenkaihua.vchat.client.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by chenkaihua on 15-10-3.
 */
public class FrameController {




    public JFrame launchDialog() {
        JFrame jFrame = new JFrame("登录/注册");
        jFrame.setBounds(300, 100, 300, 400);








        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
        return jFrame;

    }






}
