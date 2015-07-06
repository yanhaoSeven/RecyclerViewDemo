package yh.com.recyclerviewdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muji on 2015/7/6.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> list_fragment = new ArrayList<Fragment>();//fragment
    private List<String> list_fragmentTitle = new ArrayList<String>();//标题

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment,String title){
        list_fragment.add(fragment);
        list_fragmentTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_fragmentTitle.get(position).toString();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }
}
