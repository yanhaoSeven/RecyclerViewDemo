package yh.com.recyclerviewdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import yh.com.recyclerviewdemo.R;
import yh.com.recyclerviewdemo.adapter.WeiXinNewsAdapter;
import yh.com.recyclerviewdemo.bean.WeiXinAPI;
import yh.com.recyclerviewdemo.db.DbManager;
import yh.com.recyclerviewdemo.util.Contant;
import yh.com.recyclerviewdemo.util.OkHttpUtil;
import yh.com.recyclerviewdemo.util.ThreadPoolManager;

/**
 * Created by muji on 2015/7/6.
 */
public class Fragment2 extends Fragment {

    private View rootView;
    private RecyclerView rlv;
    private LinearLayoutManager manager;
    private WeiXinNewsAdapter adapter;
    private List<WeiXinAPI> list_weixin = new ArrayList<WeiXinAPI>();
    private DbManager dbManager = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String json = (String) msg.obj;
                    getGsonString(json);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment2, null);
        rlv = (RecyclerView) rootView.findViewById(R.id.rlv_img);
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rlv.setLayoutManager(manager);
        dbManager = new DbManager(getActivity());
        Runnable r = new Runnable() {
            @Override
            public void run() {
                list_weixin = dbManager.getCaChe();
                if (list_weixin.size() <= 0) {
                    getNetWorkImg();
                } else {
                    adaterData(list_weixin);
                }
            }
        };
        ThreadPoolManager.getInstance().addTask(r);

        return rootView;
    }

    private void getNetWorkImg() {
        final Request request = new Request.Builder().url(Contant.WEIXINAPI).build();
        Response response = null;
        try {
            response = OkHttpUtil.enqueue(request, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Snackbar.make(rootView, "请求失败", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String result = response.body().string();
                    Message msg = handler.obtainMessage();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析json数据
     *
     * @param result
     * @return
     */
    private void getGsonString(String result) {
        String str = "";
        try {
            JSONObject jsonObject = new JSONObject(result);
            String json_result = jsonObject.getString("result");
            JSONObject json_list = new JSONObject(json_result);
            str = json_list.getString("list");
            Gson g = new Gson();
            List<WeiXinAPI> list_json = g.fromJson(str, new TypeToken<List<WeiXinAPI>>() {
            }.getType());
            list_weixin.clear();
            list_weixin.addAll(list_json);
            for (WeiXinAPI weixin : list_weixin) {
                dbManager.addCache(weixin);
            }
            adaterData(list_weixin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adaterData(List<WeiXinAPI> list) {
        adapter = new WeiXinNewsAdapter(getActivity(), list);
        rlv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setmOnItemClick(new WeiXinNewsAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position, View view) {
                Snackbar.make(view, "===>", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

}
