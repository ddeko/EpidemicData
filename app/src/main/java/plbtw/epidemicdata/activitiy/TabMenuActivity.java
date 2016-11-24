package plbtw.epidemicdata.activitiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;


import net.yanzm.mth.MaterialTabHost;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import plbtw.epidemicdata.callbacks.OnActionbarListener;
import plbtw.epidemicdata.fragment.ChallangeFragment;
import plbtw.epidemicdata.fragment.LokasiFragment;
import plbtw.epidemicdata.fragment.RedeemFragment;
import plbtw.epidemicdata.fragment.RewardsFragment;
import plbtw.epidemicdata.R;


public class TabMenuActivity extends BaseActivity implements TabHost.OnTabChangeListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MaterialTabHost tabHost;

    public ViewPager getViewPager() {
        return viewPager;
    }
    List<Fragment> listFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        tabHost = (MaterialTabHost) findViewById(R.id.tabhost);
        tabHost.setType(MaterialTabHost.Type.FullScreenWidth);
        tabHost.setup();
        tabHost.setBackgroundColor(getResources().getColor(R.color.white));

        setRightIcon(R.drawable.ic_refresh_white);


        String[] tabnames = {"CHALLENGE", "REWARDS", "REDEEM"};

        for (int i = 0; i < tabnames.length; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabnames[i]);
            tabSpec.setIndicator(tabnames[i]);
            tabSpec.setContent(new FakeContent(this));
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new ChallangeFragment());
        listFragments.add(new RewardsFragment());
        listFragments.add(new RedeemFragment());


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), listFragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOnPageChangeListener(tabHost);

        int selectedItem = tabHost.getCurrentTab();

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View v = tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(17);

            if (i == selectedItem)
                tv.setTextColor(getResources().getColor(R.color.actionbar_dark_color));
            else
                tv.setTextColor(getResources().getColor(R.color.fbutton_color_asbestos));
        }

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                onBackPressed();
            }

            @Override
            public void onRightIconClick() {

                refresh();

            }
        });
    }

    public void refresh()
    {
        ((ChallangeFragment)listFragments.get(0)).getChallangeList();
        ((RewardsFragment)listFragments.get(1)).getRewardsList();
        ((RedeemFragment)listFragments.get(2)).getRedeemList();
    }
    @Override
    public int getLayout() {
        return R.layout.activity_tab_menu;
    }

    @Override
    public void updateUI() {



    }

    @Override
    public void onTabChanged(String tabId) {
        int selectedItem  = tabHost.getCurrentTab();

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View v = tabHost.getTabWidget().getChildAt(i);

            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);

            if(i==selectedItem)
                tv.setTextColor(getResources().getColor(R.color.actionbar_dark_color));
            else
                tv.setTextColor(getResources().getColor(R.color.fbutton_color_asbestos));
        }

        viewPager.setCurrentItem(selectedItem);
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> listFragment;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragment) {
            super(fm);
            this.listFragment = listFragment;
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    }

    public class FakeContent implements TabHost.TabContentFactory
    {
        Context context;
        public FakeContent(Context context)
        {
            this.context = context;
        }


        @Override
        public View createTabContent(String s) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }
}
