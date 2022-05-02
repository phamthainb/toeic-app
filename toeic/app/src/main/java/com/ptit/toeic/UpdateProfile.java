package com.ptit.toeic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.ptit.toeic.utils.CallAPI;

import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ptit.toeic.utils.MySharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;


public class UpdateProfile extends AppCompatActivity {
    ImageButton btnBack;
    Button btnUpdate;
    EditText name, birthday, phone;
    RadioGroup groupGender, groupTarget;
    RadioButton radioGenderMale, radioGenderFemale, radioTarget1, radioTarget2, radioTarget3;
    String gender, target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        btnBack = findViewById(R.id.btn_back);
        getProfile();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this, SettingPageNgan.class);
                startActivity(intent);
            }
        });

        name = findViewById(R.id.fill1);
        name.setText("Tran Khanh Ngan");
        birthday = findViewById(R.id.fill2);
        birthday.setText("12/12/2002");
        phone = findViewById(R.id.fill3);
        phone.setText("0987898789");

        groupGender = findViewById(R.id.gender_select);
        groupTarget = findViewById(R.id.target_select);

        radioGenderMale = findViewById(R.id.gender_male);
        radioGenderFemale = findViewById(R.id.gender_female);
        radioGenderMale.setChecked(true);
        radioTarget1 = findViewById(R.id.target_level1);
        radioTarget2 = findViewById(R.id.target_level2);
        radioTarget3 = findViewById(R.id.target_level3);
        radioTarget1.setChecked(true);

        btnUpdate = findViewById(R.id.btnUpdate);


        groupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                gender = onGenderChange(group, checkedId);
            }
        });

        groupTarget.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                target = onTargetChange(group, checkedId);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(UpdateProfile.this, "Update profile successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateProfile.this, SettingPageNgan.class);
                startActivity(intent);
            }
        });

    }

    private String onTargetChange(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        String target = "";
        if (checkedRadioId == R.id.target_level1) {
            Toast.makeText(UpdateProfile.this, "level1", Toast.LENGTH_SHORT).show();
            target = "level1";
        } else if (checkedRadioId == R.id.target_level2) {
            Toast.makeText(UpdateProfile.this, "level2", Toast.LENGTH_SHORT).show();
            target = "level2";
        } else if (checkedRadioId == R.id.target_level3) {
            Toast.makeText(UpdateProfile.this, "level3", Toast.LENGTH_SHORT).show();
            target = "level3";
        }
        return target;
    }

    private String onGenderChange(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        String gender = "";
        if (checkedRadioId == R.id.gender_male) {
            Toast.makeText(UpdateProfile.this, "male", Toast.LENGTH_SHORT).show();
            gender = "male";
        } else if (checkedRadioId == R.id.gender_female) {
            Toast.makeText(UpdateProfile.this, "female", Toast.LENGTH_SHORT).show();
            gender = "female";
        }
        return gender;
    }
    public void getProfile() {
        RequestParams params1 = new RequestParams();
        new CallAPI(getApplicationContext()).getWithToken("/account/get_profile/", params1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(UpdateProfile.this,"Call api",Toast.LENGTH_SHORT).show();
                try {
                    Toast.makeText(UpdateProfile.this,"Call api",Toast.LENGTH_SHORT).show();
                   System.out.println("response : " + String.valueOf(response.getJSONObject("data").getInt("level")));
                    int target = response.getJSONObject("data").getInt("level");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(UpdateProfile.this,"Call api false",Toast.LENGTH_SHORT).show();
                System.out.println("status + " + statusCode);
                System.out.println("headers " + headers[0]);
                System.out.println("error get target");
            }
        });
    }
}
