package com.ptit.toeic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ptit.toeic.adapter.ContentItemAdapter;
import com.ptit.toeic.adapter.PracticeHistoryAdapter;
import com.ptit.toeic.adapter.QuestionListAdapter;
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.Convert;
import com.ptit.toeic.utils.MySharedPreferences;
import com.ptit.toeic.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class PracticeHistoryActivity extends AppCompatActivity {
    ArrayList<QuestionView> list;
    Question question;
    QuestionDao questionDao;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page34_practicehistory);
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

        Integer part1 = 0, part2 = 0, part3 = 0, part4 = 0, part5 = 0, part6 = 0, part7 = 0;
        Integer part1Correct = 0, part2Correct = 0, part3Correct = 0, part4Correct = 0, part5Correct = 0, part6Correct = 0, part7Correct = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPart() == 1) {
                part1 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part1Correct += 1;
                    }
                }
            } else if (list.get(i).getPart() == 2) {
                part2 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part2Correct += 1;
                    }
                }
            } else if (list.get(i).getPart() == 3) {
                part3 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part3Correct += 1;
                    }
                }
            } else if (list.get(i).getPart() == 4) {
                part4 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part4Correct += 1;
                    }
                }
            } else if (list.get(i).getPart() == 5) {
                part5 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part5Correct += 1;
                    }
                }
            } else if (list.get(i).getPart() == 6) {
                part6 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part6Correct += 1;
                    }
                }
            } else if (list.get(i).getPart() == 7) {
                part7 += 1;
                ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(list.get(i).getData()).get("correct_answers");
                ArrayList<Object> answer = Convert.string2Array(list.get(i).getAnswer());

                for (int j = 0; j < answer.size(); j++) {
                    if (answer.get(j) == correct_answer.get(j)) {
                        part7Correct += 1;
                    }
                }
            }
        }

        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList1)).setText("True: " + part1Correct + "/" + part1 + ", False: " + String.valueOf((part1 - part1Correct)) + "/" + part1);
        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList2)).setText("True: " + part2Correct + "/" + part2 + ", False: " + String.valueOf((part2 - part2Correct)) + "/" + part2);
        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList3)).setText("True: " + part3Correct + "/" + part3 + ", False: " + String.valueOf((part3 - part3Correct)) + "/" + part3);
        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList4)).setText("True: " + part4Correct + "/" + part4 + ", False: " + String.valueOf((part4 - part4Correct)) + "/" + part4);
        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList5)).setText("True: " + part5Correct + "/" + part5 + ", False: " + String.valueOf((part5 - part5Correct)) + "/" + part5);
        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList6)).setText("True: " + part6Correct + "/" + part6 + ", False: " + String.valueOf((part6 - part6Correct)) + "/" + part6);
        ((TextView) this.findViewById(R.id.textViewResultResultPracticehistoryPartList7)).setText("True: " + part7Correct + "/" + part7 + ", False: " + String.valueOf((part7 - part7Correct)) + "/" + part7);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(context, PageMain.class);
        startActivity(intent);
        return true;
    }
}
