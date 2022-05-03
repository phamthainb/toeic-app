package com.ptit.toeic;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.toeic.utils.CallAPI;
import com.ptit.toeic.utils.MySharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;


public class Login2 extends AppCompatActivity {
    Button btnSignIn1;
    TextView tvSignUp;
    TextView tvEmail, tvPassword, tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        btnSignIn1 = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.txtSignUp);
        tvEmail = findViewById(R.id.etUsernameLogin);
        tvPassword = findViewById(R.id.edPasswordLogin);
        tvError = findViewById(R.id.tverror);
        btnSignIn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regexPattern = "^(.+)@(\\S+)$";
                if (tvEmail.getText().toString().equals("") || tvPassword.getText().toString().equals("")) {
                    tvError.setText("The field cann't be empty!!!");
                }//check email co chua @ khong?
                else if (!patternMatches(tvEmail.getText().toString(), regexPattern)) {
                    tvError.setText("Email must include @");
                } else {
                    tvError.setText("");
                    String email = tvEmail.getText().toString();
                    String pass = tvPassword.getText().toString();
//                    String email="yen@gmail.com";
//                    String pass="12345678";
                    RequestParams params = new RequestParams();
                    params.put("email", email);
                    params.put("password", pass);
                    new CallAPI(getApplicationContext()).post("/account/login/", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                MySharedPreferences.savePreferences(getApplicationContext(), "access", response.getString("access"));
                                MySharedPreferences.savePreferences(getApplicationContext(), "refresh", response.getString("refresh"));
//                              kiem tra xem co target # 0 trong csdl chua? neu co roi goi sang home, chua co goi sang select target
                                getTarget();
                            } catch (JSONException e) {
                                System.out.println("that bai");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {
                                System.out.println("response : " + errorResponse.getString("detail"));
                                tvError.setText("Email/password no correct");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login2.this, Logup1.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Login2.this, Login.class);
            startActivity(intent);
        }
        //onBackPressed();
        return true;
    }

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public void getTarget() {
        RequestParams params1 = new RequestParams();
        new CallAPI(getApplicationContext()).getWithToken("/account/get_profile/", params1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println("get_target success");
                    System.out.println("response : " + String.valueOf(response.getJSONObject("data").getInt("level")));
                    int target = response.getJSONObject("data").getInt("level");
                    if (target == 0) {
                        Intent intent = new Intent(Login2.this, Target32.class);
                        startActivity(intent);
                    } else {
                        System.out.println("Chuyen sang HOME");
                        //Mo giao dien Home
                        Intent intent = new Intent(Login2.this, PageMain.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("status + " + statusCode);
                System.out.println("headers " + headers[0]);
                System.out.println("error get target");
            }
        });
    }
}