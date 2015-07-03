package yh.com.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yh.com.recyclerviewdemo.R;

/**
 * Created by muji on 2015/7/3.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private List<String> list;
    private Context context;
    private LayoutInflater inflater;

    private ItemClick mItemClick;
    private LongClick mLongClick;

    public ItemClick getmItemClick() {
        return mItemClick;
    }

    public void setmItemClick(ItemClick mItemClick) {
        this.mItemClick = mItemClick;
    }

    public LongClick getmLongClick() {
        return mLongClick;
    }

    public void setmLongClick(LongClick mLongClick) {
        this.mLongClick = mLongClick;
    }

    public MyAdapter(List<String> list, Context context) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<String>();
        }
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.tv_view.setText("" + list.get(position).toString());
        holder.tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClick != null) {
                    int index = holder.getLayoutPosition();
                    mItemClick.onItemClick(index, v);
                }
            }
        });

        holder.tv_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClick != null) {
                    int index = holder.getLayoutPosition();
                    mLongClick.onLongItemClick(index, v);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private TextView tv_view;

        public MyHolder(View itemView) {
            super(itemView);
            tv_view = (TextView) itemView.findViewById(R.id.tv_view);
        }
    }

    public void addList(int position, List<String> list) {
        list.addAll(position, list);
        notifyItemInserted(position);
    }

    public interface ItemClick {
        void onItemClick(int position, View view);
    }

    public interface LongClick {
        void onLongItemClick(int position, View view);
    }


}
