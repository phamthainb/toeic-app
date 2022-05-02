package com.ptit.toeic;

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
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PageMain extends AppCompatActivity {
    CallAPI callAPI;
    Question question;
    QuestionDao questionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout contrainL1 = findViewById(R.id.constraintLayout2);
        ConstraintLayout contrainL2 = findViewById(R.id.constraintLayout3);
        ConstraintLayout contrainL3 = findViewById(R.id.constraintLayout4);
        ConstraintLayout contrainL4 = findViewById(R.id.constraintLayout5);
        ConstraintLayout contrainL5 = findViewById(R.id.constraintLayout6);
        ConstraintLayout contrainL6 = findViewById(R.id.constraintLayout7);
        ConstraintLayout contrainL7 = findViewById(R.id.constraintLayout8);
        ConstraintLayout contrainL8 = findViewById(R.id.constraintLayout9);

        Intent intent = new Intent(this, Part3Activity.class);

        contrainL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("hiiiiiiii");

                callAPI = new CallAPI(getApplicationContext());
                questionDao = new QuestionDao(getApplicationContext());

                callAPI.getWithToken("/question" , null, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        System.out.println("res:");
                        System.out.println(response);
                        try {
                            String task_id = response.getJSONObject("result").getString("task_id");
                            JSONArray data = response.getJSONObject("result").getJSONArray("data");

                            long prev_id = 0;
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject data_item = (JSONObject) data.get(i);
                                System.out.println("data_item: "+ data_item.getInt("part"));
                                QuestionView questionView = new QuestionView();

                                questionView.setQuestion_id(data_item.getInt("id"));
                                questionView.setStt(i+1);
                                questionView.setPart(data_item.getInt("part"));
                                questionView.setData(data_item.toString());
                                questionView.setTask_id(task_id);

                                if(prev_id != 0){
                                    questionView.setPrev_id(prev_id); // update prev_id
                                }

                                questionView.setIs_last(0);
                                if(i == data.length() - 1){
                                    questionView.setIs_last(1);
                                }

                                QuestionView question_insert = questionDao.insert(questionView);

                                long new_id = question_insert.getId();
                                if(prev_id != 0){
                                    QuestionView pre_question = questionDao.findOne(prev_id);
                                    pre_question.setNext_id(new_id);
                                    questionDao.update(pre_question); // update next_id
                                }
                                prev_id = new_id;
                                System.out.println("new_id: "+new_id);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayList<QuestionView> list_q = questionDao.findAll("de700c09-dd7c-4d46-b6cf-c0ad40c27a2e");

                        System.out.println("list "+ list_q.size());

                        startActivity(intent);
                    }

                });
            }
        });

    }
}
