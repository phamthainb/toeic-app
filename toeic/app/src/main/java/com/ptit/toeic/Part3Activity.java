package com.ptit.toeic;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.ptit.toeic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Part3Activity extends AppCompatActivity {
    CallAPI callAPI;
    Question question;
    QuestionDao questionDao;
    Boolean audio_play = true;

    // view
    Handler hdlr = new Handler();
    MediaPlayer mediaPlayer = new MediaPlayer();

//

//    Button btn_pause = findViewById(R.id.btn_quest_pause);
//    SeekBar seekBar = findViewById(R.id.seekBar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.part3_list_question);

        // api

      int stt = Integer.parseInt(MySharedPreferences.getPreferences(getApplicationContext(), "stt"));
      String task = MySharedPreferences.getPreferences(getApplicationContext(), "task_id");

//        callAPI = new CallAPI(this.getApplicationContext());
        questionDao = new QuestionDao(getApplicationContext());
        QuestionView a = questionDao.findOneByStt(task, stt, 0);
        System.out.println("a: "+a.toString());
//        callAPI.getWithToken("/question/233" , null, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                System.out.println(response);
//
//                ArrayList<QuestionView> list_q = questionDao.findAll("de700c09-dd7c-4d46-b6cf-c0ad40c27a2e");
//
//                System.out.println("list "+ list_q.size());
//             }
//
//        });

//        callAPI.login(new RequestParams().put("");)

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
//        actionBar.openOptionsMenu()
        // back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        //


    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(null != question){
//            this.genQuestion();
//        }
    }

    void genQuestion(Question question) {
        Integer part = question.getPart();
        System.out.println("part "+ part);
        // timer
        
        // general
        ImageView quest_img = findViewById(R.id.quest_img);
        TextView quest_desc = findViewById(R.id.quest_desc);
        switch (part){
            case 1:{
                quest_desc.setVisibility(View.INVISIBLE);
                Utils.loadImage(quest_img, question.getGeneral().getImage());
                break;
            }
            case 2:{
                System.out.println("getImage: " + question.getGeneral().getImage());
                break;
            }

            case 3:
            case 4:{

                break;
            }
            default:
                break;
        }

        // list question
        ListView contentItemView = findViewById(R.id.quest_list);
        ArrayList<ContentItem> contentItem = question.getContent();
        contentItemView.setAdapter(new ContentItemAdapter(contentItem));

        // control: prev, next, pause, audio process
//        if(part == 1){

         Button btn_submit = findViewById(R.id.quest_submit);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click");



//                    if(mediaPlayer.isPlaying()){
//                        mediaPlayer.pause();
//                        audio_play = false;
//                    }else{
//                        mediaPlayer.start();
//                        audio_play = true;
//                    }

                }
            });

            try {
                mediaPlayer.setDataSource(question.getGeneral().getAudio());
                mediaPlayer.prepare();
                mediaPlayer.start();
                hdlr.postDelayed(UpdateAudioTime, 100);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }




//    }

    // utils
   private Runnable UpdateAudioTime = new Runnable() {
        @Override
        public void run() {
            int sTime = mediaPlayer.getCurrentPosition();
//            seekBar.setProgress(sTime);
            hdlr.postDelayed(this, 100);
        }
    };

}







