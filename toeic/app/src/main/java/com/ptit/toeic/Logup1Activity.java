package com.ptit.toeic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import com.ptit.toeic.utils.CallAPI;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class Logup1Activity extends AppCompatActivity {
    Button btnSignUp1;
    TextView tvSignIn;
    TextView tvEmail;
    TextView tvPassword;
    TextView tvRePassword;
    TextView tvError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup1);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        btnSignUp1 = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.txtSignIn);
        tvEmail = findViewById(R.id.etUsername);
        tvPassword = findViewById(R.id.etPassword);
        tvRePassword = findViewById(R.id.etRePassword);
        tvError=findViewById(R.id.tverrorLogup);

        btnSignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexPattern = "^(.+)@(\\S+)$";
                if(tvEmail.getText().toString().equals("")||tvPassword.getText().toString().equals("")||tvRePassword.getText().toString().equals("")){
                    tvError.setText("The field cann't be empty!!!");
                }else if(!tvPassword.getText().toString().equals(tvRePassword.getText().toString())){
                    tvError.setText("Password don't match!!!");
                }else if(!patternMatches(tvEmail.getText().toString(), regexPattern)){
                    tvError.setText("Email must include @");
                } else {
                    tvError.setText("");
                    String email = tvEmail.getText().toString();
                    String password = tvPassword.getText().toString();
                    String[] str = email.split("@");
                    String username = str[0];

                    RequestParams params = new RequestParams();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("username", username);

                    new CallAPI(getApplicationContext()).post("/account/signup/", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                System.out.println("response : " + response.getString("message"));
                                // khởi tạo AlertDialog từ đối tượng Builder. tham số truyền vào ở đây là context.
                                AlertDialog.Builder builder = new AlertDialog.Builder(Logup1Activity.this);
                                // set Message là phương thức thiết lập câu thông báo
                                builder.setMessage("Register success!!! Please login.")
//                                     positiveButton là nút thuận : đặt là OK
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(Logup1Activity.this, Login2Activity.class);
                                                startActivity(intent);
                                            }
                                        });
                                // tạo dialog và hiển thị
                                builder.create().show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {
                                System.out.println("response : " + errorResponse.getJSONObject("message").getJSONArray("email").get(0));
                                tvError.setText("Email existed, Please Signup email other.");
                                tvEmail.setText("");
                                tvPassword.setText("");
                                tvRePassword.setText("");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Logup1Activity.this, Login2Activity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==android.R.id.home){
                Intent intent=new Intent(Logup1Activity.this, LoginActivity.class);
                startActivity(intent);}
               // onBackPressed();
                return true;
        }
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    }