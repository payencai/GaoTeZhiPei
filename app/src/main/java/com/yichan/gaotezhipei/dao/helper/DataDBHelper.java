package com.yichan.gaotezhipei.dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.common.util.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Simon on 2018/3/15.
 */
public class DataDBHelper extends SQLiteOpenHelper {
    Context mContext;
    private static final int VERSION = 1;

    public DataDBHelper(Context context) {
        super(context, Constans.DATABASE.DATABASE_DATA.toString(), null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dataBaseSQLName = Constans.DATABASE_SQL.DATABASE_DATA_SQL.toString();
        InputStream in = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufReader = null;
        try {
            in = mContext.getAssets().open(dataBaseSQLName.toString());
            inputStreamReader = new InputStreamReader(in);
            bufReader = new BufferedReader(inputStreamReader);
            String oneSqlStr = "";
            while ((oneSqlStr = bufReader.readLine()) != null) {
                db.execSQL(oneSqlStr.replace(";", ""));
                Log.i("DataDBHelper", "--->>>DataDBHelper--->>>execSQL" + oneSqlStr.replace(";", ""));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            FileUtil.closeBufferedReader(bufReader);
            FileUtil.closeInputStreamReader(inputStreamReader);
            FileUtil.closeInputStream(in);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Context getContext() {
        return mContext;
    }
}
