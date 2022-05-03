package com.ptit.toeic.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ptit.toeic.PageMain;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Utils {
    static Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public static void loadImage(ImageView imageView, String url) {
        // start a background thread for networking
        new Thread(new Runnable() {
            public void run() {
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
                    System.out.println("Error when try loading img from url: " + url);
                    new AlertDialog.Builder(context)
                            .setMessage("Look like your Internet has problem, Please check again.")
                            .setCancelable(false)
                            .setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(context, PageMain.class);
                                            context.startActivity(intent);
                                        }
                                    })
                            .setNegativeButton("No", null)
                            .show();

                    e.printStackTrace();
                }
            }
        }).start();
    }
}
