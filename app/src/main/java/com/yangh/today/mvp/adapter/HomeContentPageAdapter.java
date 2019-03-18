package com.yangh.today.mvp.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yangh.today.mvp.ui.fragment.category.CategoryFragment;

/**
 * Created by yangH on 2019/2/27.
 */
public class HomeContentPageAdapter extends FragmentPagerAdapter {

    private final String[] categories;

    public HomeContentPageAdapter(FragmentManager fm, String[] categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int i) {
        CategoryFragment fragment = CategoryFragment.setArguments(categories[i]);
        Bundle bundle = new Bundle();
        bundle.putString("category", categories[i]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return categories != null ? categories.length : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories[position];
    }


}
