package com.ptit.toeic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ptit.toeic.R;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;

import java.util.ArrayList;

public class PracticeHistoryAdapter extends BaseAdapter {
    final ArrayList<QuestionView> contentItems;

    public PracticeHistoryAdapter(ArrayList<QuestionView> contentItems) {
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
            view1 = View.inflate(viewGroup.getContext(), R.layout.page34_practicehistorychildren, null);
        }else{
            view1 = view;
        }

        ((TextView) view1.findViewById(R.id.textViewPracticehistoryPartList)).setText("Part " + String.valueOf(i + 1));

        for (int j = 0; j < contentItems.size(); j++) {

        }
        return view1;
    }
}
