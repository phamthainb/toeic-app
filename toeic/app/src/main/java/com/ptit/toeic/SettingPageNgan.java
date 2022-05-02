package com.ptit.toeic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingPageNgan extends AppCompatActivity {

    TextView updateProfile, changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page9_setting);
        updateProfile=findViewById(R.id.textViewProfile);
        changePass=findViewById(R.id.textChangePassword);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingPageNgan.this,UpdateProfile.class);
                //Toast.makeText(Login.this,"Sing In",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingPageNgan.this,ChangePassword.class);
                //Toast.makeText(Login.this,"Sing In",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
