package yh.com.recyclerviewdemo.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muji on 2015/7/3.
 */
public class MyData {

    public static List<String> getListData(int index) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("下标==>" + index + " 数据===>" + i);
        }
        return list;
    }


}
