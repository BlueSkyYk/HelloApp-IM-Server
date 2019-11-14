package com.cuityk.helloappim.launcher;

import com.cuityk.helloappim.rpc.RpcServerImpl;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;

import java.io.IOException;

/**
 * Rpc服务启动器
 */
public class RpcServerLauncher {

    public static RpcServerLauncher instance = new RpcServerLauncher();

    private RpcServerLauncher() {
    }

    public static RpcServerLauncher getInstance() {
        return instance;
    }

    private Server mRpcServer;

    //开启服务
    public void start() throws IOException {
        mRpcServer = NettyServerBuilder
                .forPort(Constants.JAVA_RPC_PORT)
                .addService(new RpcServerImpl().bindService())
                .build()
                .start();
        System.out.println("启动RPC服务成功。。IP = " + " Port = " + Constants.JAVA_RPC_PORT);
    }

    public boolean isShutdown() {
        return mRpcServer == null ? true : mRpcServer.isShutdown();
    }

    //停止服务
    public void stop() {
        if (mRpcServer != null) {
            mRpcServer.shutdownNow();
        }
    }


}
