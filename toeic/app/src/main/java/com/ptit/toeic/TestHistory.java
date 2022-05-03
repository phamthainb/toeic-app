package com.ptit.toeic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.Convert;

import java.util.ArrayList;

public class TestHistory extends AppCompatActivity {
    ArrayList<QuestionView> list;
    Question question;
    QuestionDao questionDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page35_testhistory);
        context = this.getApplicationContext();
        questionDao = new QuestionDao(this.getApplicationContext());

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String toolbar_title = "Practive History";
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = questionDao.findAll();
        System.out.println("list: " + list);
//        listView.setAdapter(new PracticeHistoryAdapter(list));


        Integer part1 = 0;
        Integer part1Correct = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType() == "test") {
                part1 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part1Correct += 1;
                    }
                }
            }
        }

        ((TextView) this.findViewById(R.id.textViewResultResultPartTest)).setText("True: " + part1Correct + "/" + part1 + ", False: " + String.valueOf((part1 - part1Correct)) + "/" + part1);
    }
}
