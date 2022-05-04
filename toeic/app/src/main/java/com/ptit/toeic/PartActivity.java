package com.ptit.toeic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.ptit.toeic.adapter.ContentItemAdapter;
import com.ptit.toeic.dao.QuestionDao;
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.CallAPI;
import com.ptit.toeic.utils.Convert;
import com.ptit.toeic.utils.MySharedPreferences;
import com.ptit.toeic.utils.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class PartActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectAll()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());

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

        btn_hide = findViewById(R.id.quest_hide);

        int id = Integer.parseInt(MySharedPreferences.getPreferences(context, "current_id", "1"));
        time = Double.valueOf(MySharedPreferences.getPreferences(context, "timer", "0.0"));

        // get question in database local
        questionView = questionDao.findOne(id);

        callAPI.getWithToken("/question/" + questionView.getQuestion_id(), null, new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                question = gson.fromJson(String.valueOf(response), Question.class);
                System.out.println("response: " + question.toString());

                // gen data of question
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
        System.out.println("back clicked");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_part, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_part_all_question: {
                Intent intent = new Intent(context, QuestionListActivity.class);
                startActivity(intent);
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
//        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void genQuestion(Question question) {
//        System.out.println("genQuestion: "+ question.toString());
        Integer part = question.getPart();
        // action bar
        String t = "Part " + question.getPart() + " - Number " + questionView.getStt();
        actionBar.setTitle(t.toUpperCase(Locale.ROOT));
        // timer
        this.startTimer();
        // general
        switch (part) {
            case 1: {
                quest_desc.setVisibility(View.INVISIBLE);
                new Utils(context).loadImage(quest_img, question.getContent().get(0).getImage());
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
        ContentItemAdapter contentItemAdapter = new ContentItemAdapter(this.getApplicationContext(), contentItem, questionView, question);
        contentItemView.setAdapter(contentItemAdapter);

        // control: prev, next, pause, audio process
        if (question.getTag().equals("listen")) {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                String audio_url = question.getGeneral().getAudio();
                System.out.println("audio_url: " + audio_url);
                mediaPlayer.setDataSource(audio_url);
                mediaPlayer.prepare();

                System.out.println("mediaPlayer: " + mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click play");
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
                Intent intent = new Intent(context, TestingResultActivity.class);
                startActivity(intent);
                System.out.println("submit");
            }
        });

        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
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
        Intent intent = new Intent(context, PartActivity.class);
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
                        timer_text.setText(Convert.getTimerText(time));
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
                oTime = 0;
                sTime = 0;
                eTime = 0;
            }
            hdlr.postDelayed(this, 100);
        }
    };
}







