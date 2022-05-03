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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.RequestParams;
import com.ptit.toeic.utils.CallAPI;

import cz.msebera.android.httpclient.Header;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class UpdateProfileActivity extends AppCompatActivity {
    ImageButton btnBack;
    Button btnUpdate;
    EditText name, birthday, phone;
    RadioGroup groupGender, groupTarget;
    RadioButton radioGenderMale, radioGenderFemale, radioTarget1, radioTarget2, radioTarget3;
    int gender;
    int target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        // setup toolbar
        ActionBar actionBar = getSupportActionBar();
        String toolbar_title = "Update profile";
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


        name = findViewById(R.id.fill1);
        birthday = findViewById(R.id.fill2);
        phone = findViewById(R.id.fill3);

        groupGender = findViewById(R.id.gender_select);
        groupTarget = findViewById(R.id.target_select);

        radioGenderMale = findViewById(R.id.gender_male);
        radioGenderFemale = findViewById(R.id.gender_female);

        radioTarget1 = findViewById(R.id.target_level1);
        radioTarget2 = findViewById(R.id.target_level2);
        radioTarget3 = findViewById(R.id.target_level3);


        btnUpdate = findViewById(R.id.btnUpdate);
        RequestParams params1 = new RequestParams();
        new CallAPI(getApplicationContext()).getWithToken("/account/get_profile/", params1, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    System.out.println("response : " + response);
                    int target = response.getJSONObject("data").getInt("level");
                    if (target == 1) {
                        radioTarget1.setChecked(true);
                    } else if (target == 2) {
                        radioTarget2.setChecked(true);
                    } else if (target == 3) {
                        radioTarget3.setChecked(true);
                    }
                    name.setText(response.getJSONObject("data").getString("fullname"));
                    phone.setText(response.getJSONObject("data").getString("phone"));
                    birthday.setText(response.getJSONObject("data").getString("birth_day"));

                    int genderG = response.getJSONObject("data").getInt("sex");
                    if (genderG == 0) radioGenderMale.setChecked(true);
                    else if (genderG == 1) radioGenderFemale.setChecked(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(UpdateProfileActivity.this, "Call api false", Toast.LENGTH_SHORT).show();
                System.out.println("status + " + statusCode);
                System.out.println("headers " + headers[0]);
                System.out.println("error get target");
            }
        });

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
                RequestParams params = new RequestParams();

                int getGender = groupGender.getCheckedRadioButtonId();
                if (getGender == R.id.gender_male) {
                    params.put("sex", 0);
                } else if (getGender == R.id.gender_female) {
                    params.put("sex", 1);
                }

                int getTarget = groupTarget.getCheckedRadioButtonId();
                if (getTarget == R.id.target_level1) {
                    params.put("level", 1);
                } else if (getTarget == R.id.target_level2) {
                    params.put("level", 2);
                } else if (getTarget == R.id.target_level3) {
                    params.put("level", 3);
                }

                params.put("fullname", name.getText().toString());
                params.put("phone", phone.getText().toString());
                params.put("birth_day", birthday.getText().toString());


                new CallAPI(getApplicationContext()).putWithToken("/account/update_profile/", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        System.out.println("pa " + params);
                        Toast.makeText(UpdateProfileActivity.this, "Update profile successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateProfileActivity.this, SettingActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            Toast.makeText(UpdateProfileActivity.this, "Update profile fail", Toast.LENGTH_SHORT).show();
                            System.out.println("response : " + errorResponse.getString("detail"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
//        onBackPressed();
        Intent intent = new Intent(UpdateProfileActivity.this, SettingActivity.class);
        startActivity(intent);
        return true;
    }



    private int onTargetChange(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        int target = 0;
        if (checkedRadioId == R.id.target_level1) {
            target = 1;
        } else if (checkedRadioId == R.id.target_level2) {
            target = 2;
        } else if (checkedRadioId == R.id.target_level3) {
            target = 3;
        }
        return target;
    }

    private int onGenderChange(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        int gender = -1;
        if (checkedRadioId == R.id.gender_male) {
            gender = 0;
        } else if (checkedRadioId == R.id.gender_female) {
            gender = 1;
        }
        return gender;
    }

}
