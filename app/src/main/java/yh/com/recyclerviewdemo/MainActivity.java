package yh.com.recyclerviewdemo;

import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity {
    //新控件使用
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;
    private MyAdapter adapter;
    private List<String> list;
    private int index = 0;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
    }

    private void findViewById() {

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
}
