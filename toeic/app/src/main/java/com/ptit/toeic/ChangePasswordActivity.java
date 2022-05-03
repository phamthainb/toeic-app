package com.ptit.toeic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.toeic.utils.CallAPI;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnUpdate;
    EditText oldPass, newPass1, newPass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        // setup toolbar
        ActionBar actionBar = getSupportActionBar();
        String toolbar_title = "Change password";

        // styles
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);

        // back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        oldPass = findViewById(R.id.old_pass);
        newPass1 = findViewById(R.id.new_pass1);
        newPass2 = findViewById(R.id.new_pass2);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old = oldPass.getText().toString();
                String new1 = newPass1.getText().toString();
                String new2 = newPass2.getText().toString();
                if (new1.equals("") || new2.equals("") || old.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Enter all fields!", Toast.LENGTH_SHORT).show();
                } else if (!new1.equals(new2)) {
                    Toast.makeText(ChangePasswordActivity.this, "Check your new password", Toast.LENGTH_SHORT).show();
                } else {
                    //call api
                    RequestParams params = new RequestParams();
                    params.put("old", old);
                    params.put("new", new1);
                    new CallAPI(getApplicationContext()).postWithToken("/account/reset_password//", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            Toast.makeText(ChangePasswordActivity.this, "Update password successfully, please login again!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {
                                Toast.makeText(ChangePasswordActivity.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                                System.out.println("response : " + errorResponse.getString("detail"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    Toast.makeText(getApplicationContext(), "Update password successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
//        onBackPressed();
        Intent intent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
        startActivity(intent);
        return true;
    }
}
