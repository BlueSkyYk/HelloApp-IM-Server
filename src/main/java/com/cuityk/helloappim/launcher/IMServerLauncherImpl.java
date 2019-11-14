package com.cuityk.helloappim.launcher;

import com.cuityk.helloappim.listener.MessageQoSEventListenerS2CImpl;
import com.cuityk.helloappim.listener.ServerEventListenerImpl;
import net.openmob.mobileimsdk.server.ServerLauncher;
import net.openmob.mobileimsdk.server.qos.QoS4ReciveDaemonC2S;
import net.openmob.mobileimsdk.server.qos.QoS4SendDaemonS2C;
import net.openmob.mobileimsdk.server.utils.ServerToolKits;

import java.io.IOException;

/**
 * 启动器
 */
public class IMServerLauncherImpl extends ServerLauncher {

    private static IMServerLauncherImpl instance = null;

    public static IMServerLauncherImpl getInstance(String appkey, int port) throws IOException {
        if (instance == null) {
            // 设置AppKey
            IMServerLauncherImpl.appKey = appkey;
            IMServerLauncherImpl.PORT = port;
            QoS4SendDaemonS2C.getInstance().setDebugable(true);
            QoS4ReciveDaemonC2S.getInstance().setDebugable(true);
            ServerToolKits.setSenseMode(ServerToolKits.SenseMode.MODE_3S);
            instance = new IMServerLauncherImpl();
        }
        return instance;
    }

    private IMServerLauncherImpl() throws IOException {
        super();
    }

    @Override
    protected void initListeners() {
        // ** 设置回调
        this.setServerEventListener(new ServerEventListenerImpl());
        this.setServerMessageQoSEventListener(new MessageQoSEventListenerS2CImpl());
    }
}
