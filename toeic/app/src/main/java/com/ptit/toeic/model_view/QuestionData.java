package com.ptit.toeic.model_view;

import org.json.JSONArray;

public class QuestionData {
    long id;
    int part;
    JSONArray correct_answers;
    int count_question;

    public QuestionData(long id, int part, JSONArray correct_answers, int count_question) {
        this.id = id;
        this.part = part;
        this.correct_answers = correct_answers;
        this.count_question = count_question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public JSONArray getCorrect_answers() {
        return correct_answers;
    }

    public void setCorrect_answers(JSONArray correct_answers) {
        this.correct_answers = correct_answers;
    }

    public int getCount_question() {
        return count_question;
    }

    public void setCount_question(int count_question) {
        this.count_question = count_question;
    }
}
