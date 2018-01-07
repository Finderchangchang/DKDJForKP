package liuliu.kp.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import net.tsz.afinal.view.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liuliu.kp.R;
import liuliu.kp.base.BaseActivity;
import liuliu.kp.model.OrderModel;

/**
 * Created by Administrator on 2016/12/2.
 */

public class OrderListActivity extends BaseActivity {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    private String[] mTitles = {"全部", "待支付", "待接单", "取货中", "配送中", "已完成", "已取消"};
    private ViewPager order_list_vp;
    private ArrayList<Fragment> mFragments2 = new ArrayList<>();
    private OrderFragment orderFragment1;
    private OrderFragment orderFragment2;
    private OrderFragment orderFragment3;
    private OrderFragment orderFragment4;
    private OrderFragment orderFragment5;
    private OrderFragment orderFragment6;
    private OrderFragment orderFragment7;
    private TabLayout tab_FindFragment_title;
    List<OrderModel> orderModelList;
    public static OrderListActivity mIntails;

    @Override
    public void initViews() {
        setContentView(R.layout.activity_order_list);
        order_list_vp = (ViewPager) findViewById(R.id.order_list_vp);
        //ordel_list_tab= (CommonTabLayout) findViewById(R.id.ordel_list_tab);
        tab_FindFragment_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        order_list_vp.setOffscreenPageLimit(mTitles.length);
        ButterKnife.bind(this);
        mIntails = this;
        orderFragment1 = new OrderFragment();
        orderFragment2 = new OrderFragment();
        orderFragment3 = new OrderFragment();
        orderFragment4 = new OrderFragment();
        orderFragment5 = new OrderFragment();
        orderFragment6 = new OrderFragment();
        orderFragment7 = new OrderFragment();
        mFragments2.add(orderFragment1);
        mFragments2.add(orderFragment2);
        mFragments2.add(orderFragment3);
        mFragments2.add(orderFragment4);
        mFragments2.add(orderFragment5);
        mFragments2.add(orderFragment6);
        mFragments2.add(orderFragment7);
        titleBar.setLeftClick(() -> finish());
    }

    @Override
    public void initEvents() {
        tl_2();
        position = getIntent().getIntExtra("state", 0);
        loadTag();
    }

    int position = 0;

    private void tl_2() {
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[0]));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[1]));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[2]));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[3]));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[4]));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[5]));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(mTitles[6]));
        tab_FindFragment_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        order_list_vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tab_FindFragment_title.setupWithViewPager(order_list_vp);
        tab_FindFragment_title.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                loadTag();
                order_list_vp.setCurrentItem(tab.getPosition()); //解决单击Tab标签无法翻页的问题
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadTag() {
        TabLayout.Tab tab = tab_FindFragment_title.getTabAt(position);
        if (tab != null) {
            tab.select();
        }
        switch (position) {
            case 0:
                orderFragment1.refreshList(0);
                break;
            case 1:
                orderFragment2.refreshList(1);
                break;
            case 2:
                orderFragment3.refreshList(2);
                break;
            case 3:
                orderFragment4.refreshList(6);
                break;
            case 4:
                orderFragment5.refreshList(3);
                break;
            case 5:
                orderFragment6.refreshList(4);
                break;
            case 6:
                orderFragment7.refreshList(5);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments2.get(position);
        }
    }


}
