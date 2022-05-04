package com.ptit.toeic.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ptit.toeic.model.Question;
import com.ptit.toeic.model_view.QuestionData;
import com.ptit.toeic.model_view.QuestionView;
import com.ptit.toeic.utils.Convert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class QuestionDao extends SQLiteOpenHelper {
    static String db_name = "question";

    String _id = "id";
    String _task_id = "task_id";
    String _question_id = "question_id";
    String _part = "part";
    String _prev_id = "prev_id";
    String _next_id = "next_id";
    String _is_last = "is_last";
    String _stt = "stt";
    String _answer = "answer"; // json array
    String _data = "data"; // json object
    String _type = "type"; // json object

    ObjectMapper objectMapper = new ObjectMapper();

    public QuestionDao(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s INTEGER, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                db_name, _id, _stt, _question_id, _part, _prev_id, _next_id, _is_last, _task_id, _answer, _data, _type);
        System.out.println(sql);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", db_name);
        sqLiteDatabase.execSQL(drop_students_table);
        onCreate(sqLiteDatabase);
    }

    @SuppressLint("Range")
    public QuestionView findOne(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(db_name, null, _id + " = ?",
                new String[]{
                        String.valueOf(id)
                },
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Gson gson = new GsonBuilder().create();

        QuestionView questionView = new QuestionView();
        questionView.setId(cursor.getLong(cursor.getColumnIndex(_id)));
        questionView.setTask_id(cursor.getString(cursor.getColumnIndex(_task_id)));
        questionView.setNext_id(cursor.getLong(cursor.getColumnIndex(_next_id)));
        questionView.setPrev_id(cursor.getLong(cursor.getColumnIndex(_prev_id)));
        questionView.setPart(cursor.getInt(cursor.getColumnIndex(_part)));
        questionView.setQuestion_id(cursor.getInt(cursor.getColumnIndex(_question_id)));
        questionView.setStt(cursor.getInt(cursor.getColumnIndex(_stt)));
        questionView.setIs_last(cursor.getInt(cursor.getColumnIndex(_is_last)));
        questionView.setType(cursor.getString(cursor.getColumnIndex(_type)));

        String d = cursor.getString(cursor.getColumnIndex(_data));
        questionView.setData(d);

        String a = cursor.getString(cursor.getColumnIndex(_answer));
        questionView.setAnswer(a);

        System.out.println("questionView Dao: " + questionView.getData());
        return questionView;
    }

    @SuppressLint("Range")
    public QuestionView findOneByStt(String task_id, int stt, @Nullable int part) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuestionView> list = new ArrayList<>();
        String s = String.format("Select * from %s where task_id = '%s' and stt = %s ", db_name, task_id, stt);

        if (part > 0) {
            s += String.format("and part = %s", part);
        }

        System.out.println("sql: " + s);
        Cursor cursor = db.rawQuery(s, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            QuestionView q = new QuestionView();
            q.setId(cursor.getLong(cursor.getColumnIndex(_id)));
            q.setTask_id(cursor.getString(cursor.getColumnIndex(_task_id)));
            q.setNext_id(cursor.getLong(cursor.getColumnIndex(_next_id)));
            q.setPrev_id(cursor.getLong(cursor.getColumnIndex(_prev_id)));
            q.setPart(cursor.getInt(cursor.getColumnIndex(_part)));
            q.setQuestion_id(cursor.getInt(cursor.getColumnIndex(_question_id)));
            q.setStt(cursor.getInt(cursor.getColumnIndex(_stt)));
            q.setIs_last(cursor.getInt(cursor.getColumnIndex(_is_last)));
            q.setType(cursor.getString(cursor.getColumnIndex(_type)));

            q.setData(cursor.getString(cursor.getColumnIndex(_data)));
            q.setAnswer(cursor.getString(cursor.getColumnIndex(_answer)));

            list.add(q);
            cursor.moveToNext();
        }

        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    @SuppressLint("Range")
    public ArrayList<QuestionView> findAllbyTask(String task_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuestionView> list = new ArrayList<>();
        String sql = "Select * from " + db_name + " where task_id = '" + task_id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            System.out.println("cursor: " + cursor.getString(cursor.getColumnIndex("task_id")));

            QuestionView q = new QuestionView();
            q.setId(cursor.getLong(cursor.getColumnIndex(_id)));
            q.setTask_id(cursor.getString(cursor.getColumnIndex(_task_id)));
            q.setNext_id(cursor.getLong(cursor.getColumnIndex(_next_id)));
            q.setPrev_id(cursor.getLong(cursor.getColumnIndex(_prev_id)));
            q.setPart(cursor.getInt(cursor.getColumnIndex(_part)));
            q.setQuestion_id(cursor.getInt(cursor.getColumnIndex(_question_id)));
            q.setStt(cursor.getInt(cursor.getColumnIndex(_stt)));
            q.setIs_last(cursor.getInt(cursor.getColumnIndex(_is_last)));
            q.setType(cursor.getString(cursor.getColumnIndex(_type)));

            q.setData(cursor.getString(cursor.getColumnIndex(_data)));
            q.setAnswer(cursor.getString(cursor.getColumnIndex(_answer)));

            list.add(q);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<QuestionView> findAllbyType(String task_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuestionView> list = new ArrayList<>();
        String sql = "Select * from " + db_name + " where type = '" + task_id + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            System.out.println("cursor: " + cursor.getString(cursor.getColumnIndex("task_id")));

            QuestionView q = new QuestionView();
            q.setId(cursor.getLong(cursor.getColumnIndex(_id)));
            q.setTask_id(cursor.getString(cursor.getColumnIndex(_task_id)));
            q.setNext_id(cursor.getLong(cursor.getColumnIndex(_next_id)));
            q.setPrev_id(cursor.getLong(cursor.getColumnIndex(_prev_id)));
            q.setPart(cursor.getInt(cursor.getColumnIndex(_part)));
            q.setQuestion_id(cursor.getInt(cursor.getColumnIndex(_question_id)));
            q.setStt(cursor.getInt(cursor.getColumnIndex(_stt)));
            q.setIs_last(cursor.getInt(cursor.getColumnIndex(_is_last)));
            q.setType(cursor.getString(cursor.getColumnIndex(_type)));

            q.setData(cursor.getString(cursor.getColumnIndex(_data)));
            q.setAnswer(cursor.getString(cursor.getColumnIndex(_answer)));

            list.add(q);
            cursor.moveToNext();
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<QuestionView> findAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuestionView> list = new ArrayList<>();
        String sql = "Select * from " + db_name;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            System.out.println("cursor: " + cursor.getString(cursor.getColumnIndex("task_id")));

            QuestionView q = new QuestionView();
            q.setId(cursor.getLong(cursor.getColumnIndex(_id)));
            q.setTask_id(cursor.getString(cursor.getColumnIndex(_task_id)));
            q.setNext_id(cursor.getLong(cursor.getColumnIndex(_next_id)));
            q.setPrev_id(cursor.getLong(cursor.getColumnIndex(_prev_id)));
            q.setPart(cursor.getInt(cursor.getColumnIndex(_part)));
            q.setQuestion_id(cursor.getInt(cursor.getColumnIndex(_question_id)));
            q.setStt(cursor.getInt(cursor.getColumnIndex(_stt)));
            q.setIs_last(cursor.getInt(cursor.getColumnIndex(_is_last)));
            q.setType(cursor.getString(cursor.getColumnIndex(_type)));

            q.setData(cursor.getString(cursor.getColumnIndex(_data)));
            q.setAnswer(cursor.getString(cursor.getColumnIndex(_answer)));

            list.add(q);
            cursor.moveToNext();
        }
        return list;
    }

    public QuestionView insert(QuestionView questionView) {

        SQLiteDatabase db = this.getWritableDatabase();
        boolean check_exist = tableExists(db, db_name);

        if (!check_exist) {
            onCreate(db);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(_stt, questionView.getStt());
        contentValues.put(_question_id, questionView.getQuestion_id());
        contentValues.put(_part, questionView.getPart());
        contentValues.put(_prev_id, questionView.getPrev_id());
        contentValues.put(_next_id, questionView.getNext_id());
        contentValues.put(_is_last, questionView.isIs_last());
        contentValues.put(_task_id, questionView.getTask_id());
        contentValues.put(_type, questionView.getType());

        try {
            Map<String, Object> map
                    = objectMapper.readValue(questionView.getData(), new TypeReference<Map<String, Object>>() {
            });

            // set default answer to -1
            ArrayList<Integer> an = new ArrayList<Integer>(Collections.nCopies((Integer) map.get("count_question"), -1));
            contentValues.put(_answer, an.toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        contentValues.put(_data, questionView.getData());

        long id = db.insert(db_name, null, contentValues);
        db.close();
        System.out.println("Insert success: " + String.valueOf(id));

        questionView.setId(id);
        return questionView;
    }

    public QuestionView update(QuestionView questionView) {

        SQLiteDatabase db = this.getWritableDatabase();
        boolean check_exist = tableExists(db, db_name);

        if (!check_exist) {
            onCreate(db);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(_stt, questionView.getStt());
        contentValues.put(_question_id, questionView.getQuestion_id());
        contentValues.put(_part, questionView.getPart());
        contentValues.put(_prev_id, questionView.getPrev_id());
        contentValues.put(_next_id, questionView.getNext_id());
        contentValues.put(_is_last, questionView.isIs_last());
        contentValues.put(_task_id, questionView.getTask_id());
        contentValues.put(_data, String.valueOf(questionView.getData()));
        contentValues.put(_answer, String.valueOf(questionView.getAnswer()));
        contentValues.put(_type, String.valueOf(questionView.getType()));

        db.update(db_name, contentValues, _id + " = ?", new String[]{
                String.valueOf(questionView.getId())
        });
        db.close();

        return questionView;
    }

    boolean tableExists(SQLiteDatabase db, String tableName) {
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?",
                new String[]{"table", tableName}
        );
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    @SuppressLint("Range")
    public ArrayList<QuestionView> findAllbyPart(String part) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<QuestionView> list = new ArrayList<>();
        String sql = "Select * from " + db_name + " where part = '" + part + "'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            System.out.println("cursor: " + cursor.getString(cursor.getColumnIndex("part")));

            QuestionView q = new QuestionView();
            q.setId(cursor.getLong(cursor.getColumnIndex(_id)));
            q.setTask_id(cursor.getString(cursor.getColumnIndex(_task_id)));
            q.setNext_id(cursor.getLong(cursor.getColumnIndex(_next_id)));
            q.setPrev_id(cursor.getLong(cursor.getColumnIndex(_prev_id)));
            q.setPart(cursor.getInt(cursor.getColumnIndex(_part)));
            q.setQuestion_id(cursor.getInt(cursor.getColumnIndex(_question_id)));
            q.setStt(cursor.getInt(cursor.getColumnIndex(_stt)));
            q.setIs_last(cursor.getInt(cursor.getColumnIndex(_is_last)));
            q.setType(cursor.getString(cursor.getColumnIndex(_type)));

            q.setData(cursor.getString(cursor.getColumnIndex(_data)));
            q.setAnswer(cursor.getString(cursor.getColumnIndex(_answer)));

            list.add(q);
            cursor.moveToNext();
        }
        return list;
    }
}
