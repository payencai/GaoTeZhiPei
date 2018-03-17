package com.yichan.gaotezhipei.dao.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Simon on 2018/3/15.
 */
public class SQLiteDbController {
    private SQLiteOpenHelper mHelper;

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


    public SQLiteDbController(SQLiteOpenHelper helper) {
        mHelper = helper;
    }

    private SQLiteDatabase getReadableDatabase() {
        if (mWritableDB != null) {
            return mWritableDB;
        }
        if (mReadableDB == null) {
            // mReadableDB = mHelper.getReadableDatabase(PASS_WORD);
            mReadableDB = mHelper.getReadableDatabase();
        }
        return mReadableDB;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (mReadableDB != null) {
            closeDB();
        }
        if (mWritableDB == null) {
            // mWritableDB = mHelper.getWritableDatabase(PASS_WORD);
            mWritableDB = mHelper.getWritableDatabase();
        }
        return mWritableDB;
    }




    public Cursor queryFromDB(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                              String having, String orderBy) {
        return this.getReadableDatabase()
                .query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor queryFromDB(String table, String[] columns, String selection, String[] selectionArgs, String groupBy,
                              String having, String orderBy, String limit) {
        return this.getReadableDatabase()
                .query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor queryFromDB(boolean distinct, String table, String[] columns, String selection,
                              String[] selectionArgs,
                              String groupBy, String having, String orderBy, String limit) {
        return this.getReadableDatabase()
                .query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQueryFromDB(String sql, String[] selectionArgs) {
        return this.getReadableDatabase().rawQuery(sql, selectionArgs);
    }

    // public Cursor rawQueryFromDB(String sql, String[] selectionArgs, int initialRead, int maxRead) {
    // return this.getReadableDatabase().rawQuery(sql, selectionArgs, initialRead, maxRead);
    // }

    public void execSQLToDB(String arg0) {
        this.getWritableDatabase().execSQL(arg0);
    }

    public void execSQLToDB(String arg0, Object[] arg1) {
        this.getWritableDatabase().execSQL(arg0, arg1);
    }

    public long insertToDB(String arg0, String arg1, ContentValues arg2) {

        return this.getWritableDatabase().insert(arg0, arg1, arg2);
    }

    public void deleteFromDB(String arg0, String arg1, String[] arg2) {
        this.getWritableDatabase().delete(arg0, arg1, arg2);
    }

    public int updateFromDB(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return this.getWritableDatabase().update(table, values, whereClause, whereArgs);
    }

    public void beginTransaction() {
        getWritableDatabase().beginTransaction();
    }

    public void setTransactionSuccessful(){
        getWritableDatabase().setTransactionSuccessful();
    }

    public void endTransaction() {
        getWritableDatabase().endTransaction();
    }

    public void closeDB() {
        if (mWritableDB != null) {
            mWritableDB.close();
            mWritableDB = null;
        }
        if (mReadableDB != null) {
            mReadableDB.close();
            mReadableDB = null;
        }
    }

}
