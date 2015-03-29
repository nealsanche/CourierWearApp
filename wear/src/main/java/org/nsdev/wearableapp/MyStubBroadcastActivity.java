package org.nsdev.wearableapp;

import android.app.Activity;
import android.os.Bundle;


/**
 * Example shell activity which simply broadcasts to our receiver and exits.
 */
public class MyStubBroadcastActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finish();
    }
}
