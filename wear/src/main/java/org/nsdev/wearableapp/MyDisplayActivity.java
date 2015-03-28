package org.nsdev.wearableapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MyDisplayActivity extends Activity {

    @InjectView(R.id.text)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.root)
    public void onRootClicked(View root) {
        // Do something
        mTextView.setText("Tapped.");
    }
}