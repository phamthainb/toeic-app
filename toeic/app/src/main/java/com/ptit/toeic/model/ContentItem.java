package com.ptit.toeic.model;

import java.util.ArrayList;

public class ContentItem {
    private String question;
    ArrayList<Object> answers = new ArrayList<Object>();
    private float correctAnswer;
    private String image;
    Object explainAllObject;

    public ContentItem() {
    }

    public ContentItem(String question, ArrayList<Object> answers, float correctAnswer, String image, Object explainAllObject) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.image = image;
        this.explainAllObject = explainAllObject;
    }

    // Getter Methods
    public String getQuestion() {
        return question;
    }

    public float getCorrectAnswer() {
        return correctAnswer;
    }

    public String getImage() {
        return image;
    }

    public Object getExplainAll() {
        return explainAllObject;
    }

    // Setter Methods

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(float correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setExplainAll(Object explainAllObject) {
        this.explainAllObject = explainAllObject;
    }

    @Override
    public String toString() {
        return "ContentItem{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", correctAnswer=" + correctAnswer +
                ", image='" + image + '\'' +
                ", explainAllObject=" + explainAllObject +
                '}';
    }
}
