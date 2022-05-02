package com.ptit.toeic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ChangePassword extends AppCompatActivity {
    ImageButton btnBack;
    Button btnUpdate;
    EditText oldPass, newPass1, newPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        btnBack = findViewById(R.id.btn_back);
        oldPass = findViewById(R.id.old_pass);
        newPass1 = findViewById(R.id.new_pass1);
        newPass2 = findViewById(R.id.new_pass2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassword.this, SettingPageNgan.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = oldPass.getText().toString();
                String new1 = newPass1.getText().toString();
                String new2 = newPass2.getText().toString();
                if (new1.equals("") || new2.equals("") || old.equals("")) {
                    Toast.makeText(ChangePassword.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
                } else if (!new1.equals(new2)) {
                    Toast.makeText(ChangePassword.this, "Check your new password", Toast.LENGTH_SHORT).show();
                } else {
                    //call api
                    Toast.makeText(ChangePassword.this, "Update password successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangePassword.this, SettingPageNgan.class);
                    startActivity(intent);
                }
            }
        });

    }
}
