package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Db.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USERS = "User";
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String IMAGE = "image";
    private static final String POINTS = "points";

    private static final String BENEFITS = "Benefits";
    private static final String BENEFIT_ID = "benefit_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";

    private static final String SUBSCRIPTION_BENEFITS = "Subscription_benefits";

    public Database(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_USER_CREATE =
            " CREATE TABLE " + USERS +"(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EMAIL + " TEXT NOT NULL, " +
                    USERNAME + " TEXT NOT NULL, " +
                    PASSWORD + " TEXT NOT NULL, " +
                    IMAGE + " TEXT, " +
                    POINTS + " INTEGER NOT NULL) ";

    private static final String TABLE_BENEFITS_CREATE =
            " CREATE TABLE " + BENEFITS +"(" +
                    BENEFIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT NOT NULL, " +
                    PRICE + " INTEGER NOT NULL) ";

    private static final String TABLE_SUBSCRIPTION_CREATE =
            " CREATE TABLE " + SUBSCRIPTION_BENEFITS + "(" +
                    USER_ID + " INTEGER NOT NULL, " +
                    BENEFIT_ID + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(" + USER_ID + ")REFERENCES " + USERS + "(" + USER_ID + ")," +
                    " FOREIGN KEY(" + BENEFIT_ID + ")REFERENCES " + BENEFITS + "(" + BENEFIT_ID + ")" + ")";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_BENEFITS_CREATE);
        db.execSQL(TABLE_SUBSCRIPTION_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(" DROP TABLE IF EXISTS " + USERS);
        db.execSQL(TABLE_USER_CREATE);

        db.execSQL(" DROP TABLE IF EXISTS " + BENEFITS);
        db.execSQL(TABLE_BENEFITS_CREATE);

        db.execSQL(" DROP TABLE IF EXISTS " + SUBSCRIPTION_BENEFITS);
        db.execSQL(TABLE_SUBSCRIPTION_CREATE);
    }

    public Boolean insertUser(String email, String username, String password, int points){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL, email);
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        contentValues.put(IMAGE, "");
        contentValues.put(POINTS, points);
        long in = db.insert(USERS, null, contentValues);
        if(in == 1)
            return false;
        else {
            return true;
        }
    }

    public void insertAdditionalData (int userId, int benefitId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, userId);
        contentValues.put(BENEFIT_ID, benefitId);
        db.insert(SUBSCRIPTION_BENEFITS, null, contentValues);
    }

    public Boolean chkEmail(String email){
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(" SELECT * FROM " + USERS + " WHERE "+ EMAIL + " = ?", new String[]{email});
            if (cursor.getCount() > 0)
                return false;
            else
                return true;
        }catch (Exception e){
            throw e;
        }

    }

    public boolean checkEmailPassword(String email, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM "+ USERS +" WHERE "+ EMAIL + " = ? AND " + PASSWORD + " = ?";
        Cursor cursor = database.rawQuery( query, new String[]{email, password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public String getUsername(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM " + USERS + " WHERE " + EMAIL + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{email});

        if(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndex(USERNAME));
            return username;
        }
        return null;
    }

    public int getUserId(String email){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = " SELECT " + USER_ID + " FROM " + USERS + " WHERE " + EMAIL + " =? ";
        Cursor cursor = database.rawQuery(query, new String[]{email});
        if (cursor != null && cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public void updateUserImage(int id, String image){
        SQLiteDatabase database = this.getReadableDatabase();
        database.execSQL(" UPDATE " + USERS + " SET " + IMAGE + " = '" + image + "' WHERE " +
                USER_ID + " = " + id);
        database.close();
    }

    public Bitmap getUserImage(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " SELECT * FROM " + USERS + " WHERE " + EMAIL + " =? ";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        Bitmap image ;

        if(cursor.moveToNext()){
            String imageBase64 = cursor.getString(cursor.getColumnIndex(IMAGE));
            if (imageBase64 == null){
                return null;
            }else{
                byte[] imageByte = Base64.decode(imageBase64, Base64.DEFAULT);
                image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                return image;
            }
        }
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

}
