package com.cuityk.helloappim.listener;


import com.cuityk.helloappim.rpc.RpcClientImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.openmob.mobileimsdk.server.event.MessageQoSEventListenerS2C;
import net.openmob.mobileimsdk.server.protocal.Protocal;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * 服务器主动发消息回调
 */
public class MessageQoSEventListenerS2CImpl implements MessageQoSEventListenerS2C {


    //消息发送成功回调
    @Override
    public void messagesBeReceived(String theFingerPrint) {
        try {
            int msgId = Integer.parseInt(theFingerPrint);
            boolean success = RpcClientImpl.s2cMsgResult(msgId, true);
            System.out.println("ServerToClientMsgFailResult-ToGoServer: " + success);
        } catch (Exception e) {
        }
        System.out.println("服务器to客户端--- 发送消息成功  " + theFingerPrint);
    }

    //消息发送失败回调
    @Override
    public void messagesLost(ArrayList<Protocal> arrayList) {
        if (arrayList != null && !arrayList.isEmpty()) {
            for (Protocal protocal : arrayList) {
                System.out.println("失败消息为： " + protocal.toGsonString());
                try {
                    int msgId = Integer.parseInt(protocal.getFp());
                    boolean success = RpcClientImpl.s2cMsgResult(msgId, false);
                    System.out.println("ServerToClientMsgFailResult-ToGoServer: " + success);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("服务器to客户端--- 发送消息失败  " + new Gson().toJson(arrayList));
    }
}
