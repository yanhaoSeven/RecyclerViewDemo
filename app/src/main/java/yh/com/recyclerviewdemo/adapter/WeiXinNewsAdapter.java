package yh.com.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import yh.com.recyclerviewdemo.MyApplication;
import yh.com.recyclerviewdemo.R;
import yh.com.recyclerviewdemo.bean.WeiXinAPI;

/**
 * Created by muji on 2015/7/8.
 */
public class WeiXinNewsAdapter extends RecyclerView.Adapter<WeiXinNewsAdapter.MyWeiXinHolder> {
    private Context context;
    private List<WeiXinAPI> list;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private OnItemClick mOnItemClick;

    public OnItemClick getmOnItemClick() {
        return mOnItemClick;
    }

    public void setmOnItemClick(OnItemClick mOnItemClick) {
        this.mOnItemClick = mOnItemClick;
    }

    public WeiXinNewsAdapter(Context context, List<WeiXinAPI> list) {
        this.context = context;
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<WeiXinAPI>();
        }
        this.inflater = LayoutInflater.from(context);
        this.loader = ImageLoader.getInstance();
    }

    @Override
    public MyWeiXinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.weixinnewsitem, parent, false);
        return new MyWeiXinHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyWeiXinHolder holder, int position) {
        loader.displayImage(list.get(position).getFirstImg(), holder.iv_img, MyApplication.getCacheOptions(0));
        holder.tv_title.setText("title:" + list.get(position).getTitle());
        holder.tv_source.setText("source:" + list.get(position).getSource());
        holder.ll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick!=null){
                    int position=holder.getLayoutPosition();
                    mOnItemClick.onItemClick(position,v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyWeiXinHolder extends RecyclerView.ViewHolder {

        private ImageView iv_img;
        private TextView tv_title, tv_source;
        private LinearLayout ll_view;

        public MyWeiXinHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_source = (TextView) itemView.findViewById(R.id.tv_source);
            ll_view= (LinearLayout) itemView.findViewById(R.id.ll_view);
        }
    }

    public interface OnItemClick{
        void onItemClick(int position,View view);
    }
}
