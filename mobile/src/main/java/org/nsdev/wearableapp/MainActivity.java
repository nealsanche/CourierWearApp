package org.nsdev.wearableapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.wearable.Node;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.nsdev.shared.Message;

import java.io.IOException;
import java.util.List;

import me.denley.courier.Courier;
import me.denley.courier.LocalNode;
import me.denley.courier.ReceiveMessages;
import me.denley.courier.RemoteNodes;


public class MainActivity extends Activity {

    @LocalNode
    Node localNode;

    private List<Node> mConnectedNodes;
    private EditText mServerHost;

    @RemoteNodes
    void onConnectionStateChanged(List<Node> connectedNodes) {
        mConnectedNodes = connectedNodes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServerHost = (EditText) findViewById(R.id.server);
        Courier.startReceiving(this);
    }

    @Override
    protected void onDestroy() {
        Courier.stopReceiving(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaySomethingButtonClicked(View button) {
        Courier.deliverMessage(this, "/message", "From Mobile!");

        Message message = new Message("Me", getRandomPhrase(), System.currentTimeMillis());
        Courier.deliverData(this, "/message/data", message);
    }

    private String getRandomPhrase() {
        int randomIndex = (int) (Math.random() * 10);

        switch (randomIndex) {
            case 0:
                return "Come to dinner.";
            case 1:
                return "See a doctor.";
            case 2:
                return "Where are you?";
            case 3:
                return "What is your name?";
            case 4:
                return "Take care.";
            case 5:
                return "Be well.";
            case 6:
                return "What's new?";
            case 7:
                return "Have a good one.";
            case 8:
                return "Don't do anything stupid.";
            case 9:
                return "How are you feeling?";
            case 10:
                return "Don't do anything I wouldn't do.";
        }
        return "No comment.";
    }

    @ReceiveMessages("/keynote/command")
    public void onKeynoteMessage(String message) {
        try {
            runCommand(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final OkHttpClient client = new OkHttpClient();

    public void runCommand(final String command) throws Exception {
        Request request = new Request.Builder()
                .get()
                .url(String.format("http://%s/json/%s", mServerHost.getText().toString(), command))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                try {
                    JSONObject responseJson = new JSONObject(response.body().string());

                    String slide = responseJson.optString("slide");
                    String build = responseJson.optString("build");

                    Message message = new Message("Me", "Slide: " + slide + "\nBuild: " + build, System.currentTimeMillis());
                    Courier.deliverData(MainActivity.this, "/message/data", message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onPreviousButtonClicked(View view) {
        try {
            runCommand("previous");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onNextButtonClicked(View view) {
        try {
            runCommand("next");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
