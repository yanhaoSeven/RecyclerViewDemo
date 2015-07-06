package yh.com.recyclerviewdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import yh.com.recyclerviewdemo.MainActivity;
import yh.com.recyclerviewdemo.R;
import yh.com.recyclerviewdemo.adapter.MyAdapter;
import yh.com.recyclerviewdemo.data.MyData;
import yh.com.recyclerviewdemo.util.DividerItemDecoration;

import static yh.com.recyclerviewdemo.R.layout.fragment1;

/**
 * Created by muji on 2015/7/6.
 */
public class Fragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View rootView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private MyAdapter adapter;
    private List<String> list;
    private int upIndex = 0;//刷新下标
    private int index = 0;//加载更多下标
    private boolean loading = false;

    private Handler handler = new Handler();

    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            List<String> upListData = MyData.upListData(upIndex);
            adapter.upData(upListData);
            upIndex++;
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment1,null);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.mSwipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mrecyclerview);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        list = MyData.getListData(index);

        adapter = new MyAdapter(list, getActivity());


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(adapter);

        adapter.setmItemClick(new MyAdapter.ItemClick() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(getActivity(), "单击事件" + list.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        adapter.setmLongClick(new MyAdapter.LongClick() {
            @Override
            public void onLongItemClick(int position, View view) {
                Toast.makeText(getActivity(), "长按事件" + list.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!loading && adapter.getItemCount() == (manager.findLastVisibleItemPosition() + 1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loading = true;
                    Toast.makeText(getActivity(), "加载更多", Toast.LENGTH_SHORT).show();
                    int position = list.size();
                    index++;
                    List<String> list_loading = MyData.getListData(index);
                    adapter.addList(list_loading);
                    loading = false;
                }
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (!loading && manager.findLastVisibleItemPosition() == list.size() - 1) {
//                    loading = true;
//                    Toast.makeText(MainActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
//                    int position = list.size();
//                    index++;
//                    List<String> list_loading = MyData.getListData(index);
//                    adapter.addList(list_loading);
//                    loading = false;
//                }
                mSwipeRefreshLayout.setEnabled(manager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        return rootView;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        handler.removeCallbacks(r);
        handler.postDelayed(r, 3000);
    }
}
