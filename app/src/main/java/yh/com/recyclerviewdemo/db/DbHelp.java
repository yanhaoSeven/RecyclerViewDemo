package yh.com.recyclerviewdemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by muji on 2015/7/8.
 */
public class DbHelp extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DATA_NAME = "BW_BASE_DATA";

    public DbHelp(Context context) {
        super(context, DATA_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS cache(wid text,title text,source text,firstImg text,mark text,url text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if (version < 1) {
            // 缓存表
            db.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS cache(wid text,title text,source text,firstImg text,mark text,url text)";
            try {
                db.execSQL(sql);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                version = 1;
                db.endTransaction();
            }
        }
    }
}
