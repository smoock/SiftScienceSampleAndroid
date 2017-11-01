package com.moockscience.smoock.siftsciencesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import siftscience.android.Sift;

public class Main2Activity extends AppCompatActivity {

    String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mUserId = null;
            } else {
                mUserId = extras.getString("user_id");
            }
        } else {
            mUserId = (String) savedInstanceState.getSerializable("user_id");
        }

        final Intent siftIntent = new Intent(Main2Activity.this, MainActivity.class);
        siftIntent.putExtra("user_id", mUserId);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(siftIntent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Sift.open(this);
        Sift.get().setConfig(new Sift.Config.Builder()
                .withAccountId("59ef7ca64f0cb9954edcb6cc")
                .withBeaconKey("f46eba38fa")
                .build());
        // For Fragments, use Sift.open(this.getActivity()) instead
        Sift.collect();
        Sift.get().setUserId(mUserId);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Sift.get().save();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Sift.close();
    }

}
