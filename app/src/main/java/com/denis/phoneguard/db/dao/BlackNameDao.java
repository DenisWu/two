package com.denis.phoneguard.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.denis.phoneguard.bean.BlackName;
import com.denis.phoneguard.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询黑名单数据库
 *
 * @auther o
 * @date 2016/9/19.
 */
public class BlackNameDao {
    public BlackNameDao(Context context) {
        dbHelper = DBHelper.newInstance(context);
    }

    private static int MODE_BLOCK_ALL = 3;
    private static int MODE_BLOCK_PHONE = 2;
    private static int MODE_BLOCK_SMS = 1;
    private DBHelper dbHelper;

    /**
     * 插入数据库
     */
    public boolean addBlackName(String phone, int mode) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("mode", mode);
        long insert = db.insert(DBHelper.BlackNameList.TABLE_NAME, null, values);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 删除记录
     *
     * @param phone
     * @return
     */
    public boolean deleteBlackName(String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.v("Shortcut", "删除的phone:" + phone);
        int delete = db.delete(DBHelper.BlackNameList.TABLE_NAME, "phone=?", new String[]{phone});
        if (delete == 0) {
            return false;
        } else {
            Log.v("Shortcut", "删除记录数" + delete);
            return true;
        }
    }

    /**
     * 修改记录
     *
     * @param phone
     * @param mode
     * @return
     */
    public boolean modifyBlackName(String phone, int mode) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mode", mode);
        int update = db.update(DBHelper.BlackNameList.TABLE_NAME, values, "phone=?", new String[]{phone});
        if (update == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据电话号码查询记录
     *
     * @param phone
     * @return
     */
    public int queryBlackName(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.BlackNameList.TABLE_NAME, new String[]{"mode"}, "phone=?", new String[]{phone}, null, null, null);
        String mode = 1 + "";
        if (cursor != null) {
            try {
                if (cursor.moveToNext()) {
                    mode = cursor.getString(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return Integer.valueOf(mode);
    }

    /**
     * 查询全部记录
     */
    public List<BlackName> findAll() {
        List<BlackName> blackNameList = new ArrayList<BlackName>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.BlackNameList.TABLE_NAME, null, null, null, null, null, DBHelper.BlackNameList.PHONE);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    BlackName blackName = new BlackName();
                    blackName.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.BlackNameList.PHONE)));
                    blackName.setMode(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.BlackNameList.MODE))));
                    blackNameList.add(blackName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return blackNameList;
    }

    /**
     * 查询某页的数据量
     * currentPage要返回的页面，number每一页的数量
     *
     * @return
     */
    public List<BlackName> findPage(int currentPage, int number) {
        List<BlackName> blackNameList = new ArrayList<BlackName>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.BlackNameList.TABLE_NAME, null, null, null, null, null, DBHelper.BlackNameList.PHONE, (currentPage - 1) *
                number + "," + number);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    BlackName blackName = new BlackName();
                    blackName.setPhone(cursor.getString(cursor.getColumnIndex(DBHelper.BlackNameList.PHONE)));
                    blackName.setMode(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.BlackNameList.MODE))));
                    blackNameList.add(blackName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return blackNameList;
    }

    public int getCount() {
        //得到操作数据库的实例
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 调用查找书库代码并返回数据源
        Cursor cursor = db.rawQuery("select count(*)from BlackName", null);
        //游标移到第一条记录准备获取数据
        cursor.moveToFirst();
        // 获取数据中的LONG类型数据
        int count = Integer.valueOf(cursor.getString(0));
        Log.v("Shortcut", "count=" + count);
        return count;
    }
}
