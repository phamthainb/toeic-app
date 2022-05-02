package com.ptit.toeic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ptit.toeic.R;
import com.ptit.toeic.model.ContentItem;

import java.util.ArrayList;

public class QuestionListAdapter extends BaseAdapter {
    final ArrayList<ContentItem> contentItems;

    public QuestionListAdapter(ArrayList<ContentItem> contentItems) {
        this.contentItems = contentItems;
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
        if(view == null){
            view1 = View.inflate(viewGroup.getContext(), R.layout.part3_question, null);
        }else{
            view1 = view;
        }

        // bind data to view
        ContentItem q = (ContentItem) getItem(i);
        System.out.println(q.getAnswers().getClass().getName());
        ((TextView) view1.findViewById(R.id.quest_index)).setText("Question " + String.valueOf(i + 1));
        ((TextView) view1.findViewById(R.id.quest_content)).setText(q.getQuestion());

        // map list answer
        ArrayList<Object> listAnswer = q.getAnswers();
        RadioGroup listAnswerView = view1.findViewById(R.id.quest_list_answer);
        listAnswerView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                System.out.println("Clicked : "+ radioGroup.getCheckedRadioButtonId());
            }
        });
        for (int n = 0; n < listAnswer.size(); n++) {
            RadioButton rb = new RadioButton(view1.getContext());
            rb.setText(String.valueOf(listAnswer.get(n)));
            rb.setId(n);
            listAnswerView.addView(rb);
        }

        return view1;
    }
}
