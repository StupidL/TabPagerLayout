package me.stupidme.tabpagerlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

import java.util.ArrayList;
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
     * A callback listener. You can add some methods to this interface to implements callback functions.
     */
    private TabPagerLayoutListener mListener;

    /**
     * A listener of TabLayout. We use it to change background colors when tab selected.
     */
    private TabLayout.OnTabSelectedListener mTabSelectedColorListener;

    private ViewPagerAdapter mAdapter;

    private Context mContext;

    private List<TabItem> mTabItems;

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
            initFields();
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
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);

        array.recycle();
    }

    private void initFields() {
        mTabItems = new ArrayList<>();
        mAdapter = new ViewPagerAdapter(((AppCompatActivity) mContext).getSupportFragmentManager(), mTabItems);
        mViewPager.setAdapter(mAdapter);

        mTabSelectedColorListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mToolbar.setBackgroundColor(mTabItems.get(tab.getPosition()).getTabColor());
                mTabLayout.setBackgroundColor(mTabItems.get(tab.getPosition()).getTabColor());
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
        mTabLayout.addOnTabSelectedListener(mTabSelectedColorListener);
    }

    public TabPagerLayout setTabPagerLayoutListener(TabPagerLayoutListener layoutListener) {
        mListener = layoutListener;
        mListener.setToolbarUpOnClickListener(mActionBar);
        return this;
    }

    public TabPagerLayout addOnTabSelectedListener(TabLayout.OnTabSelectedListener listener) {
        mTabLayout.addOnTabSelectedListener(listener);
        return this;
    }

    public TabPagerLayout addTabItem(int position, TabItem item) {
        mTabItems.add(position, item);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout addTabItem(TabItem item) {
        mTabItems.add(item);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout addAllTabItems(List<TabItem> items) {
        mTabItems.addAll(items);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout addAllTabItems(int position, List<TabItem> items) {
        mTabItems.addAll(position, items);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout removeTabItem(int position) {
        mTabItems.remove(position);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout removeTabItem(TabItem item) {
        mTabItems.remove(item);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout removeAllTabItems(List<TabItem> items) {
        mTabItems.removeAll(items);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public TabPagerLayout clearTabItems() {
        mTabItems.clear();
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<>();
        for (TabItem item : mTabItems) {
            list.add(item.getTabFragment());
        }
        return list;
    }

    public Fragment getFragment(int position) {
        return mTabItems.get(position).getTabFragment();
    }

    public TabPagerLayout setupBackgroundColor(int color) {
        mBackgroundColor = color;
        mToolbar.setBackgroundColor(mBackgroundColor);
        mTabLayout.setBackgroundColor(mBackgroundColor);
        return this;
    }

    public TabPagerLayout setToolbarTitle(String title) {
        mToolbarTitle = title;
        mActionBar.setTitle(mToolbarTitle);
        return this;
    }

    public TabPagerLayout setToolbarTitleColor(int color) {
        mToolbarTitleColor = color;
        mToolbar.setTitleTextColor(mToolbarTitleColor);
        return this;
    }

    public TabPagerLayout showToolbarUp(boolean show) {
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

}
