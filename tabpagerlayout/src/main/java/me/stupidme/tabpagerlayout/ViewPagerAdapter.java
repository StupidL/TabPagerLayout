package me.stupidme.tabpagerlayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by StupidL on 2017/3/16.
 */

class ViewPagerAdapter extends FragmentPagerAdapter {

    public List<TabItem> mTabItems;

    ViewPagerAdapter(FragmentManager fm, List<TabItem> list) {
        super(fm);
        mTabItems = list;
    }

    @Override
    public int getCount() {
        return mTabItems.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mTabItems.get(position).getTabFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabItems.get(position).getTabTitle();
    }

}
