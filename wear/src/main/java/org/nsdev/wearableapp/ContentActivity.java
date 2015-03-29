package org.nsdev.wearableapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import me.denley.courier.Courier;
import me.denley.courier.ReceiveData;

public class ContentActivity extends Activity {

    Message mMessage;

    @InjectView(R.id.watch_view_stub)
    WatchViewStub mStub;

    @Optional
    @InjectView(value = R.id.text)
    TextView mTextView;

    @Optional
    @InjectView(R.id.dismiss_overlay)
    DismissOverlayView mDismissOverlayView;

    private GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);

        // Keep the screen on - Be careful in regular apps not to do this
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                ButterKnife.inject(ContentActivity.this, mStub);

                mDismissOverlayView.setIntroText(R.string.dismiss_intro_text);
                mDismissOverlayView.showIntroIfNecessary();

                mDetector = new GestureDetector(ContentActivity.this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public void onLongPress(MotionEvent e) {
                        mDismissOverlayView.show();
                    }
                });
            }
        });

        Courier.startReceiving(this);
    }

    @Override
    protected void onDestroy() {
        Courier.stopReceiving(this);
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private void updateMessage() {
        mTextView.setText(mMessage.mMessageText);
    }

    @ReceiveData("/message/data")
    void onMessageChanged(Message message) {
        mMessage = message;
        updateMessage();
    }
}
