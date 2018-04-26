package com.alex.witAg.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by dth.
 * Des:
 * Date: 2017/2/9.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment;
    private List<String>   mTitleList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment) {
        super(fm);
        this.mFragment = mFragment;
    }

    /**
     * 接收传递的标题
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> mFragment, List<String> mTitleList) {
        super(fm);
        this.mFragment = mFragment;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return  mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        } else {
            return "";
        }
    }

    public void addFragmentList(List<Fragment> fragment) {
        this.mFragment.clear();
        this.mFragment = null;
        this.mFragment = fragment;
        notifyDataSetChanged();
    }
}
