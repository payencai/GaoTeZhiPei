package com.yichan.gaotezhipei.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.yichan.gaotezhipei.common.constant.Constans;
import com.yichan.gaotezhipei.dao.helper.DataDBHelper;
import com.yichan.gaotezhipei.dao.helper.SQLiteDbController;
import com.yichan.gaotezhipei.server.netstation.entity.ExpressCompanyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2018/3/15.
 */
public class ExpressCompanyDao {

    private Context mContext;

    public ExpressCompanyDao(Context context) {
        this.mContext = context;
    }

    public void saveOrUpdate(ExpressCompanyItem expressCompanyItem) {

        ContentValues values = new ContentValues();
        values.put(Constans.TBL_EXPRESS_COMPANY_COLUMNS.ID.toString(), expressCompanyItem.getId());
        values.put(Constans.TBL_EXPRESS_COMPANY_COLUMNS.CODE.toString(), expressCompanyItem.getCode());
        values.put(Constans.TBL_EXPRESS_COMPANY_COLUMNS.NAME.toString(), expressCompanyItem.getName());
        values.put(Constans.TBL_EXPRESS_COMPANY_COLUMNS.PIC_KEY.toString(), expressCompanyItem.getPicKey());
        values.put(Constans.TBL_EXPRESS_COMPANY_COLUMNS.IS_CANCEL.toString(), expressCompanyItem.getIsCancel());

        DataDBHelper dbHelper = new DataDBHelper(mContext);
        SQLiteDbController dbController = new SQLiteDbController(dbHelper);

        Cursor cursor = dbController.rawQueryFromDB(
                String.format("select count(*) from %1$s where id=?", Constans.DATA_DB_TABLES.TBL_EXPRESS_COMPANY.toString()),
                new String[]{expressCompanyItem.getId().toString()});
        int count = 0;
        if (cursor.moveToNext())
            count = cursor.getInt(0);
        if (count > 0) {
            String whereClause = Constans.TBL_EXPRESS_COMPANY_COLUMNS.ID + "=?";
            String[] whereArgs = new String[]{expressCompanyItem.getId().toString()};
            dbController.updateFromDB(Constans.DATA_DB_TABLES.TBL_EXPRESS_COMPANY.toString(), values, whereClause, whereArgs);
        } else {

            long rowId = dbController.insertToDB(Constans.DATA_DB_TABLES.TBL_EXPRESS_COMPANY.toString(), null, values);

        }

        dbController.closeDB();
        //dbController.closeDBHelper();
        dbHelper.close();

    }

    /**
     * @param
     */

    public int getCount() {
        DataDBHelper dbHelper = new DataDBHelper(mContext);
        SQLiteDbController dbController = new SQLiteDbController(dbHelper);

        String sql = "select count(*) from  " + Constans.DATA_DB_TABLES.TBL_EXPRESS_COMPANY + " where " + Constans.TBL_EXPRESS_COMPANY_COLUMNS.IS_CANCEL + "=?";
        String[] selectionArgs = new String[]{"0"};

        Cursor cursor = dbController.rawQueryFromDB(sql,
                selectionArgs);

        cursor.moveToFirst();
        int count = cursor.getInt(0);

        dbController.closeDB();
        dbHelper.close();

        return count;

    }

    public void updateAllToDeleted() {
        ContentValues values = new ContentValues();
        values.put(Constans.TBL_EXPRESS_COMPANY_COLUMNS.IS_CANCEL.toString(), 1);

        DataDBHelper dbHelper = new DataDBHelper(mContext);
        SQLiteDbController dbController = new SQLiteDbController(dbHelper);

        dbController.updateFromDB(Constans.DATA_DB_TABLES.TBL_EXPRESS_COMPANY.toString(), values, null, null);

        dbController.closeDB();
        dbHelper.close();
    }

    /**
     * 获取所有的item
     */
    public List<ExpressCompanyItem> findList() {
        List<ExpressCompanyItem> list = new ArrayList<>();
        DataDBHelper dbHelper = new DataDBHelper(mContext);
        SQLiteDbController dbController = new SQLiteDbController(dbHelper);

        String selection = Constans.TBL_EXPRESS_COMPANY_COLUMNS.IS_CANCEL.toString() + "=?";
        String[] selectionArgs = new String[]{"0"};

        Cursor cursor = dbController.queryFromDB(Constans.DATA_DB_TABLES.TBL_EXPRESS_COMPANY.toString()
                , null, selection, selectionArgs, null, null, Constans.TBL_EXPRESS_COMPANY_COLUMNS.ID.toString());
        ExpressCompanyItem expressCompanyItem;
        while (cursor.moveToNext()) {
            expressCompanyItem = new ExpressCompanyItem();
            expressCompanyItem.initWithCursor(cursor);
            list.add(expressCompanyItem);
        }
        cursor.close();
        dbController.closeDB();
        dbHelper.close();

        return list;

    }
}
