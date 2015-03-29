package org.nsdev.shared;

import me.denley.courier.Deliverable;

/**
 * Created by neal on 15-03-28.
 */
@Deliverable
public class Message {
    public String mSender;
    public String mMessageText;
    public long mTimeStamp;

    public Message() {
    }

    public Message(String sender, String messageText, long timeStamp) {
        mSender = sender;
        mMessageText = messageText;
        mTimeStamp = timeStamp;
    }
}
