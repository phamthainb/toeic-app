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
import com.ptit.toeic.model.ContentItem;
import com.ptit.toeic.model.General;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.utils.CallAPI;
import com.ptit.toeic.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class Part3Activity extends AppCompatActivity {
    Gson gson = new Gson();
    Question question = gson.fromJson("{\"part\":1,\"id\":105,\"tag\":\"listen\",\"kind\":\"picture description\",\"general\":{\"audio\":\"https://migiitoeic.eupgroup.net/uploads/audio_ETS/ad7802e186038010f91635848904ac68.mp3\",\"txt_read\":\"\",\"txt_read_vn\":\"\",\"image\":\"https://migiitoeic.eupgroup.net/uploads/image_ETS/5634406bc916b7a0484de2dcbcbff940.JPG\",\"txt_audio\":\"A. They're folding some papers<br>B. They're putting a picture in a frame<br>C. They're studying a drawing<br>D. They're closing a window\",\"txt_audio_vn\":\"<p>A. Họ đang gấp một v&agrave;i giấy tờ&nbsp;</p><p>B. Họ đang lồng bức tranh v&agrave;o khung&nbsp;</p><p>C. Họ đang nghi&ecirc;n cứu bản vẽ&nbsp;</p><p>D. Họ đang đ&oacute;ng cửa sổ</p>\",\"txt_audio_trans\":{\"vi\":\"<p>A. Họ đang gấp một v&agrave;i giấy tờ&nbsp;</p><p>B. Họ đang lồng bức tranh v&agrave;o khung&nbsp;</p><p>C. Họ đang nghi&ecirc;n cứu bản vẽ&nbsp;</p><p>D. Họ đang đ&oacute;ng cửa sổ</p>\",\"ja\":\"A.彼らはいくつかの紙を折っています<br>B.彼らは額縁に写真を入れています<br>C.彼らは絵を勉強しています<br>D.彼らは窓を閉めている\",\"ko\":\"A. 그들은 종이를 접고 있습니다.<br>B. 액자에 사진을 넣고 있어요<br>C. 그들은 그림을 공부하고 있다<br>D. 창문을 닫고 있다\",\"zh-CN\":\"A. 他们在折一些纸<br>B. 他们把照片放在相框里<br>C. 他们正在研究一幅画<br>D. 他们正在关上一扇窗户\",\"fr\":\"A. Ils plient des papiers<br>B. Ils mettent une photo dans un cadre<br>C. Ils étudient un dessin<br>D. Ils ferment une fenêtre\",\"zh-TW\":\"A. 他們在折一些紙<br>B. 他們把照片放在相框裡<br>C. 他們正在研究一幅畫<br>D. 他們正在關上一扇窗戶\"}},\"content\":[{\"question\":\"Select the answer\",\"answers\":[\"A\",\"B\",\"C\",\"D\"],\"correctAnswer\":2,\"image\":\"https://migiitoeic.eupgroup.net/uploads/image_ETS/ca58dd0d15fa3bc212943125c07b25c6.png\",\"explainAll\":{\"vn\":\"<p>studying, a drawing</p>\",\"en\":\"<p>studying, a drawing</p>\",\"ja\":\"<p>studying, a drawing</p>\",\"vi\":\"<p>studying, a drawing</p>\",\"ko\":\"<p>studying, a drawing</p>\",\"zh-CN\":\"<p>studying, a drawing</p>\",\"fr\":\"<p>studying, a drawing</p>\",\"zh-TW\":\"<p>studying, a drawing</p>\"}}],\"correct_answers\":[2],\"count_question\":1,\"title\":\"For each question, you will see a picture and you will hear four short statements. The statements will be spoken just one time. They will not be printed in your test book so you must listen carefully to understand what the speaker says. When you hear the four statements, look at the picture and choose the statement that best describes what you see in the picture. Choose the best answer A, B, C or D\",\"title_trans\":{\"vn\":\"Với mỗi câu hỏi, bạn sẽ được xem 1 bức tranh và nghe 4 câu mô tả ngắn. Mỗi câu sẽ chỉ được nói 1 lần. Chúng sẽ không được in trên đề thi nên bạn cần nghe thật cẩn thận để hiểu những điều người nói. Khi bạn nghe 4 câu mô tả, hãy nhìn vào bức tranh và chọn câu mô tả đúng nhất những gì bạn thấy ở trong bức tranh. Chọn đáp án đúng nhất A, B, C, D.\",\"en\":\"For each question, you will see a picture and you will hear four short statements. The statements will be spoken just one time. They will not be printed in your test book so you must listen carefully to understand what the speaker says. When you hear the four statements, look at the picture and choose the statement that best describes what you see in the picture. Choose the best answer A, B, C or D\"},\"scores\":[1]}", Question.class);
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

    void genQuestion() {
        Integer part = question.getPart();
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
        if(part == 1){

         Button btn_submit = findViewById(R.id.quest_submit);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    RequestParams params = new RequestParams();
//                    params.put("email", "phamthainb@gmail.com");
//                    params.put("password", "12345678");
//
//                    CallAPI.login(params);

                    RequestParams signup = new RequestParams();
                    signup.put("email", "sss@gmail.com");
                    signup.put("password", "12345678");
                    signup.put("username", "sss");

                   new CallAPI(getApplicationContext()).post("/account/signup/", signup, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            System.out.println("response : "+ response);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            System.out.println("1");
                            try {
                                System.out.println("response : "+ errorResponse.getJSONObject("message").getJSONArray("email").get(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
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




    }

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







