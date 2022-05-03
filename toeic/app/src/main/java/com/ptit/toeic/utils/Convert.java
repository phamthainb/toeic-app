package com.ptit.toeic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Convert {

    public static <T> ArrayList<T> string2Array(String s){
        try {
            return new ObjectMapper().reader(List.class).readValue(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static Map<String, Object> string2Json(String s){
        try {
            return new ObjectMapper().readValue(s, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return (Map<String, Object>) new Object();
    }

    public static Integer caculator_score(String type, Integer number_crrect){
        Integer score = 0;
        if(type == "reading"){
            switch (String.valueOf(number_crrect)) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
                case "16":
                case "17":
                case "18":
                case "19":
                case "20":
                case "21":
                    score = 5;
                    break;
                case "22":
                    score = 10;
                    break;
                case "23":
                    score = 15;
                    break;
                case "24":
                    score = 20;
                    break;
                case "25":
                    score = 25;
                    break;
                case "26":
                    score = 30;
                    break;
                case "27":
                    score = 35;
                    break;
                case "28":
                    score = 40;
                    break;
                case "29":
                    score = 45;
                    break;
                case "30":
                    score = 55;
                    break;
                case "31":
                    score = 60;
                    break;
                case "32":
                    score = 65;
                    break;
                case "33":
                    score = 70;
                    break;
                case "34":
                    score = 75;
                    break;
                case "35":
                    score = 80;
                    break;
                case "36":
                    score = 85;
                    break;
                case "37":
                    score = 90;
                    break;
                case "38":
                    score = 95;
                    break;
                case "39":
                    score = 105;
                    break;
                case "40":
                    score = 115;
                    break;

                case "41":
                    score = 120;
                    break;
                case "42":
                    score = 125;
                    break;
                case "43":
                    score = 130;
                    break;
                case "44":
                    score = 135;
                    break;
                case "45":
                    score = 140;
                    break;
                case "46":
                    score = 145;
                    break;
                case "47":
                    score = 155;
                    break;
                case "48":
                    score = 160;
                    break;
                case "49":
                    score = 170;
                    break;
                case "50":
                    score = 175;
                    break;
                case "51":
                    score = 185;
                    break;
                case "52":
                    score = 195;
                    break;
                case "53":
                    score = 205;
                    break;
                case "54":
                    score = 210;
                    break;
                case "55":
                    score = 215;
                    break;
                case "56":
                    score = 220;
                    break;
                case "57":
                    score = 230;
                    break;
                case "58":
                    score = 240;
                    break;
                case "59":
                    score = 245;
                    break;
                case "60":
                    score = 250;
                    break;
                case "61":
                    score = 255;
                    break;
                case "62":
                    score = 260;
                    break;
                case "63":
                    score = 270;
                    break;
                case "64":
                    score = 275;
                    break;
                case "65":
                    score = 280;
                    break;
                case "66":
                    score = 285;
                    break;
                case "67":
                    score = 290;
                    break;
                case "68":
                    score = 295;
                    break;
                case "69":
                    score = 295;
                    break;
                case "70":
                    score = 300;
                    break;
                case "71":
                    score = 310;
                    break;
                case "72":
                    score = 315;
                    break;
                case "73":
                    score = 320;
                    break;
                case "74":
                    score = 325;
                    break;
                case "75":
                    score = 330;
                    break;
                case "76":
                    score = 335;
                    break;
                case "77":
                    score = 340;
                    break;
                case "78":
                    score = 340;
                    break;
                case "79":
                    score = 345;
                    break;
                case "80":
                    score = 360;
                    break;
                case "81":
                    score = 370;
                    break;
                case "82":
                    score = 375;
                    break;
                case "83":
                    score = 385;
                    break;
                case "84":
                    score = 390;
                    break;
                case "85":
                    score = 395;
                    break;
                case "86":
                    score = 405;
                    break;
                case "87":
                    score = 415;
                    break;
                case "88":
                    score = 420;
                    break;
                case "89":
                    score = 425;
                    break;
                case "90":
                    score = 435;
                    break;
                case "91":
                    score = 440;
                    break;
                case "92":
                    score = 450;
                    break;
                case "93":
                    score = 455;
                    break;
                case "94":
                    score = 460;
                    break;
                case "95":
                    score = 470;
                    break;
                case "96":
                    score = 475;
                    break;
                case "97":
                    score = 485;
                    break;
                case "98":
                    score = 485;
                    break;
                case "99":
                    score = 490;
                    break;
                case "100":
                    score = 495;
                    break;
                default:
                    score = 0;

            }
        }
        else {
            switch (String.valueOf(number_crrect)) {
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "10":
                case "11":
                case "12":
                case "13":
                case "14":
                case "15":
                case "16":
                case "17":
                    score = 5;
                    break;
                case "18":
                    score = 10;
                    break;
                case "19":
                    score = 15;
                    break;
                case "20":
                    score = 20;
                    break;
                case "21":
                    score = 25;
                    break;
                case "22":
                    score = 30;
                    break;
                case "23":
                    score = 35;
                    break;
                case "24":
                    score = 40;
                    break;
                case "25":
                    score = 45;
                    break;
                case "26":
                    score = 50;
                    break;
                case "27":
                    score = 55;
                    break;
                case "28":
                    score = 60;
                    break;
                case "29":
                    score = 70;
                    break;
                case "30":
                    score = 80;
                    break;
                case "31":
                    score = 85;
                    break;
                case "32":
                    score = 90;
                    break;
                case "33":
                    score = 95;
                    break;
                case "34":
                    score = 100;
                    break;
                case "35":
                    score = 105;
                    break;
                case "36":
                    score = 115;
                    break;
                case "37":
                    score = 125;
                    break;
                case "38":
                    score = 135;
                    break;
                case "39":
                    score = 140;
                    break;
                case "40":
                    score = 150;
                    break;
                case "41":
                    score = 160;
                    break;
                case "42":
                    score = 170;
                    break;
                case "43":
                    score = 175;
                    break;
                case "44":
                    score = 180;
                    break;
                case "45":
                    score = 190;
                    break;
                case "46":
                    score = 200;
                    break;
                case "47":
                    score = 205;
                    break;
                case "48":
                    score = 215;
                    break;
                case "49":
                    score = 220;
                    break;
                case "50":
                    score = 225;
                    break;
                case "51":
                    score = 230;
                    break;
                case "52":
                    score = 235;
                    break;
                case "53":
                    score = 245;
                    break;
                case "54":
                    score = 255;
                    break;
                case "55":
                    score = 260;
                    break;
                case "56":
                    score = 265;
                    break;
                case "57":
                    score = 275;
                    break;
                case "58":
                    score = 285;
                    break;
                case "59":
                    score = 290;
                    break;
                case "60":
                    score = 295;
                    break;
                case "61":
                    score = 300;
                    break;
                case "62":
                    score = 310;
                    break;
                case "63":
                    score = 320;
                    break;
                case "64":
                    score = 325;
                    break;
                case "65":
                    score = 330;
                    break;
                case "66":
                    score = 335;
                    break;
                case "67":
                    score = 340;
                    break;
                case "68":
                    score = 345;
                    break;
                case "69":
                    score = 350;
                    break;
                case "70":
                    score = 355;
                    break;
                case "71":
                    score = 360;
                    break;
                case "72":
                    score = 365;
                    break;
                case "73":
                    score = 370;
                    break;
                case "74":
                    score = 375;
                    break;
                case "75":
                    score = 385;
                    break;
                case "76":
                    score = 395;
                    break;
                case "77":
                    score = 400;
                    break;
                case "78":
                    score = 405;
                    break;
                case "79":
                    score = 415;
                    break;
                case "80":
                    score = 420;
                    break;
                case "81":
                    score = 425;
                    break;
                case "82":
                    score = 430;
                    break;
                case "83":
                    score = 435;
                    break;
                case "84":
                    score = 440;
                    break;
                case "85":
                    score = 445;
                    break;
                case "86":
                    score = 455;
                    break;
                case "87":
                    score = 460;
                    break;
                case "88":
                    score = 465;
                    break;
                case "89":
                    score = 475;
                    break;
                case "90":
                    score = 480;
                    break;
                case "91":
                    score = 485;
                    break;
                case "92":
                    score = 490;
                    break;
                case "93":
                case "94":
                case "95":
                case "96":
                case "97":
                case "98":
                case "99":
                case "100":
                    score = 495;
                    break;
                default:
                    score = 0;
            }
        }
        return score;
    }

    public static String getTimerText(Double time) {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formatTime(seconds, minutes, hours);
    }

    public static String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
}
