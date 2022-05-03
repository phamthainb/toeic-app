package com.ptit.toeic;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TestingResultActivity extends AppCompatActivity {
    Double time = 0.0;
    String type, task_id;
    Integer score_total = 0, score_reading = 0, score_listening = 0, current_id;
    Context context;
    ArrayList<QuestionView> list_question;
    QuestionDao questionDao;
    ArrayList<Integer> sc = new ArrayList<Integer>(14);

    // find id
    TextView tx_timer, tx_total, tx_listening, tx_reading, tx_num_listening, tx_num_reading;

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
        tx_num_listening = findViewById(R.id.number_listening);
        tx_num_reading = findViewById(R.id.number_reading);

        // blind data
        tx_total.setText(String.valueOf(score_total));
        tx_timer.setText(Convert.getTimerText(time));
        tx_listening.setText(String.valueOf(score_listening));
        tx_reading.setText(String.valueOf(score_reading));

        tx_num_listening.setText(
                String.valueOf(sc.get(0) + sc.get(2) + sc.get(4) + sc.get(6)) +
                        "/" +
                        String.valueOf(sc.get(1) + sc.get(3) + sc.get(5) + sc.get(7)));
        tx_num_reading.setText(
                String.valueOf(sc.get(8) + sc.get(10) + sc.get(12)) +
                        "/" +
                        String.valueOf(sc.get(9) + sc.get(11) + sc.get(13)));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculator_score() {
//        {"id":53,"part":1,"correct_answers":[0],"count_question":1}
        AtomicInteger r = new AtomicInteger();
        AtomicInteger l = new AtomicInteger();

        for (int i = 0; i < 14; i++) {
            sc.add(0);
        }

        list_question.forEach(questionView -> {
            ArrayList<Integer> ans = Convert.string2Array(questionView.getAnswer()),
                    ans_correct = (ArrayList<Integer>) Convert.string2Json(questionView.getData()).get("correct_answers");
            Integer c = 0;

            for (int i = 0; i < ans.size(); i++) {
                if (ans.get(i) == ans_correct.get(i)) {
                    c += 1;
                }
            }

            switch (questionView.getPart()) {
                case 1: {
                    sc.set(0, sc.get(0) + c);
                    sc.set(1, sc.get(1) + ans.size());
                    break;
                }
                case 2: {
                    sc.set(2, sc.get(2) + c);
                    sc.set(3, sc.get(3) + ans.size());
                    break;
                }
                case 3: {
                    sc.set(4, sc.get(4) + c);
                    sc.set(5, sc.get(5) + ans.size());
                    break;
                }
                case 4: {
                    sc.set(6, sc.get(6) + c);
                    sc.set(7, sc.get(7) + ans.size());
                    break;
                }
                case 5: {
                    sc.set(8, sc.get(8) + c);
                    sc.set(9, sc.get(9) + ans.size());
                    break;
                }
                case 6: {
                    sc.set(10, sc.get(10) + c);
                    sc.set(11, sc.get(11) + ans.size());
                    break;
                }
                case 7: {
                    sc.set(12, sc.get(12) + c);
                    sc.set(13, sc.get(13) + ans.size());
                    break;
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
            System.out.println("score_total: " + score_total);
            score_total = (l.get() + r.get()) * 5;
            score_listening = l.get() * 5;
            score_reading = r.get() * 5;
        }

        ((TextView) findViewById(R.id.score_part1)).setText("Part 1: (" + sc.get(0) + "/" + sc.get(1) + ")");
        ((TextView) findViewById(R.id.score_part2)).setText("Part 2: (" + sc.get(2) + "/" + sc.get(3) + ")");
        ((TextView) findViewById(R.id.score_part3)).setText("Part 3: (" + sc.get(4) + "/" + sc.get(5) + ")");
        ((TextView) findViewById(R.id.score_part4)).setText("Part 4: (" + sc.get(6) + "/" + sc.get(7) + ")");
        ((TextView) findViewById(R.id.score_part5)).setText("Part 5: (" + sc.get(8) + "/" + sc.get(9) + ")");
        ((TextView) findViewById(R.id.score_part6)).setText("Part 6: (" + sc.get(10) + "/" + sc.get(11) + ")");
        ((TextView) findViewById(R.id.score_part7)).setText("Part 7: (" + sc.get(12) + "/" + sc.get(13) + ")");
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
                Intent intent = new Intent(context, QuestionListActivity.class);
                startActivity(intent);
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
//        return true;
    }

}