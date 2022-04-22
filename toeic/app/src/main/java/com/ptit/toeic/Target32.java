package com.ptit.toeic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Target32 extends AppCompatActivity {
    ImageView level1,level2,level3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target32);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Choose your target");
        //mapping
        level1=findViewById(R.id.level1);
        level2=findViewById(R.id.level2);
        level3=findViewById(R.id.level3);
        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Target32.this,"Choose level 1",Toast.LENGTH_SHORT).show();
            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Target32.this,"Choose level 2",Toast.LENGTH_SHORT).show();
            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Target32.this,"Choose level 3",Toast.LENGTH_SHORT).show();
            }
        });
    }
}