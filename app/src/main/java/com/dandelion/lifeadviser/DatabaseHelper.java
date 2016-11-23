package com.dandelion.lifeadviser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private String line;

    private static final String DATABASE_NAME = "advicesdatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "advices";
    private static final String ID_COLUMN = "id";
    private static final String ADVICE_COLUMN = "advice";
    private static final String AUTHOR_COLUMN = "author";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + ID_COLUMN
            + " integer primary key autoincrement, " + ADVICE_COLUMN
            + " text not null, " + AUTHOR_COLUMN + " text not null);";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (null == databaseHelper) {
            databaseHelper = new DatabaseHelper(context);
            sqLiteDatabase = databaseHelper.getReadableDatabase();
        }
        return databaseHelper;
    }

    public List<String> getAll() {
        List<String> advices = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.DATABASE_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            advices.add(cursor.getString(cursor
                    .getColumnIndex(DatabaseHelper.ADVICE_COLUMN)));
        }
        cursor.close();
        return advices;
    }

    public List<String> get(String advice) {
        List<String> advices = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                new String[]{ADVICE_COLUMN, AUTHOR_COLUMN},
                ADVICE_COLUMN + " = ?", new String[]{advice}, null, null, null);
        while (cursor.moveToNext()) {
            advices.add(cursor.getString(cursor.getColumnIndex(ADVICE_COLUMN)));
            advices.add(cursor.getString(cursor.getColumnIndex(AUTHOR_COLUMN)));
        }
        cursor.close();
        return advices;
    }

    public void create(String advice, String author) {
        ContentValues values = new ContentValues();
        values.put(ADVICE_COLUMN, advice);
        values.put(AUTHOR_COLUMN, author);
        sqLiteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public void delete(String advice) {
        sqLiteDatabase.delete(DATABASE_TABLE,
                ADVICE_COLUMN + "= ?", new String[]{advice});
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
