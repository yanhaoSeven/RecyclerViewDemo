package yh.com.recyclerviewdemo.util;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by muji on 2015/7/7.
 */
public class LeakHelpr {
    private Context mCtx;
    private TextView mTextView;

    private static LeakHelpr ourInstance = null;

    public static LeakHelpr getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new LeakHelpr(context);
        }
        return ourInstance;
    }

    public void setRetainedTextView(TextView tv) {
        this.mTextView = tv;
        mTextView.setText(mCtx.getString(android.R.string.ok));
    }

    public void setRemoveTextView() {
        mTextView = null;
    }

    private LeakHelpr() {
    }

    private LeakHelpr(Context context) {
        this.mCtx = context;
    }
}
