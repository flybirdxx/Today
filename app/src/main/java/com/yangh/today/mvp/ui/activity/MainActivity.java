package com.yangh.today.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.yangh.today.R;
import com.yangh.today.mvp.adapter.HomePageAdapter;
import com.yangh.today.mvp.ui.fragment.home.HomeFragment;
import com.yangh.today.mvp.ui.fragment.info.InfoFragment;
import com.yangh.today.mvp.ui.fragment.mine.MineFragment;
import com.yangh.today.mvp.ui.fragment.walfare.WelfareFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.topBar)
    QMUITopBar topBar;

    @BindView(R.id.pager)
    QMUIViewPager pager;
    @BindView(R.id.tab_segment)
    QMUITabSegment tabSegment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTopBar();
        initTabAndPager();
    }

    private void initTopBar() {
        topBar.setTitle(getString(R.string.app_name));
        topBar.setTitleGravity(Gravity.CENTER);

    }


    private void initTabAndPager() {

        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new InfoFragment());
        adapter.addFragment(new WelfareFragment());
        adapter.addFragment(new MineFragment());
        adapter.notifyDataSetChanged();
        pager.setCurrentItem(0);

        int normalColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_gray_6);
        int selectColor = QMUIResHelper.getAttrColor(this, R.attr.qmui_config_color_blue);

        tabSegment.setDefaultNormalColor(normalColor);
        tabSegment.setDefaultSelectedColor(selectColor);
        tabSegment.setHasIndicator(false);
        tabSegment.addTab(new QMUITabSegment.Tab(ContextCompat.getDrawable(this, R.mipmap.barrage),
                ContextCompat.getDrawable(this, R.mipmap.barrage_fill),
                "首页", false));
        tabSegment.addTab(new QMUITabSegment.Tab(ContextCompat.getDrawable(this, R.mipmap.flashlight),
                ContextCompat.getDrawable(this, R.mipmap.flashlight_secected),
                "资讯", false));
        tabSegment.addTab(new QMUITabSegment.Tab(ContextCompat.getDrawable(this, R.mipmap.picture),
                ContextCompat.getDrawable(this, R.mipmap.picture_selectd),
                "FUN", false));
        tabSegment.addTab(new QMUITabSegment.Tab(ContextCompat.getDrawable(this, R.mipmap.emoji),
                ContextCompat.getDrawable(this, R.mipmap.emoji_fill),
                "我", false));
        tabSegment.setupWithViewPager(pager, false);
        tabSegment.setMode(QMUITabSegment.MODE_FIXED);
        tabSegment.setTabTextSize(16);

        tabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {

            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {

            }

            @Override
            public void onDoubleTap(int index) {
                tabSegment.hideSignCountView(index);
            }
        });
    }


}
