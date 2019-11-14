package com.cuityk.helloappim.rpc;

import com.cuityk.helloappim.javarpcproto.JavaBaseResult;
import com.cuityk.helloappim.javarpcproto.JavaRpcServerInterfaceGrpc;
import com.cuityk.helloappim.javarpcproto.MessageParam;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.grpc.stub.StreamObserver;
import net.openmob.mobileimsdk.server.utils.LocalSendHelper;

public class RpcServerImpl extends JavaRpcServerInterfaceGrpc.JavaRpcServerInterfaceImplBase {

    //发送消息到指定用户
    @Override
    public void sendMessageToUser(MessageParam request, StreamObserver<JavaBaseResult> responseObserver) {
        //获取用户名和消息内容
        String toUsername = request.getToUsername();
        String jsonContent = request.getContent();
        System.out.println("RPC收到消息：" + jsonContent);
        JavaBaseResult baseResult = null;
        //发送消息
        try {
            JsonObject jo = (JsonObject) new JsonParser().parse(jsonContent);
            int msgId = jo.get("msgId").getAsInt();
            if (!LocalSendHelper.sendData(toUsername, jsonContent, true, String.valueOf(msgId))) {
                baseResult = JavaBaseResult.newBuilder().setCode(-100).setMessage("发送消息失败").build();
            }
        } catch (Exception e) {
            baseResult = JavaBaseResult.newBuilder().setCode(-100).setMessage("发送消息失败").build();
        } finally {
            responseObserver.onNext(baseResult);
            responseObserver.onCompleted();
        }
    }
}
