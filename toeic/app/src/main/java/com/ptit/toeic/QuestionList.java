package com.ptit.toeic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.ptit.toeic.adapter.QuestionListAdapter;
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.MySharedPreferences;
import java.util.ArrayList;

public class QuestionList extends AppCompatActivity {
    ArrayList<QuestionView> list;
    QuestionDao questionDao;
    Context context;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page11_allquestion);
        context = this.getApplicationContext();
        questionDao = new QuestionDao(this.getApplicationContext());
        listView = findViewById(R.id.listViewChildrenQuesion);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        ActionBar actionBar = getSupportActionBar();
        String toolbar_title = "All question";
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);
        actionBar.setDisplayHomeAsUpEnabled(true);

        String task = MySharedPreferences.getPreferences(context, "task_id", "");
        list = questionDao.findAllbyTask(task);
        listView.setAdapter(new QuestionListAdapter(list));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(context, Part3Activity.class);
        startActivity(intent);
        return true;
    }
}
