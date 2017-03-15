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

    /**
     * Toolbar
     */
    private Toolbar mToolbar;

    /**
     * ActionBar，可以设置属性
     */
    private ActionBar mActionBar;

    /**
     * TabLayout
     */
    private TabLayout mTabLayout;

    /**
     * ViewPager
     */
    private ViewPager mViewPager;

    /**
     * Toolbar和TabLayout的背景颜色
     */
    private int mBackgroundColor;

    /**
     * 是否在Toolbar上显示向上键
     */
    private boolean mToolbarShowUp;

    /**
     * TabLayout一栏标题颜色
     */
    private int mTabNormalTextColor;

    /**
     * TabLayout一栏被选中时的标题颜色
     */
    private int mTabSelectedTextColor;

    /**
     * TabLayout指示器颜色
     */
    private int mTabIndicatorColor;

    /**
     * TabLayout指示器高度
     */
    private float mTabIndicatorHeight;

    /**
     * TabLayout标签栏高度
     */
    private float mTabHeight;

    /**
     * Toolbar的标题
     */
    private String mToolbarTitle;

    /**
     * Toolbar标题颜色
     */
    private int mToolbarTitleColor;

    /**
     * 为TabLayout的各个标签设置不同的颜色
     */
    private int[] mColorArray;

    /**
     * 为TabLayout各个标签设置不同的标题
     */
    private String[] mTitleArray;

    private TabPagerLayoutListener mListener;

    private TabLayout.OnTabSelectedListener mTabSelectedColorListener;

    private List<Fragment> mFragments;

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

    /**
     * 初始化
     *
     * @param context 上下文
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.tabpagerlayout, this, true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        ((AppCompatActivity) context).setSupportActionBar(mToolbar);
        mActionBar = ((AppCompatActivity) context).getSupportActionBar();

        mContext = context;
    }

    /**
     * 初始化自定义属性
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr
     */
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

    /**
     * 设置监听器
     *
     * @param layoutListener 监听器
     * @return
     */
    public TabPagerLayout setTabPagerLayoutListener(TabPagerLayoutListener layoutListener) {
        mListener = layoutListener;
        mListener.setToolbarUpOnClickListener(mActionBar);
        return this;
    }

    /**
     * 设置每个标签的fragment
     *
     * @param list fragment集合
     * @return
     */
    public TabPagerLayout setFragments(List<Fragment> list) {
        mFragments = list;
        if (mTitleArray != null && mFragments != null) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(
                    ((AppCompatActivity) mContext).getSupportFragmentManager(), mTitleArray, mFragments);
            mViewPager.setAdapter(adapter);
        } else {
            throw new NullPointerException("titles array is null or fragment list is null");
        }
        return this;
    }

    /**
     * 获取Fragment列表
     *
     * @return fragment列表
     */
    public List<Fragment> getFragments() {
        return mFragments;
    }

    /**
     * 获取固定位置的fragment
     *
     * @param position 位置
     * @return fragment
     */
    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }

    /**
     * 设置颜色数组，使不同的标签可以有不同的颜色
     *
     * @param array 颜色数组
     */
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

    /**
     * 得到颜色数组
     *
     * @return 颜色数组
     */
    public int[] getColorArray() {
        return mColorArray;
    }

    /**
     * 设置TabLayout的标签标题
     *
     * @param array 标题数组
     */
    public TabPagerLayout setTitleArray(String[] array) {
        mTitleArray = array;
        return this;
    }

    /**
     * 得到标签标题
     *
     * @return 标题数组
     */
    public String[] getTitleArray() {
        return mTitleArray;
    }

    /**
     * 设置Toolbar和TabLayout的背景颜色
     *
     * @param color 背景颜色
     */
    public TabPagerLayout setupBackgroundColor(int color) {
        mBackgroundColor = color;
        mToolbar.setBackgroundColor(mBackgroundColor);
        mTabLayout.setBackgroundColor(mBackgroundColor);
        return this;
    }

    /**
     * 得到背景颜色
     *
     * @return 背景颜色
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * 设置Toolbar标题
     *
     * @param title 标题
     */
    public TabPagerLayout setToolbarTitle(String title) {
        mToolbarTitle = title;
        mToolbar.setTitle(mToolbarTitle);
        return this;
    }

    /**
     * 得到Toolbar标题
     *
     * @return 标题
     */
    public String getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 设置Toolbar标题颜色
     *
     * @param color 标题颜色
     */
    public TabPagerLayout setToolbarTitleColor(int color) {
        mToolbarTitleColor = color;
        mToolbar.setTitleTextColor(mToolbarTitleColor);
        return this;
    }

    /**
     * 得到Toolbar标题颜色
     *
     * @return 标题颜色
     */
    public int getToolbarTitleColor() {
        return mToolbarTitleColor;
    }

    /**
     * 是否显示向上键
     *
     * @param show 是否显示
     */
    public TabPagerLayout toolbarShowUp(boolean show) {
        mActionBar.setDisplayHomeAsUpEnabled(show);
        return this;
    }

    /**
     * 设置TabLayout标签正常文字颜色
     *
     * @param color 文字颜色
     */
    public TabPagerLayout setTabNormalTextColor(int color) {
        mTabNormalTextColor = color;
        mTabLayout.setTabTextColors(mTabNormalTextColor, mTabSelectedTextColor);
        return this;
    }

    /**
     * 得到TabLayout标签正常文字颜色
     *
     * @return 文字颜色
     */
    public int getTabNormalTextColor() {
        return mTabNormalTextColor;
    }

    /**
     * 设置选中标题颜色
     *
     * @param color 颜色
     * @return
     */
    public TabPagerLayout setTabSelectedTextColor(int color) {
        mTabSelectedTextColor = color;
        mTabLayout.setTabTextColors(Color.WHITE, mTabSelectedTextColor);
        return this;
    }

    /**
     * 设置TabLayout标签标题颜色
     *
     * @param normalColor   正常颜色
     * @param selectedColor 被选中颜色
     * @return
     */
    public TabPagerLayout setTabTextColors(int normalColor, int selectedColor) {
        mTabNormalTextColor = normalColor;
        mTabSelectedTextColor = selectedColor;
        mTabLayout.setTabTextColors(mTabNormalTextColor, mTabSelectedTextColor);
        return this;
    }

    /**
     * 获取选中颜色
     *
     * @return 颜色
     */
    public int getTabSelectedTextColor() {
        return mTabSelectedTextColor;
    }

    /**
     * 设置TabLayout标签栏的高度
     *
     * @param height 标签栏高度
     */
    public TabPagerLayout setTabHeight(float height) {
        mTabHeight = height;
        LayoutParams params = (LayoutParams) mTabLayout.getLayoutParams();
        params.height = (int) mTabHeight;
        mTabLayout.setLayoutParams(params);
        return this;
    }

    /**
     * 得到TabLayout标签栏高度
     *
     * @return 标签栏高度
     */
    public float getTabHeight() {
        return mTabHeight;
    }

    /**
     * 设置指示器颜色
     *
     * @param color 颜色
     * @return
     */
    public TabPagerLayout setTabIndicatorColor(int color) {
        mTabIndicatorColor = color;
        mTabLayout.setSelectedTabIndicatorColor(mTabIndicatorColor);
        return this;
    }

    /**
     * 获取指示器颜色
     *
     * @return 颜色
     */
    public int getTabIndicatorColor() {
        return mTabIndicatorColor;
    }

    /**
     * 设置指示器高度
     *
     * @param height 高度
     * @return
     */
    public TabPagerLayout setTabIndicatorHeight(int height) {
        mTabIndicatorHeight = height;
        mTabLayout.setSelectedTabIndicatorHeight((int) mTabIndicatorHeight);
        return this;
    }

    /**
     * 获取指示器高度
     *
     * @return 高度
     */
    public int getTabIndicatorHeight() {
        return (int) mTabIndicatorHeight;
    }


    /**
     * 回掉接口
     */
    public interface TabPagerLayoutListener {

        /**
         * 为ActionBar的向上键设置点击事件
         *
         * @param actionBar
         */
        void setToolbarUpOnClickListener(ActionBar actionBar);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private String[] mTitles;

        private List<Fragment> mFragments;

        public ViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> list) {
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
