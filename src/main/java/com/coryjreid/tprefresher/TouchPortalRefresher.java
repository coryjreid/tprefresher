package com.coryjreid.tprefresher;

import java.util.Random;

import com.christophecvb.touchportal.TouchPortalPlugin;
import com.christophecvb.touchportal.annotations.Category;
import com.christophecvb.touchportal.annotations.Event;
import com.christophecvb.touchportal.annotations.Plugin;
import com.christophecvb.touchportal.annotations.Setting;
import com.christophecvb.touchportal.annotations.State;
import com.christophecvb.touchportal.model.TPBroadcastMessage;
import com.christophecvb.touchportal.model.TPInfoMessage;
import com.christophecvb.touchportal.model.TPListChangeMessage;
import com.christophecvb.touchportal.model.TPNotificationOptionClickedMessage;
import com.christophecvb.touchportal.model.TPSettingsMessage;
import com.google.gson.JsonObject;

@Plugin(version = 1L, colorDark = "#203060", colorLight = "#4070F0", name = "Touch Portal Refresher")
public class TouchPortalRefresher extends TouchPortalPlugin implements TouchPortalPlugin.TouchPortalPluginListener {
    private static final TouchPortalRefresher touchPortalRefresher = new TouchPortalRefresher();

    @Setting(name = "Refresh Interval (ms)", defaultValue = "500", minValue = 500, maxValue = Integer.MAX_VALUE)
    private int refreshInterval;

    @State(defaultValue = "-1", categoryId = "TouchPortalRefresherMain")
    @Event(format = "When randomNumber becomes $val")
    private String randomNumber;
    public TouchPortalRefresher() {
        super(true);
    }

    private Thread refresherThread = null;

    @Override
    public void onDisconnected(final Exception exception) {
        // Socket connection is lost or plugin has received close message
        System.exit(0);
    }

    @Override
    public void onReceived(final JsonObject jsonMessage) {
    }

    @Override
    public void onInfo(final TPInfoMessage tpInfoMessage) {
        if (refresherThread != null && refresherThread.isAlive()) {
            refresherThread.interrupt();
        }

        refresherThread = new Thread(() -> {
            while (true) {
                final String value = Integer.toString(Math.abs(new Random().nextInt()));
                touchPortalRefresher.sendStateUpdate(
                    TouchPortalRefresherConstants.TouchPortalRefresherMain.States.RandomNumber.ID,
                    value);
                try {
                    Thread.sleep(refreshInterval);
                } catch (final InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        refresherThread.start();
    }

    @Override
    public void onListChanged(final TPListChangeMessage tpListChangeMessage) {
    }

    @Override
    public void onBroadcast(final TPBroadcastMessage tpBroadcastMessage) {
    }

    @Override
    public void onSettings(final TPSettingsMessage tpSettingsMessage) {
    }

    @Override
    public void onNotificationOptionClicked(final TPNotificationOptionClickedMessage tpNotificationOptionClickedMessage) {
    }

    public static void main(final String... args) {
        // Initiate the connection with the Touch Portal Plugin System
        touchPortalRefresher.connectThenPairAndListen(touchPortalRefresher);
    }

    private enum Categories {
        @Category(id = "TouchPortalRefresherMain", name = "Touch Portal Refresher", imagePath = "images/icon.png")
        Main
    }
}
