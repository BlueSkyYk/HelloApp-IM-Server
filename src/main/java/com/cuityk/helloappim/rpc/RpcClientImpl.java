package com.cuityk.helloappim.rpc;

import com.cuityk.helloappim.gorpcproto.*;
import com.cuityk.helloappim.launcher.Constants;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

public class RpcClientImpl {

    private static ManagedChannel channel = null;

    //登录IM
    public static boolean loginIM(String account, String password) throws Exception {
        ManagedChannel channel = createChannel();
        GoRpcServerInterfaceGrpc.GoRpcServerInterfaceBlockingStub goRpcServerInterfaceBlockingStub = GoRpcServerInterfaceGrpc.newBlockingStub(channel);
        LoginParam loginParam = LoginParam.newBuilder().setAccount(account).setPassword(password).build();
        GoBaseResult baseResult = goRpcServerInterfaceBlockingStub.iMLogin(loginParam);
        shutdownChannel(channel);
        if (baseResult == null) {
            throw new Exception("请求GoRpcServer失败");
        } else {
            if (baseResult.getCode() == 200) {
                return true;
            } else {
                throw new Exception(baseResult.getMessage());
            }
        }
    }

    //返回S2C消息发送结果
    public static boolean s2cMsgResult(int msgId, boolean success) throws Exception {
        ManagedChannel channel = createChannel();
        GoRpcServerInterfaceGrpc.GoRpcServerInterfaceBlockingStub goRpcServerInterfaceBlockingStub = GoRpcServerInterfaceGrpc.newBlockingStub(channel);
        ServerToClientMsgResultParam msgResultParam = ServerToClientMsgResultParam.newBuilder().setMsgId(msgId).setCode(success ? 200 : -100).build();
        GoBaseResult baseResult = goRpcServerInterfaceBlockingStub.serverToClientMessageResult(msgResultParam);
        shutdownChannel(channel);
        if (baseResult == null) {
            throw new Exception("请求GoRpcServer失败");
        } else {
            if (baseResult.getCode() == 200) {
                return true;
            } else {
                throw new Exception(baseResult.getMessage());
            }
        }
    }


    //创建通道
    private static ManagedChannel createChannel() {
        if (channel == null || channel.isShutdown()) {
            channel = NettyChannelBuilder.forAddress(Constants.GO_RPC_HOST, Constants.GO_RPC_PORT)
                    .negotiationType(NegotiationType.PLAINTEXT)
                    .build();
        }
        return channel;
    }

    //关闭通道
    private static void shutdownChannel(ManagedChannel channel) {
        try {
            channel.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
