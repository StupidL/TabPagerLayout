package me.stupidme.stupidpagerlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import me.stupidme.tabpagerlayout.TabItem;
import me.stupidme.tabpagerlayout.TabPagerLayout;

public class MainActivity extends AppCompatActivity {

    private static final int[] mColors = {R.color.red_800, R.color.deep_orange_800,
            R.color.indigo_800, R.color.light_green_800, R.color.purple_800,
            R.color.teal_800, R.color.blue_grey_600, R.color.brown_600};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        TabPagerLayout tabPagerLayout = (TabPagerLayout) findViewById(R.id.tab_pager_layout);

        List<TabItem> items = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            TabItem item = new TabItem();
            item.setTabTitle("TAB" + i);
            item.setTabColor(getResources().getColor(mColors[i]));
            Fragment fragment = BlankFragment.newInstance(i + "");
            item.setTabFragment(fragment);
            items.add(item);
        }

        tabPagerLayout.addAllTabItems(items)
                .setToolbarTitle("TabPagerLayout")
                .setTabHeight(56)
                .setTabIndicatorColor(Color.WHITE)
                .setTabIndicatorHeight(8)
                .setTabNormalTextColor(Color.WHITE)
                .setTabSelectedTextColor(Color.BLACK)
                .showToolbarUp(true)
                .setTabPagerLayoutListener(new TabPagerLayout.TabPagerLayoutListener() {
                    @Override
                    public void setToolbarUpOnClickListener(ActionBar actionBar) {
                        //...
                    }
                });
    }
}
