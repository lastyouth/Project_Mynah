package com.seven.mynah_ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.brandontate.BTGridPager.*;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private BTFragmentGridPager.FragmentGridPagerAdapter mFragmentGridPagerAdapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BTFragmentGridPager mFragmentGridPager = (BTFragmentGridPager) findViewById(R.id.fragmentGridPager);


        mFragmentGridPagerAdapter = new BTFragmentGridPager.FragmentGridPagerAdapter() {

            List<MainFragment> mainFragmentArrayList = new ArrayList<MainFragment>();


            @Override
            public int rowCount() {
                return 10;
            }

            @Override
            public int columnCount(int row) {
                return 10;
            }

            @Override
            public Fragment getItem(BTFragmentGridPager.GridIndex index) {

                //MainFragment panelFrag1 = mainFragmentArrayList.get(index.getRow());

                MainFragment panelFrag1 = new MainFragment();

                panelFrag1.setGridIndex(index);
                //RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(400,500);
                //panelFrag1.getView().getLayoutParams().height = 400;

                return panelFrag1;
            }


        };

        mFragmentGridPager.setGridPagerAdapter(mFragmentGridPagerAdapter);
    }
}

