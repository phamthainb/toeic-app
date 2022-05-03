package com.ptit.toeic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ptit.toeic.R;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.Convert;

import java.util.ArrayList;

public class QuestionListAdapter extends BaseAdapter {
    final ArrayList<QuestionView> contentItems;

    public QuestionListAdapter(ArrayList<QuestionView> contentItems) {
        this.contentItems = contentItems;
    }

    @Override
    public int getCount() {
        return contentItems.size();
    }

    @Override
    public QuestionView getItem(int i) {
        return contentItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1;
        LinearLayout listView;

        if (view == null) {
            view1 = View.inflate(viewGroup.getContext(), R.layout.page11_children_question, null);
        } else {
            view1 = view;
        }

        ((TextView) view1.findViewById(R.id.textViewidchildrenquestionallchildrenpage11)).setText("Question " + String.valueOf(i + 1));
//        System.out.println("size answer: " + this.getItem(i).getAnswer()); //
//        System.out.println("size corret: " + this.getItem(i).getData()); //

        ArrayList<Object> answer = Convert.string2Array(this.getItem(i).getAnswer());
        ArrayList<Object> correct_answer = (ArrayList<Object>) Convert.string2Json(this.getItem(i).getData()).get("correct_answers");

        Integer temp = 0;
        for (int j = 0; j < answer.size(); j++) {
            if (answer.get(j) == correct_answer.get(j)) {
                temp += 1;
            }
        }

        ((TextView) view1.findViewById(R.id.textViewABChildren)).setText(temp + "/" + answer.size());


//        ArrayList<MyData> listChildren = new ArrayList<MyData>();

//        System.out.println("listChildren: " + listChildren);

//        for (int j = 0; j < answer.size(); j++) {
//            MyData mydata = new MyData((Integer) answer.get(j), (Integer) correct_answer.get(j));
//            listChildren.add(mydata);
//        }
//
//        System.out.println("listChildren: " + listChildren);
//        System.out.println("answer: " + answer);
//        System.out.println("correct_answer: " + correct_answer);
//        System.out.println("Convert.string2Json(this.getItem(i).getData()): " + Convert.string2Json(this.getItem(i).getData()));
//
//        listView = view1.findViewById(R.id.listquestionchildrenchildren);
//        if (listChildren.size() > 1) {
////            listView.setAdapter(new QuestionListChildrenAdapter(listChildren));
//            for (int j = 0; j < listChildren.size(); j++) {
//                TextView t = new TextView(view.getContext());
//                t.setText(String.valueOf(listChildren.get(j).answer));
//                listView.addView(t);
//            }
//        } else {
//            listView.setVisibility(View.INVISIBLE);
////            listView.
//        }

        return view1;
    }

    public class MyData {
        Integer answer;
        Integer correct_answer;

        public MyData(Integer answer, Integer correct_answer) {
            this.answer = answer;
            this.correct_answer = correct_answer;
        }

        public Integer getAnswer() {
            return answer;
        }

        public void setAnswer(Integer answer) {
            this.answer = answer;
        }

        public Integer getCorrect_answer() {
            return correct_answer;
        }

        public void setCorrect_answer(Integer correct_answer) {
            this.correct_answer = correct_answer;
        }
    }
}
