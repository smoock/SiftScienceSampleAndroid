package com.moockscience.smoock.siftsciencesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

import siftscience.android.Sift;

public class MainActivity extends AppCompatActivity {


    String mUserId;
    Button mSaveButton;
    Button mRandomButton;
    EditText mUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUserId = "test_user123";
        mSaveButton = (Button) findViewById(R.id.savebutton);
        mRandomButton = (Button) findViewById(R.id.randombutton);
        mUserName = (EditText) findViewById(R.id.username);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserId = mUserName.getText().toString();
                Sift.get().setUserId(mUserId);
            }
        });

        mRandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserName.setText(random());
            }
        });

        Sift.open(this);
        Sift.get().setConfig(new Sift.Config.Builder()
                .withAccountId("YOUR_ACCOUNT_ID")
                .withBeaconKey("YOUR_BEACON_KEY")
                .build());
        Sift.collect();


        final Intent siftIntent = new Intent(MainActivity.this, Main2Activity.class);
        siftIntent.putExtra("user_id", mUserId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(siftIntent);
//                Snackbar.make(view,"Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Sift.open(this);
        Sift.collect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Sift.get().save();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sift.close();
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


    public static String random() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        int length = 10;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        return output;
    }

}
