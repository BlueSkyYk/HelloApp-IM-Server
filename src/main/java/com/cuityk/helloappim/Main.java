package com.cuityk.helloappim;

import com.cuityk.helloappim.launcher.Constants;
import com.cuityk.helloappim.launcher.IMServerLauncherImpl;
import com.cuityk.helloappim.launcher.RpcServerLauncher;
import com.cuityk.helloappim.rpc.RpcClientImpl;
import com.cuityk.helloappim.rpc.RpcServerImpl;
import com.google.gson.Gson;
import net.openmob.mobileimsdk.server.protocal.Protocal;
import net.openmob.mobileimsdk.server.utils.LocalSendHelper;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    /**
     * APP启动
     *
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        String appKey = Constants.APP_KEY;
        int port = Constants.IM_PORT;
        //启动即时通讯服务
        IMServerLauncherImpl.getInstance(appKey, port).startup();
        System.out.println("启动即时通讯服务成功   端口号：" + port);

        //启动Rpc服务
        RpcServerLauncher.getInstance().start();
//
//        new Thread(new Runnable() {
//            int i = 0;
//
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        i++;
//                        HashMap<String, Object> param = new HashMap<>();
//                        param.put("event", 110);
//
//                        HashMap<String, Object> map = new HashMap<>();
//                        map.put("type", 0);
//                        map.put("fromUserId", 10);
//                        map.put("fromNickname", "我是发消息的人");
//                        map.put("content", "我想告诉你你一件事 " + i);
//
//                        param.put("data", map);
//                        System.out.println("" + i + "发送消息结果：" + LocalSendHelper.sendData("Hello_q7bk6ng5", new Gson().toJson(param), true, String.valueOf(i)));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();

        Thread.currentThread().join();
    }
}
