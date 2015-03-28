package org.nsdev.wearableapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class ContentActivity extends Activity {

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
}
