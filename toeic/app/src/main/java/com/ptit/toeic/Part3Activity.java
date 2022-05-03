package com.ptit.toeic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.ptit.toeic.adapter.ContentItemAdapter;
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.General;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.CallAPI;
import com.ptit.toeic.utils.MySharedPreferences;
import com.ptit.toeic.utils.URLImageParser;
import com.ptit.toeic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class Part3Activity extends AppCompatActivity {
    CallAPI callAPI;
    Question question;
    QuestionDao questionDao;
    QuestionView questionView;
    Boolean audio_play = false;
    private static int oTime = 0, sTime = 0, eTime = 0;
    Context context;

    // Object
    Handler hdlr = new Handler();
    MediaPlayer mediaPlayer = new MediaPlayer();
    Gson gson = new Gson();
    ActionBar actionBar;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    // view element
    ImageButton btn_pause, btn_play, btn_next, btn_prev, btn_submit, btn_hide;
    SeekBar seekBar;
    ImageView quest_img;
    TextView quest_desc, timer_text;
    ListView contentItemView;

//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part3_list_question);

        // object
        context = this.getApplicationContext();
        timer = new Timer();
        callAPI = new CallAPI(this.getApplicationContext());
        questionDao = new QuestionDao(this.getApplicationContext());

        // findId
        timer_text = findViewById(R.id.time_left);

        btn_pause = findViewById(R.id.btn_quest_pause);
        btn_pause.setEnabled(false);

        btn_next = findViewById(R.id.btn_quest_next);
        btn_next.setEnabled(false);
        btn_prev = findViewById(R.id.btn_quest_prev);
        btn_prev.setEnabled(false);

        btn_play = findViewById(R.id.btn_quest_play);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setClickable(true);
        quest_img = findViewById(R.id.quest_img);

        quest_desc = findViewById(R.id.quest_desc);
        quest_desc.setMovementMethod(new ScrollingMovementMethod());

        contentItemView = findViewById(R.id.quest_list);

        btn_submit = findViewById(R.id.quest_submit);
        btn_submit.setEnabled(false);

//        login();
//        seed_data(1, 10);
//        seed_data(2, 10);
//        seed_data(3, 10);
//        seed_data(4, 10);
//        seed_data(5, 10);
//        seed_data(6, 10);
//        seed_data(7, 10);

//        ArrayList<QuestionView> s = questionDao.findAll();
//        System.out.println(s.size());
        // api
//        MySharedPreferences.savePreferences(this.getApplicationContext(), "task_id", "30d32ade-437a-436c-9b24-ee0ce3a2c661");
//        MySharedPreferences.savePreferences(this.getApplicationContext(), "stt", "1");
//        MySharedPreferences.savePreferences(this.getApplicationContext(), "current_id", "3");
//        MySharedPreferences.savePreferences(this.getApplicationContext(), "timer", "1");

//        int stt = Integer.parseInt(MySharedPreferences.getPreferences(this.getApplicationContext(), "stt"));
//        String task = MySharedPreferences.getPreferences(this.getApplicationContext(), "task_id");

//        this.startTimer();

        int id = Integer.parseInt(MySharedPreferences.getPreferences(context, "current_id", "1"));
        time = Double.valueOf(MySharedPreferences.getPreferences(context, "timer", "0.0"));

//        System.out.println("stt: "+ stt);
        questionView = questionDao.findOne(id);

        callAPI.getWithToken("/question/" + questionView.getQuestion_id(), null, new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                question = gson.fromJson(String.valueOf(response), Question.class);
                System.out.println("response: " + question.toString());
                genQuestion(question);
            }

        });

        // setup toolbar
        actionBar = getSupportActionBar();
        String toolbar_title = "Part";
        SpannableString ss = new SpannableString(toolbar_title);
        ss.setSpan(
                new ForegroundColorSpan(Color.parseColor("#00B7D1")),
                0,
                toolbar_title.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        actionBar.setTitle(ss);

        // back button
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(context, PageMain.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void genQuestion(Question question) {
//        System.out.println("genQuestion: "+ question.toString());
        Integer part = question.getPart();
        // action bar
        String t = "Part " + question.getPart() + " - " + question.getKind();
        actionBar.setTitle(t.toUpperCase(Locale.ROOT));
        // timer
        this.startTimer();
        // general
        switch (part) {
            case 1: {
                quest_desc.setVisibility(View.INVISIBLE);
                Utils.loadImage(quest_img, question.getContent().get(0).getImage());
                break;
            }
            case 2: {
                quest_img.setVisibility(View.INVISIBLE);
                quest_img.setMaxHeight(0);
                quest_desc.setText(question.getTitle());
                break;
            }

            case 3:
            case 4: {
                quest_desc.setText(question.getTitle());
                break;
            }
            case 5: {
                quest_desc.setText(question.getTitle());
                seekBar.setVisibility(View.INVISIBLE);
                btn_pause.setVisibility(View.INVISIBLE);
                btn_play.setVisibility(View.INVISIBLE);

                break;
            }
            case 6:
            case 7: {
                seekBar.setVisibility(View.INVISIBLE);
                btn_pause.setVisibility(View.INVISIBLE);
                btn_play.setVisibility(View.INVISIBLE);

//                URLImageParser p = new URLImageParser(quest_desc, this);
//                Spanned htmlSpan = Html.fromHtml(question.getGeneral().getTxt_read(), p, null);
                Spanned htmlSpan = HtmlCompat.fromHtml(question.getGeneral().getTxt_read(), HtmlCompat.FROM_HTML_MODE_COMPACT);
                quest_desc.setMovementMethod(LinkMovementMethod.getInstance());
                quest_desc.setText(htmlSpan);
                quest_desc.setHeight(400);
                break;
            }
            default:
                break;
        }

        // list question
        ArrayList<ContentItem> contentItem = question.getContent();
        contentItemView.setAdapter(new ContentItemAdapter(this.getApplicationContext(), contentItem, questionView, question));

        // control: prev, next, pause, audio process
        if (question.getTag().equals("listen")) {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(question.getGeneral().getAudio());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    System.out.println("click play");
                    mediaPlayer.start();
                    audio_play = true;

                    eTime = mediaPlayer.getDuration();
                    sTime = mediaPlayer.getCurrentPosition();

                    if (oTime == 0) {
                        seekBar.setMax(eTime);
                        oTime = 1;
                    }
                    seekBar.setProgress(sTime);
                    hdlr.postDelayed(UpdateAudioTime, 100);

                    btn_pause.setEnabled(true);
                    btn_play.setEnabled(false);
                }
            });

            btn_pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click pause");
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        audio_play = false;

                        btn_play.setEnabled(true);
                        btn_pause.setEnabled(false);
                    }
                }
            });
        }

        // button control: next, prev
        if (questionView.isIs_last() != 1) {
            btn_next.setEnabled(true);
        } else {
            btn_submit.setEnabled(true);
        }

        if (questionView.getStt() > 1) {
            btn_prev.setEnabled(true);
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to(questionView.getNext_id());
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to(questionView.getPrev_id());
            }
        });

        // button submit
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("submit");
            }
        });
    }

    void go_to(long id) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            oTime = 0;
            sTime = 0;
            eTime = 0;
        }
        MySharedPreferences.savePreferences(context, "timer", String.valueOf(time));
        MySharedPreferences.savePreferences(context, "current_id", String.valueOf(id));
        Intent intent = new Intent(context, Part3Activity.class);
        startActivity(intent);
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timer_text.setText(getTimerText(time));
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    // utils
    private Runnable UpdateAudioTime = new Runnable() {
        @Override
        public void run() {
            sTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(sTime);
//            System.out.println(sTime+ " > " + eTime);
            if (sTime >= eTime) {
                btn_play.setEnabled(true);
                btn_pause.setEnabled(false);
            }
            hdlr.postDelayed(this, 100);
        }
    };

    private String getTimerText(Double time) {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    void login() {
        RequestParams login = new RequestParams();
        login.put("email", "phamthainb@gmail.com");
        login.put("password", "12345678");
        callAPI.login(login);
    }

    void seed_data(Integer part, Integer limit) {
        callAPI.getWithToken(String.format("/pratice/get_question/?part=%s&limmit=%s", part, limit), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
                try {
                    String task_id = response.getJSONObject("result").getString("task_id");
                    JSONArray data = response.getJSONObject("result").getJSONArray("data");

                    long prev_id = 0;
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject data_item = (JSONObject) data.get(i);
                        System.out.println("data_item: " + data_item.getInt("part"));
                        QuestionView questionView = new QuestionView();

                        questionView.setQuestion_id(data_item.getInt("id"));
                        questionView.setStt(i + 1);
                        questionView.setPart(data_item.getInt("part"));
                        questionView.setData(data_item.toString());
                        questionView.setTask_id(task_id);

                        if (prev_id != 0) {
                            questionView.setPrev_id(prev_id); // update prev_id
                        }

                        questionView.setIs_last(0);
                        if (i == data.length() - 1) {
                            questionView.setIs_last(1);
                        }

                        QuestionView question_insert = questionDao.insert(questionView);

                        long new_id = question_insert.getId();
                        if (prev_id != 0) {
                            QuestionView pre_question = questionDao.findOne(prev_id);
                            pre_question.setNext_id(new_id);
                            questionDao.update(pre_question); // update next_id
                        }
                        prev_id = new_id;
                        System.out.println("new_id: " + new_id);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                ArrayList<QuestionView> list_q = questionDao.findAll("a38e3b99-b5b3-4a4b-b474-dea63680c7b0");

//                System.out.println("list "+ list_q.size());
            }

        });
    }

}







