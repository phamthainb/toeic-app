package com.ptit.toeic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ptit.toeic.R;
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContentItemAdapter extends BaseAdapter {
    final ArrayList<ContentItem> contentItems;
    QuestionView questionView;
    Question question;
    Context context;
    QuestionDao questionDao;

    public ContentItemAdapter(Context context, ArrayList<ContentItem> contentItems, QuestionView questionView, Question question) {
        this.contentItems = contentItems;
        this.context = context;
        this.questionView = questionView;
        this.questionDao = new QuestionDao(context);
        this.question = question;
    }

    @Override
    public int getCount() {
        return contentItems.size();
    }

    @Override
    public Object getItem(int i) {
        return contentItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1;
        if (view == null) {
            view1 = ConstraintLayout.inflate(this.context, R.layout.part3_question, null);
            // bind data to view
            ContentItem q = (ContentItem) getItem(i);
            // stt, title, desc
            String title = question.getTitle();
            if (question.getPart() == 2 || question.getPart() == 3 || question.getPart() == 4) {
                title = "Listen and Pick your answer";
            }
            if (question.getPart() == 5) {
                title = contentItems.get(i).getQuestion();
            }
            if (question.getPart() == 7) {
                title = contentItems.get(i).getQuestion();
            }
            ((TextView) view1.findViewById(R.id.quest_index)).setText("Question " + String.valueOf(questionView.getStt()) + "." + (i + 1));
            ((TextView) view1.findViewById(R.id.quest_content)).setText(title);
            // map list answer
            ArrayList<Object> listAnswer = q.getAnswers();
            RadioGroup listAnswerView = view1.findViewById(R.id.quest_list_answer);
            listAnswerView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int j) {
                    String an = questionView.getAnswer();
                    try {
                        ArrayList<Integer> an_new = new ObjectMapper().reader(List.class).readValue(an);
                        an_new.set(i, radioGroup.getCheckedRadioButtonId());
                        questionView.setAnswer(String.valueOf(an_new));
                        System.out.println("Pick Answer: " + an_new);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    // update when pick answer
                    questionDao.update(questionView);
                }
            });

            // A, B, C, D
            if (listAnswerView.getChildCount() <= listAnswer.size()) {
                for (int n = 0; n < listAnswer.size(); n++) {
                    RadioButton rb = new RadioButton(view1.getContext());
                    rb.setText(String.valueOf(listAnswer.get(n)));
                    rb.setId(n);
                    listAnswerView.addView(rb);
                }
            }
        } else {
            view1 = view;
            return view1;
        }

        return view1;
    }
}
