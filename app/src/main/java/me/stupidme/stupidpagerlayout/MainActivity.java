package me.stupidme.stupidpagerlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.stupidme.tabpagerlayout.TabPagerLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabPagerLayout tabPagerLayout = (TabPagerLayout) findViewById(R.id.tab_pager_layout);

        tabPagerLayout.setColorArray(new int[]{Color.CYAN, Color.GRAY});
        tabPagerLayout.setTitleArray(new String[]{"Tab1", "Tab2"});

        Fragment fragment = BlankFragment.newInstance("1");
        Fragment fragment1 = BlankFragment.newInstance("2");
        List<Fragment> list = new ArrayList<>();
        list.add(fragment);
        list.add(fragment1);
        tabPagerLayout.setFragments(list);

    }
}
