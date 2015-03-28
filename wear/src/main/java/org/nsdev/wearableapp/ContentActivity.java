package org.nsdev.wearableapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class ContentActivity extends Activity {

    @Optional
    @InjectView(value = R.id.text)
    TextView mTextView;

    @InjectView(R.id.watch_view_stub)
    WatchViewStub mStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.inject(this);

        mStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                ButterKnife.inject(ContentActivity.this, mStub);
            }
        });
    }
}
