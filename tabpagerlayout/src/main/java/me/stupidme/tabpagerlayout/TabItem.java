package me.stupidme.tabpagerlayout;

import android.support.v4.app.Fragment;

/**
 * Created by StupidL on 2017/3/16.
 */

public class TabItem {
    private String mTabTitle;
    private int mTabColor;
    private Fragment mTabFragment;

    public TabItem() {
    }

    public TabItem(String title, int color, Fragment fragment) {
        mTabTitle = title;
        mTabColor = color;
        mTabFragment = fragment;
    }

    public String getTabTitle() {
        return mTabTitle;
    }

    public TabItem setTabTitle(String mTabTitle) {
        this.mTabTitle = mTabTitle;
        return this;
    }

    public int getTabColor() {
        return mTabColor;
    }

    public TabItem setTabColor(int mTabColor) {
        this.mTabColor = mTabColor;
        return this;
    }

    public Fragment getTabFragment() {
        return mTabFragment;
    }

    public TabItem setTabFragment(Fragment mTabFragment) {
        this.mTabFragment = mTabFragment;
        return this;
    }
}
