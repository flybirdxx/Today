package com.yangh.today.mvp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.qmuiteam.qmui.arch.QMUIFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangH on 2019/2/27.
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {
    private final FragmentManager fm;
    private List<Fragment> fragmentList = new ArrayList<>();
    //    private final List<Fragment> fragmentList;

    public HomePageAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        //        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

}
