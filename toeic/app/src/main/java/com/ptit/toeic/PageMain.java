package com.ptit.toeic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.CallAPI;
import com.ptit.toeic.utils.MySharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PageMain extends AppCompatActivity {
    CallAPI callAPI;
    QuestionDao questionDao;
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
        callAPI = new CallAPI(this.getApplicationContext());
        questionDao = new QuestionDao(this.getApplicationContext());
        context = this.getApplicationContext();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String toolbar_title = "Ptit Toeic";
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.seting_icon);    //Icon muốn hiện thị
        actionBar.setDisplayUseLogoEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout contrainL1 = findViewById(R.id.constraintLayout2);
        ConstraintLayout contrainL2 = findViewById(R.id.constraintLayout3);
        ConstraintLayout contrainL3 = findViewById(R.id.constraintLayout4);
        ConstraintLayout contrainL4 = findViewById(R.id.constraintLayout5);
        ConstraintLayout contrainL5 = findViewById(R.id.constraintLayout6);
        ConstraintLayout contrainL6 = findViewById(R.id.constraintLayout7);
        ConstraintLayout contrainL7 = findViewById(R.id.constraintLayout8);
        ConstraintLayout contrainL8 = findViewById(R.id.constraintLayout9);

        contrainL1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_part(1, 10);
                    }
                }
        );
        contrainL2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("1: " + MySharedPreferences.getPreferences(context, "current_id", ""));
                        select_part(2, 10);

                    }
                }
        );
        contrainL3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_part(3, 10);
                    }
                }
        );
        contrainL4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_part(4, 10);
                    }
                }
        );
        contrainL5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_part(5, 10);
                    }
                }
        );
        contrainL6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_part(6, 10);
                    }
                }
        );
        contrainL7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_part(7, 10);
                    }
                }
        );
        contrainL8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        get_test();
                    }
                }
        );
    }

//    void login() {
//        RequestParams login = new RequestParams();
//        login.put("email", "phamthainb@gmail.com");
//        login.put("password", "12345678");
//        callAPI.login(login);
//    }

    void select_part(Integer part, Integer limit) {
        seed_data(part, limit);
    }

    void seed_data(Integer part, Integer limit) {
        callAPI.getWithToken(String.format("/pratice/get_question/?part=%s&limmit=%s", part, limit), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
                String task_id = "";
                Integer stt = 0;
                Long current_id = Long.valueOf(1);
                JSONArray data;

                try {
                    task_id = response.getJSONObject("result").getString("task_id");
                    data = response.getJSONObject("result").getJSONArray("data");

                    long prev_id = 0;
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject data_item = (JSONObject) data.get(i);
                        System.out.println("data_item: " + data_item.getInt("part"));
                        QuestionView questionView = new QuestionView();

                        questionView.setQuestion_id(data_item.getInt("id"));
                        questionView.setStt(i + 1);
                        questionView.setPart(data_item.getInt("part"));
                        questionView.setData(data_item.toString());
                        questionView.setTask_id(task_id);
                        questionView.setType("practice");

                        if (prev_id != 0) {
                            questionView.setPrev_id(prev_id); // update prev_id
                        }

                        questionView.setIs_last(0);
                        if (i == data.length() - 1) {
                            questionView.setIs_last(1);
                        }

                        QuestionView question_insert = questionDao.insert(questionView);

                        long new_id = question_insert.getId();
                        if (prev_id != 0) {
                            QuestionView pre_question = questionDao.findOne(prev_id);
                            pre_question.setNext_id(new_id);
                            questionDao.update(pre_question); // update next_id
                        }
                        prev_id = new_id;
                        System.out.println("new_id: " + new_id);

                        if (i == 0) {
                            stt = question_insert.getStt();
                            current_id = question_insert.getId();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                ArrayList<QuestionView> list_q = questionDao.findAll("a38e3b99-b5b3-4a4b-b474-dea63680c7b0");

//                System.out.println("list "+ list_q.size());
                MySharedPreferences.savePreferences(context, "task_id", task_id);
                MySharedPreferences.savePreferences(context, "stt", String.valueOf(stt));
                MySharedPreferences.savePreferences(context, "current_id", String.valueOf(current_id));
                MySharedPreferences.savePreferences(context, "timer", "0.0");

                intent = new Intent(context, Part3Activity.class);
                startActivity(intent);
            }

        });
    }

    void get_test() {
        callAPI.getWithToken("/pratice/get_test", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
                String task_id = "";
                Integer stt = 0;
                Long current_id = Long.valueOf(1);
                JSONArray data;

                try {
                    task_id = response.getJSONObject("result").getString("task_id");
                    data = response.getJSONObject("result").getJSONArray("data");

                    long prev_id = 0;
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject data_item = (JSONObject) data.get(i);
                        System.out.println("data_item: " + data_item.getInt("part"));
                        QuestionView questionView = new QuestionView();

                        questionView.setQuestion_id(data_item.getInt("id"));
                        questionView.setStt(i + 1);
                        questionView.setPart(data_item.getInt("part"));
                        questionView.setData(data_item.toString());
                        questionView.setTask_id(task_id);
                        questionView.setType("test");

                        if (prev_id != 0) {
                            questionView.setPrev_id(prev_id); // update prev_id
                        }

                        questionView.setIs_last(0);
                        if (i == data.length() - 1) {
                            questionView.setIs_last(1);
                        }

                        QuestionView question_insert = questionDao.insert(questionView);

                        long new_id = question_insert.getId();
                        if (prev_id != 0) {
                            QuestionView pre_question = questionDao.findOne(prev_id);
                            pre_question.setNext_id(new_id);
                            questionDao.update(pre_question); // update next_id
                        }
                        prev_id = new_id;
                        System.out.println("new_id: " + new_id);

                        if (i == 0) {
                            stt = question_insert.getStt();
                            current_id = question_insert.getId();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                ArrayList<QuestionView> list_q = questionDao.findAll("a38e3b99-b5b3-4a4b-b474-dea63680c7b0");

//                System.out.println("list "+ list_q.size());
                MySharedPreferences.savePreferences(context, "task_id", task_id);
                MySharedPreferences.savePreferences(context, "stt", String.valueOf(stt));
                MySharedPreferences.savePreferences(context, "current_id", String.valueOf(current_id));
                MySharedPreferences.savePreferences(context, "timer", "0.0");

                intent = new Intent(context, Part3Activity.class);
                startActivity(intent);
            }

        });
    }
}
