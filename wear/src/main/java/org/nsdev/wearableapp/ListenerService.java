package org.nsdev.wearableapp;

import android.content.Intent;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

import me.denley.courier.Packager;

/**
 * Created by neal on 15-03-28.
 */
public class ListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals("/message")) {
            String message = Packager.unpack(messageEvent.getData(), String.class);

            Intent i = new Intent();
            i.setAction("org.nsdev.wearableapp.SHOW_NOTIFICATION");
            i.putExtra(MyPostNotificationReceiver.CONTENT_KEY, message);
            sendBroadcast(i);
        }

        super.onMessageReceived(messageEvent);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
    }

    @Override
    public void onPeerConnected(Node peer) {
        super.onPeerConnected(peer);
    }

    @Override
    public void onPeerDisconnected(Node peer) {
        super.onPeerDisconnected(peer);
    }
}
