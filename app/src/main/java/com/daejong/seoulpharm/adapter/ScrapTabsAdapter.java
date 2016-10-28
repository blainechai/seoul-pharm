package com.daejong.seoulpharm.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;

/**
 * Created by Hyunwoo on 2016. 10. 28..
 */
public class ScrapTabsAdapter extends FragmentPagerAdapter implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    /**
     * TabHost와 Pager를 연동하여 관리할 수 있도록 만든 class.
     */

    private final Context mContext;
    private final TabHost mTabHost;
    private final ViewPager mViewPager;
    private final FragmentManager mFragmentManager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();      // tab 리스트
    private final static String FIELD_KEY_PREFIX = "tabinfo";

    static final class TabInfo {            // Tab의 정보
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    static class DummyTabFactory implements TabHost.TabContentFactory {     // TabHost의 tabcontent에 Dummy view를 설정하여 화면에 보이지 않도록
        private final Context mContext;

        public DummyTabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    /* Constructor */
    public ScrapTabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
        this(activity, activity.getSupportFragmentManager(), tabHost, pager);
    }

    public ScrapTabsAdapter(Context context, FragmentManager fragmentManager, TabHost tabHost, ViewPager pager) {
        super(fragmentManager);
        mContext = context;
        mFragmentManager = fragmentManager;
        mTabHost = tabHost;
        mViewPager = pager;
        mTabHost.setOnTabChangedListener(this);
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    /* 메모리 부족 종료시 tab의 정보를 복원 */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        for (TabInfo info : mTabs) {
            String keyfield = FIELD_KEY_PREFIX + info.tag;
            info.fragment = mFragmentManager.getFragment(savedInstanceState, keyfield);
        }
    }

    /* 메모리 부족 종료시 tab의 정보를 저장 */
    public void onSaveInstanceState(Bundle outState) {
        for (TabInfo info : mTabs) {
            String keyfield = FIELD_KEY_PREFIX + info.tag;
            if (info.fragment != null) {
                mFragmentManager.putFragment(outState, keyfield, info.fragment);
            }
        }
    }

    /**
     * addTab()
     * TabsAdapter에 addTab 을 해 주면 TabInfo를 하나 생성 후 TabHost에 추가한 다음 notifyDataSetChanged()로 PagerAdapter를 다시 구동해준다.
     *
     * @param tabSpec
     * @param clss
     * @param args
     */
    public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
        tabSpec.setContent(new DummyTabFactory(mContext));
        String tag = tabSpec.getTag();
        TabInfo info = new TabInfo(tag, clss, args);
        mTabs.add(info);
        notifyDataSetChanged();
        mTabHost.addTab(tabSpec);
    }


    /* TabHost Implements ... */
    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabs.get(position);
        info.fragment = Fragment.instantiate(mContext, info.clss.getName(), info.args);
        return info.fragment;
    }

    /* TabChange Listener */
    /**
     * setOnTabChangeListener
     * TabsAdapter가 TabHost의 OnTabChangeListener를 받아서 처리하므로 OnTabChangeListener를 등록하여 처리해 주도록 한다.
     */
    TabHost.OnTabChangeListener mTabChangeListener;

    public void setOnTabChangedListener(TabHost.OnTabChangeListener listener) {
        mTabChangeListener = listener;
    }

    @Override
    public void onTabChanged(String tabId) {                // 탭이 변경되면
        int position = mTabHost.getCurrentTab();            // 1. 탭의 현재 위치를 가져와서
        mViewPager.setCurrentItem(position);                //    Pager에게 해당 위치로 페이지를 세팅
        if (mTabChangeListener != null) {
            mTabChangeListener.onTabChanged(tabId);
        }
    }

    public Fragment getTabFragment(int position) {
        TabInfo info = mTabs.get(position);
        if (info != null) {
            return info.fragment;
        }
        return null;
    }

    public Fragment getCurrentTabFragment() {
        return getTabFragment(mTabHost.getCurrentTab());
    }


    /* PageChange Listener */
    ViewPager.OnPageChangeListener mPageChangeListener;

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mPageChangeListener = listener;
    }

    /* ViewPager Implements ... */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mTabHost.setCurrentTab(position);
        widget.setDescendantFocusability(oldFocusability);
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mPageChangeListener != null) {
            mPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}