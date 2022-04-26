package com.ptit.toeic.model;

public class General {
    // data of list question
    private String audio;
    private String txt_read;
    private String txt_read_vn;
    private String image;
    private String txt_audio;
    private String txt_audio_vn;
    private Object Txt_audio_transObject;

    public General(String audio, String txt_read, String txt_read_vn, String image, String txt_audio, String txt_audio_vn, Object txt_audio_transObject) {
        this.audio = audio;
        this.txt_read = txt_read;
        this.txt_read_vn = txt_read_vn;
        this.image = image;
        this.txt_audio = txt_audio;
        this.txt_audio_vn = txt_audio_vn;
        Txt_audio_transObject = txt_audio_transObject;
    }

    public General() {
    }

    // Getter Methods
    public String getAudio() {
        return audio;
    }

    public String getTxt_read() {
        return txt_read;
    }

    public String getTxt_read_vn() {
        return txt_read_vn;
    }

    public String getImage() {
        return image;
    }

    public String getTxt_audio() {
        return txt_audio;
    }

    public String getTxt_audio_vn() {
        return txt_audio_vn;
    }

    public Object getTxt_audio_trans() {
        return Txt_audio_transObject;
    }

    // Setter Methods

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public void setTxt_read(String txt_read) {
        this.txt_read = txt_read;
    }

    public void setTxt_read_vn(String txt_read_vn) {
        this.txt_read_vn = txt_read_vn;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTxt_audio(String txt_audio) {
        this.txt_audio = txt_audio;
    }

    public void setTxt_audio_vn(String txt_audio_vn) {
        this.txt_audio_vn = txt_audio_vn;
    }

    public void setTxt_audio_trans(Object txt_audio_transObject) {
        this.Txt_audio_transObject = txt_audio_transObject;
    }

    @Override
    public String toString() {
        return "General{" +
                "audio='" + audio + '\'' +
                ", txt_read='" + txt_read + '\'' +
                ", txt_read_vn='" + txt_read_vn + '\'' +
                ", image='" + image + '\'' +
                ", txt_audio='" + txt_audio + '\'' +
                ", txt_audio_vn='" + txt_audio_vn + '\'' +
                ", Txt_audio_transObject=" + Txt_audio_transObject +
                '}';
    }
}
