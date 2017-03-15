package me.stupidme.tabpagerlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import java.util.List;

/**
 * This is a View based on ViewPager and TabLayout.
 */

public class TabPagerLayout extends CoordinatorLayout {

    private Toolbar mToolbar;

    private ActionBar mActionBar;

    private boolean mToolbarShowUp;

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    /**
     * Set background for toolbar and tabs at the same time. Default color is R.color.colorPrimary.
     */
    private int mBackgroundColor;

    /**
     * Color of text in tab when unselected. Default color is Color.WHITE.
     */
    private int mTabNormalTextColor;

    /**
     * Color of text in tab when selected. Default color is Color.WHITE.
     */
    private int mTabSelectedTextColor;

    /**
     * Color of tab indicator. Default color is Color.WHITE.
     */
    private int mTabIndicatorColor;

    /**
     * Height of tab indicator. Default is 8.
     */
    private float mTabIndicatorHeight;

    /**
     * Height of tab. Default is 50.
     */
    private float mTabHeight;

    /**
     * Title of Toolbar
     */
    private String mToolbarTitle;

    /**
     * Color of Toolbar's title. Default is Color.WHITE.
     */
    private int mToolbarTitleColor;

    /**
     * An array to store colors for tabs. Each tab's color is mColorArray[tabPosition].
     */
    private int[] mColorArray;

    /**
     * An array to store titles for tabs. Each tab's title is mTitleArray[tabPosition].
     */
    private String[] mTitleArray;

    /**
     * A callback listener. You can add some methods to this interface to implements callback functions.
     */
    private TabPagerLayoutListener mListener;

    /**
     * A listener of TabLayout. We use it to change background colors when tab selected.
     */
    private TabLayout.OnTabSelectedListener mTabSelectedColorListener;

    /**
     * A list to store fragments which is used by PagerAdapter.
     */
    private List<Fragment> mFragments;

    private ViewPagerAdapter mAdapter;

    private Context mContext;

    public TabPagerLayout(Context context) {
        this(context, null, 0);
    }

    public TabPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initView(context);
            initAttrs(context, attrs, defStyleAttr);
        }
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tabpagerlayout, this, true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        ((AppCompatActivity) context).setSupportActionBar(mToolbar);
        mActionBar = ((AppCompatActivity) context).getSupportActionBar();

        mContext = context;
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabPagerLayout, defStyleAttr, 0);
        mBackgroundColor = array.getColor(R.styleable.TabPagerLayout_backgroundColor,
                context.getResources().getColor(R.color.colorPrimary));
        mTabNormalTextColor = array.getColor(R.styleable.TabPagerLayout_tabNormalTextColor, Color.WHITE);
        mTabSelectedTextColor = array.getColor(R.styleable.TabPagerLayout_tabSelectedTextColor, Color.WHITE);
        mTabHeight = array.getDimension(R.styleable.TabPagerLayout_tabHeight, 50);

        mTabIndicatorColor = array.getColor(R.styleable.TabPagerLayout_tabIndicatorColor, Color.WHITE);
        mTabIndicatorHeight = array.getDimension(R.styleable.TabPagerLayout_tabIndicatorHeight, 8);

        mToolbarShowUp = array.getBoolean(R.styleable.TabPagerLayout_toolbarShowUp, false);
        mToolbarTitle = array.getString(R.styleable.TabPagerLayout_toolbarTitle);
        mToolbarTitleColor = array.getColor(R.styleable.TabPagerLayout_toolbarTitleColor, Color.WHITE);

        mToolbar.setBackgroundColor(mBackgroundColor);
        mTabLayout.setBackgroundColor(mBackgroundColor);
        mToolbar.setTitleTextColor(mToolbarTitleColor);
        mActionBar.setDisplayHomeAsUpEnabled(mToolbarShowUp);
        mToolbar.setTitle(mToolbarTitle);
        mToolbar.setTitleTextColor(mToolbarTitleColor);

        mTabLayout.setTabTextColors(mTabNormalTextColor, mTabSelectedTextColor);
        mTabLayout.setSelectedTabIndicatorColor(mTabIndicatorColor);
        mTabLayout.setSelectedTabIndicatorHeight((int) mTabIndicatorHeight);

        mTabLayout.setupWithViewPager(mViewPager);

        array.recycle();
    }

    public TabPagerLayout setTabPagerLayoutListener(TabPagerLayoutListener layoutListener) {
        mListener = layoutListener;
        mListener.setToolbarUpOnClickListener(mActionBar);
        return this;
    }

    public TabPagerLayout setFragments(List<Fragment> list) {
        mFragments = list;
        if (mTitleArray != null && mFragments != null) {
            mAdapter = new ViewPagerAdapter(((AppCompatActivity) mContext).getSupportFragmentManager(),
                    mTitleArray, mFragments);
            mViewPager.setAdapter(mAdapter);
        } else {
            throw new NullPointerException("titles array is null or fragment list is null");
        }
        return this;
    }

    public List<Fragment> getFragments() {
        return mFragments;
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }

    public TabPagerLayout setColorArray(int[] array) {
        mColorArray = array;
        if (mTabSelectedColorListener == null) {
            mTabSelectedColorListener = new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    mToolbar.setBackgroundColor(mColorArray[tab.getPosition()]);
                    mTabLayout.setBackgroundColor(mColorArray[tab.getPosition()]);
                    mTabLayout.setTabTextColors(mTabNormalTextColor, mTabSelectedTextColor);
                    mTabLayout.setSelectedTabIndicatorColor(mTabIndicatorColor);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            };
        }
        mTabLayout.addOnTabSelectedListener(mTabSelectedColorListener);
        return this;
    }

    public TabPagerLayout setTitleArray(String[] array) {
        mTitleArray = array;
        return this;
    }

    public TabPagerLayout setupBackgroundColor(int color) {
        mBackgroundColor = color;
        mToolbar.setBackgroundColor(mBackgroundColor);
        mTabLayout.setBackgroundColor(mBackgroundColor);
        return this;
    }

    public TabPagerLayout setToolbarTitle(String title) {
        mToolbarTitle = title;
        mToolbar.setTitle(mToolbarTitle);
        return this;
    }

    public TabPagerLayout setToolbarTitleColor(int color) {
        mToolbarTitleColor = color;
        mToolbar.setTitleTextColor(mToolbarTitleColor);
        return this;
    }

    public TabPagerLayout toolbarShowUp(boolean show) {
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    public TabPagerLayout setTabNormalTextColor(int color) {
        mTabNormalTextColor = color;
        mTabLayout.setTabTextColors(mTabNormalTextColor, mTabSelectedTextColor);
        return this;
    }

    public TabPagerLayout setTabSelectedTextColor(int color) {
        mTabSelectedTextColor = color;
        mTabLayout.setTabTextColors(Color.WHITE, mTabSelectedTextColor);
        return this;
    }

    public TabPagerLayout setTabTextColors(int normalColor, int selectedColor) {
        mTabNormalTextColor = normalColor;
        mTabSelectedTextColor = selectedColor;
        mTabLayout.setTabTextColors(mTabNormalTextColor, mTabSelectedTextColor);
        return this;
    }

    public TabPagerLayout setTabHeight(float height) {
        mTabHeight = height;
        LayoutParams params = (LayoutParams) mTabLayout.getLayoutParams();
        params.height = (int) mTabHeight;
        mTabLayout.setLayoutParams(params);
        return this;
    }

    public TabPagerLayout setTabIndicatorColor(int color) {
        mTabIndicatorColor = color;
        mTabLayout.setSelectedTabIndicatorColor(mTabIndicatorColor);
        return this;
    }

    public TabPagerLayout setTabIndicatorHeight(int height) {
        mTabIndicatorHeight = height;
        mTabLayout.setSelectedTabIndicatorHeight((int) mTabIndicatorHeight);
        return this;
    }


    public interface TabPagerLayoutListener {

        void setToolbarUpOnClickListener(ActionBar actionBar);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private String[] mTitles;

        private List<Fragment> mFragments;

        ViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> list) {
            super(fm);
            mTitles = titles;
            mFragments = list;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
