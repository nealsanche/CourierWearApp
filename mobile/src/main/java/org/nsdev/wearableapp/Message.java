package org.nsdev.wearableapp;

import me.denley.courier.Deliverable;

/**
 * Created by neal on 15-03-28.
 */
@Deliverable
public class Message {
    String mSender;
    String mMessageText;
    long mTimeStamp;

    public Message() {
    }

    public Message(String sender, String messageText, long timeStamp) {

        mSender = sender;
        mMessageText = messageText;
        mTimeStamp = timeStamp;
    }
}
