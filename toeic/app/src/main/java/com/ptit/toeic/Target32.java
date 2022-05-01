package com.ptit.toeic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.toeic.utils.CallAPI;
import com.ptit.toeic.utils.MySharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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
                CallAPISetTarger(1);
            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Target32.this,"Choose level 2",Toast.LENGTH_SHORT).show();
                CallAPISetTarger(2);

            }
        });
        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Target32.this,"Choose level 3",Toast.LENGTH_SHORT).show();
                CallAPISetTarger(3);
            }
        });
    }
        public void CallAPISetTarger(int i){
            RequestParams params=new RequestParams();
            params.put("level",i+"");
            new CallAPI(getApplicationContext()).postWithToken("/account/set_target/", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        System.out.println("response : " + response.getString("message"));
                        System.out.println("set_target success");
                        //Mo giao dien Home
//                            Intent intent=new Intent(Target32.this,Home.class);
//                            startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    System.out.println("error_settarget");
                }
            });
        }
}