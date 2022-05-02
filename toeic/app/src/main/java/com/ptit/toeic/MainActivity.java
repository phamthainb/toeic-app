package com.ptit.toeic;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);


    }
}