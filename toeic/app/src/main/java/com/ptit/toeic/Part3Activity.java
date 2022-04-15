package com.ptit.toeic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Part3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part3);

        // setup toolbar
        ActionBar actionBar = getSupportActionBar();
        String toolbar_title = actionBar.getTitle().toString();
        // styles
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);

        // back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        //
        this.genQuestion();

    }
    // top
    // image (Image) or TextView as Description of main question => list question (QuestionItem)
    // => control(timer, next, prev, pause)

    void genQuestion() {
        // image
        ImageView img = findViewById(R.id.img_question);
        loadImage(img, "https://icdn.dantri.com.vn/thumb_w/640/2019/11/02/nhung-hot-girl-noi-bat-thang-10-docx-1572697558949.jpeg");
        // list question => map list question

        //
    }

    // utils
    public void loadImage (ImageView imageView, String url) {
        //start a background thread for networking
        new Thread(new Runnable() {
            public void run(){
                try {
                    //download the drawable
                    final Drawable drawable = Drawable.createFromStream((InputStream) new URL(url).getContent(), "src");
                    //edit the view in the UI thread
                    imageView.post(new Runnable() {
                        public void run() {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                } catch (IOException e) {
                    System.out.println("Error when try loading img from url");
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

class Question {
    private Integer id;
    private String tag; // listen, reading
    //     "1": "picture description",
    //     "2": "question - response",
    //     "3": "short conversations",
    //     "4": "short talks",
    //     "5": "incomplete sentences",
    //     "6": "text completion",
    //     "7": "reading comprehension",
    private String kind; // look above (7 part)

    General GeneralObject; // Image, Desc of Main question (part 1 => image)
    ArrayList< ContentItem > content = new ArrayList < ContentItem > ();
    ArrayList < Integer > correct_answers = new ArrayList <Integer> ();
    private Integer count_question;
    private String title;
    Object title_transObject;
    ArrayList < Integer > scores = new ArrayList < Integer > ();

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

    public General getGeneralObject() {
        return GeneralObject;
    }

    public void setGeneralObject(General generalObject) {
        GeneralObject = generalObject;
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

    class General {
        // data of list question
        private String audio;
        private String txt_read;
        private String txt_read_vn;
        private String image;
        private String txt_audio;
        private String txt_audio_vn;
        private Object Txt_audio_transObject;


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
    }

    class ContentItem {
        private String question;
        ArrayList < Object > answers = new ArrayList < Object > ();
        private float correctAnswer;
        private String image;
        Object explainAllObject;


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
    }
}






