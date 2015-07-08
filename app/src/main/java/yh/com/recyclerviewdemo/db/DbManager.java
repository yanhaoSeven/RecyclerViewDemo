package yh.com.recyclerviewdemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import yh.com.recyclerviewdemo.bean.WeiXinAPI;

/**
 * Created by muji on 2015/7/8.
 */
public class DbManager {
    private DbHelp dbhelp;
    private SQLiteDatabase db;
    private static DbManager instance;

    public DbManager(Context context) {
        dbhelp = new DbHelp(context);
        db = dbhelp.getReadableDatabase();
    }

    public List<WeiXinAPI> getCaChe() {
        List<WeiXinAPI> list = new ArrayList<WeiXinAPI>();
        String sql = "select * from cache";
        Cursor c = null;
        try {
            c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                list.add(new WeiXinAPI(c.getString(c.getColumnIndex("wid")), c.getString(c.getColumnIndex("title")), c.getString(c.getColumnIndex("source")),
                        c.getString(c.getColumnIndex("firstImg")), c.getString(c.getColumnIndex("mark")), c.getString(c.getColumnIndex("url"))));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }
        }
        return list;
    }

    public void addCache(WeiXinAPI weiXinAPI){
        ContentValues values = new ContentValues();
        values.put("wid", weiXinAPI.getWid());
        values.put("title", weiXinAPI.getTitle());
        values.put("source", weiXinAPI.getSource());
        values.put("firstImg", weiXinAPI.getFirstImg());
        values.put("mark", weiXinAPI.getMark());
        values.put("url", weiXinAPI.getUrl());
        db.insert("cache",null,values);
    }

}
