package com.ptit.toeic;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.Convert;
import com.ptit.toeic.utils.MySharedPreferences;
import com.ptit.toeic.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TestingResult26 extends AppCompatActivity {
    Double time = 0.0;
    String type, task_id;
    Integer score_total = 0, score_reading = 0, score_listening = 0, current_id;
    Context context;
    ArrayList<QuestionView> list_question;
    QuestionDao questionDao;
    // find id
    TextView tx_timer, tx_total, tx_listening, tx_reading;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_result26);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Score");
        // object
        context = getApplicationContext();
        questionDao = new QuestionDao(context);
        // data
        type = MySharedPreferences.getPreferences(context, "type", ""); //// test, practice
        time = Double.valueOf(MySharedPreferences.getPreferences(context, "timer", "0.0"));
        task_id = MySharedPreferences.getPreferences(context, "task_id", "");

        if (task_id != "") {
            list_question = questionDao.findAllbyTask(task_id);
        }
        this.calculator_score();

        // find id
        tx_timer = findViewById(R.id.tv3);
        tx_total = findViewById(R.id.score_total);
        tx_listening = findViewById(R.id.score_listening);
        tx_reading = findViewById(R.id.score_reading);

        // blind data
        tx_total.setText(String.valueOf(score_total));
        tx_timer.setText(Convert.getTimerText(time));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculator_score() {
//        {"id":53,"part":1,"correct_answers":[0],"count_question":1}
        AtomicInteger r = new AtomicInteger();
        AtomicInteger l = new AtomicInteger();

        list_question.forEach(questionView -> {
            ArrayList<Integer> ans = Convert.string2Array(questionView.getAnswer()),
                    ans_correct = (ArrayList<Integer>) Convert.string2Json(questionView.getData()).get("correct_answers");
            Integer c = 0;

            for (int i = 0; i < ans.size(); i++) {
                if (ans.get(i) == ans_correct.get(i)) {
                    c += 1;
                }
            }
            if (questionView.getPart() <= 4) {
                l.addAndGet(c);
            } else {
                r.addAndGet(c);
            }
        });

        score_listening = Convert.caculator_score("listening", l.get());
        score_reading = Convert.caculator_score("reading", r.get());
        score_total = score_listening + score_reading;

        if (list_question.size() == 10) {
            System.out.println("score_total: "+score_total);
            score_total = (l.get() + r.get()) * 5;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        System.out.println("back clicked");
        Intent intent = new Intent(getApplicationContext(), PageMain.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_part, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_part_all_question: {
                Intent intent = new Intent(context, QuestionList.class);
                startActivity(intent);
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
//        return true;
    }

}