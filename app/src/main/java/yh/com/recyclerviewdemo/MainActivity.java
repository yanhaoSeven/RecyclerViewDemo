package yh.com.recyclerviewdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import yh.com.recyclerviewdemo.Data.MyData;
import yh.com.recyclerviewdemo.adapter.MyAdapter;
import yh.com.recyclerviewdemo.util.DividerItemDecoration;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
    }

    private void findViewById() {

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeColors(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.mrecyclerview);
        manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        list = MyData.getListData(index);

        adapter = new MyAdapter(list, MainActivity.this);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(adapter);

        adapter.setmItemClick(new MyAdapter.ItemClick() {
            @Override
            public void onItemClick(int position, View view) {
                Toast.makeText(MainActivity.this, "单击事件" + list.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        adapter.setmLongClick(new MyAdapter.LongClick() {
            @Override
            public void onLongItemClick(int position, View view) {
                Toast.makeText(MainActivity.this, "长按事件" + list.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!loading && manager.findLastVisibleItemPosition() == list.size() - 1) {
                    loading = true;
                    Toast.makeText(MainActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
                    int position = list.size();
                    index++;
                    List<String> list_loading = MyData.getListData(index);
                    adapter.addList(list_loading);
                    loading = false;
                }
            }
        });


    }

    //下拉刷新
    @Override
    public void onRefresh() {
        handler.removeCallbacks(r);
        handler.postDelayed(r, 3000);
    }
}
