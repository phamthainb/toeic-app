package com.ptit.toeic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ptit.toeic.activity.Login;

public class Logup1 extends AppCompatActivity {
    Button btnSignUp1;
    TextView tvSignIn;
    TextView tvEmail;
    TextView tvPassword;
    TextView tvRePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup1);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        btnSignUp1=findViewById(R.id.btnSignUp);
        tvSignIn=findViewById(R.id.txtSignIn);
        tvEmail=findViewById(R.id.etUsername);
        tvPassword=findViewById(R.id.etPassword);
        tvRePassword=findViewById(R.id.etRePassword);

        btnSignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=tvEmail.getText().toString();
                String password=tvPassword.getText().toString();
                String repassword=tvRePassword.getText().toString();
                //check validate cuar email, pass, pass vs repass cos giong nhau ko?

            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Logup1.this,Login2.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==android.R.id.home){
                Intent intent=new Intent(Logup1.this, Login.class);
                startActivity(intent);}
               // onBackPressed();
                return true;
    }

}