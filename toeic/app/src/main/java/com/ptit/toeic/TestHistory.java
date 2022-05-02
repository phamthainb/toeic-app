package com.ptit.toeic;

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

import com.ptit.toeic.adapter.ContentItemAdapter;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.Question;

import java.util.ArrayList;

public class TestHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page35_testhistory);

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
    }


    void genQuestion(Question question) {
        // general
        TextView textView1 = findViewById(R.id.textViewTimeResult);
        TextView textView2 = findViewById(R.id.textViewResultResult);

        // list practice history
        ListView contentItemView = findViewById(R.id.quest_list);
        ArrayList<ContentItem> contentItem = question.getContent();

//        contentItemView.setAdapter(new ContentItemAdapter(contentItem));

        System.out.println("fsdnjsahb");
    }

}
