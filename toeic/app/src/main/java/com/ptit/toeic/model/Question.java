package com.ptit.toeic.model;

import java.util.ArrayList;

public class Question {
    private Integer id;
    private Integer part;
    private String tag; // listen, reading
    //     "1": "picture description",
    //     "2": "question - response",
    //     "3": "short conversations",
    //     "4": "short talks",
    //     "5": "incomplete sentences",
    //     "6": "text completion",
    //     "7": "reading comprehension",
    private String kind; // look above (7 part)

    General general; // Image, Desc of Main question (part 1 => image)
    ArrayList< ContentItem > content = new ArrayList < ContentItem > ();
    ArrayList < Integer > correct_answers = new ArrayList <Integer> ();
    private Integer count_question;
    private String title;
    Object title_transObject;
    ArrayList < Integer > scores = new ArrayList < Integer > ();


    public Question() {
    }

    public Question(Integer id, Integer part, String tag, String kind, General general, ArrayList<ContentItem> content, ArrayList<Integer> correct_answers, Integer count_question, String title, Object title_transObject, ArrayList<Integer> scores) {
        this.id = id;
        this.part = part;
        this.tag = tag;
        this.kind = kind;
        this.general = general;
        this.content = content;
        this.correct_answers = correct_answers;
        this.count_question = count_question;
        this.title = title;
        this.title_transObject = title_transObject;
        this.scores = scores;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public ArrayList<ContentItem> getContent() {
        return content;
    }

    public void setContent(ArrayList<ContentItem> content) {
        this.content = content;
    }

    public ArrayList<Integer> getCorrect_answers() {
        return correct_answers;
    }

    public void setCorrect_answers(ArrayList<Integer> correct_answers) {
        this.correct_answers = correct_answers;
    }

    public Integer getCount_question() {
        return count_question;
    }

    public void setCount_question(Integer count_question) {
        this.count_question = count_question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getTitle_transObject() {
        return title_transObject;
    }

    public void setTitle_transObject(Object title_transObject) {
        this.title_transObject = title_transObject;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", part=" + part +
                ", tag='" + tag + '\'' +
                ", kind='" + kind + '\'' +
                ", general=" + general +
                ", content=" + content +
                ", correct_answers=" + correct_answers +
                ", count_question=" + count_question +
                ", title='" + title + '\'' +
                ", title_transObject=" + title_transObject +
                ", scores=" + scores +
                '}';
    }
}

