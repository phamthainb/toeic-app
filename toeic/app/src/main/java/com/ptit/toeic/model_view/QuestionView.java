package com.ptit.toeic.model_view;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionView {
    long id;
    int stt;
    int question_id;
    int part;
    long prev_id;
    long next_id;

    int is_last; // 0 is false, 1 is true

    String task_id;
    String answer;
    String data;

    public QuestionView(long id, String task_id, int question_id, int part, long prev_id, long next_id, int is_last, int stt, String answer, String data) {
        this.id = id;
        this.task_id = task_id;
        this.question_id = question_id;
        this.part = part;
        this.prev_id = prev_id;
        this.next_id = next_id;
        this.is_last = is_last;
        this.stt = stt;
        this.answer = answer; // user pick
        this.data = data;
    }

    public QuestionView() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public long getPrev_id() {
        return prev_id;
    }

    public void setPrev_id(long prev_id) {
        this.prev_id = prev_id;
    }

    public long getNext_id() {
        return next_id;
    }

    public void setNext_id(long next_id) {
        this.next_id = next_id;
    }

    public int isIs_last() {
        return is_last;
    }

    public void setIs_last(int is_last) {
        this.is_last = is_last;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public int getIs_last() {
        return is_last;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QuestionView{" +
                "id=" + id +
                ", stt=" + stt +
                ", question_id=" + question_id +
                ", part=" + part +
                ", prev_id=" + prev_id +
                ", next_id=" + next_id +
                ", is_last=" + is_last +
                ", task_id='" + task_id + '\'' +
                ", answer='" + answer + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

