package com.ptit.toeic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    TextView updateProfile, changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setup toolbar
        ActionBar actionBar = getSupportActionBar();
        String toolbar_title = "Setting";
        // styles
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);

        // back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.page9_setting);
        updateProfile=findViewById(R.id.textViewProfile);
        changePass=findViewById(R.id.textChangePassword);

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this, UpdateProfileActivity.class);
                //Toast.makeText(Login.this,"Sing In",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this, ChangePasswordActivity.class);
                //Toast.makeText(Login.this,"Sing In",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this.getApplicationContext(), PageMain.class);
        startActivity(intent);
        return true;
    }
}
