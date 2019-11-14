package com.cuityk.helloappim.listener;

import com.cuityk.helloappim.entity.Message;
import com.cuityk.helloappim.rpc.RpcClientImpl;
import com.cuityk.helloappim.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.rabbitmq.tools.json.JSONUtil;
import net.openmob.mobileimsdk.server.event.ServerEventListener;
import net.openmob.mobileimsdk.server.protocal.Protocal;
import org.apache.mina.core.session.IoSession;

/**
 * 框架基本事件回调
 */
public class ServerEventListenerImpl implements ServerEventListener {

    // 用户身份验证回调方法定义
    // 服务端的应用层可在本方法中实现用户登陆验证。详细请参见API文档说明。
    @Override
    public int onVerifyUserCallBack(String username, String password, String extra, IoSession ioSession) {
//        return 0;
        System.out.println("用户登录：  " + username + "  " + password + "  " + extra);
        //调用GoRpcServer验证登录信息
        try {
            if (RpcClientImpl.loginIM(username, password)) {
                //返回0表示登录成功
                return 0;
            }
        } catch (Exception e) {
            System.out.println("IM用户登录失败：" + e);
            return -1;
        }
        return -1;
    }

    // 用户登录验证成功后的回调方法定义
    // 服务端的应用层通常可在本方法中实现用户上线通知等。详细请参见API文档说明。
    @Override
    public void onUserLoginAction_CallBack(String userId, String extra, IoSession ioSession) {
        System.out.println("用户登录成功：  " + userId + "  " + extra);
    }

    // 用户退出登录回调方法定义。
    // 服务端的应用层通常可在本方法中实现用户下线通知等。详细请参见API文档说明。
    @Override
    public void onUserLogoutAction_CallBack(String userId, Object obj, IoSession ioSession) {
        System.out.println("用户退出登录：  " + userId + "  " + obj);
    }

    // 通用数据回调方法定义（客户端发给服务端的（即接收user_id=0））
    // 上层通常可在本方法中实现如：添加好友请求等业务实现。详细请参见API文档说明。
    @Override
    public boolean onTransBuffer_C2S_CallBack(Protocal protocal, IoSession ioSession) {
        System.out.println("客户端to服务端   " + new Gson().toJson(protocal));
        return false;
    }

    // 通道数据回调函数定义（客户端发给客户端的（即接收user_id>0））。详细请参见API文档说明。
    // 上层通常可在本方法中实现用户聊天信息的收集，以便后期监控分析用户的行为等^_^。
    @Override
    public void onTransBuffer_C2C_CallBack(Protocal protocal) {
        System.out.println("客户端to客户端   " + new Gson().toJson(protocal));
    }

    // 通用数据实时发送失败后的回调函数定义（客户端发给客户端的（即接收user_id>0））
    // 开发者可在此方法中处理离线消息的保存等。详细请参见API文档说明。
    @Override
    public boolean onTransBuffer_C2C_RealTimeSendFaild_CallBack(Protocal protocal) {
        System.out.println("onTransBuffer_C2C_RealTimeSendFaild_CallBack" + "消息发送失败：" + new Gson().toJson(protocal));
        return false;
    }
}
