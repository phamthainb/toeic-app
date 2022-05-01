package com.ptit.toeic.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {
    public static void loadImage(ImageView imageView, String url) {
        // start a background thread for networking
        new Thread(new Runnable() {
            public void run(){
                try {
                    // download the drawable
                    final Drawable drawable = Drawable.createFromStream((InputStream) new URL(url).getContent(), "src");
                    // edit the view in the UI thread
                    imageView.post(new Runnable() {
                        public void run() {
                            imageView.setImageDrawable(drawable);
                        }
                    });
                } catch (IOException e) {
                    System.out.println("Error when try loading img from url: "+url);
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
