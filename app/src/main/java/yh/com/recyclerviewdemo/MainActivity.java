package yh.com.recyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import yh.com.recyclerviewdemo.adapter.MyViewPagerAdapter;
import yh.com.recyclerviewdemo.fragment.Fragment1;
import yh.com.recyclerviewdemo.fragment.Fragment2;
import yh.com.recyclerviewdemo.fragment.Fragment3;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private NavigationView nav_view;
    private Toolbar toolbar;

    private TabLayout tab;
    private ViewPager viewPager;
    private MyViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
    }

    private void findViewById() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setHomeAsUpIndicator(R.drawable.ic_menu);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);

        nav_view = (NavigationView) findViewById(R.id.nav_view);

        if (nav_view != null) {
            setupDrawerContent(nav_view);
        }
        tab = (TabLayout) findViewById(R.id.tab);
        tab.setTabTextColors(Color.WHITE,Color.GRAY);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        if(tab!=null){
            tab.setupWithViewPager(viewPager);
        }

    }


    private void setupDrawerContent(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                if (menuItem.getItemId() == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "点击了:home1", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.nav_viewpager) {
                    Toast.makeText(MainActivity.this, "点击了:home2", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.nav_subsamplingScale) {
                    Toast.makeText(MainActivity.this, "点击了:home3", Toast.LENGTH_SHORT).show();
                } else if (menuItem.getItemId() == R.id.nav_gifview) {
                    Toast.makeText(MainActivity.this, "点击了:home4", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setupViewPager(ViewPager upViewPager) {
        viewPagerAdapter=new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new Fragment1(),"recyclerView");
        viewPagerAdapter.addFragment(new Fragment2(),"测试2");
        viewPagerAdapter.addFragment(new Fragment3(),"测试3");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
