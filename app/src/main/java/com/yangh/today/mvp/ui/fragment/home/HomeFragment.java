package com.yangh.today.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.yangh.today.R;
import com.yangh.today.mvp.adapter.HomeContentPageAdapter;

import butterknife.BindView;

/**
 * Created by yangH on 2019/2/26.
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.contentViewPage)
    ViewPager contentViewPage;
    private static String[] categories = {"Android", "iOS", "前端", "拓展资源", "瞎推荐"};
    @BindView(R.id.tab_category)
    TabLayout tabLayout;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        HomeContentPageAdapter adapter = new HomeContentPageAdapter(getChildFragmentManager(), categories);
        contentViewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(contentViewPage);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        contentViewPage.setOffscreenPageLimit(1);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

}
