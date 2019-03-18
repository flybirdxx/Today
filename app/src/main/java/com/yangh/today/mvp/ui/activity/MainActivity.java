package com.yangh.today.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.FragmentUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yangh.today.R;
import com.yangh.today.app.ARouterPaths;
import com.yangh.today.app.utils.DialogHelper;
import com.yangh.today.mvp.adapter.HomePageAdapter;
import com.yangh.today.mvp.ui.fragment.HeWeatherFragment;
import com.yangh.today.mvp.ui.fragment.home.HomeFragment;
import com.yangh.today.mvp.ui.fragment.info.InfoFragment;
import com.yangh.today.mvp.ui.fragment.mine.MineFragment;
import com.yangh.today.mvp.ui.fragment.walfare.WelfareFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.topBar)
    Toolbar topBar;
    @BindView(R.id.pager)
    QMUIViewPager pager;
    @BindView(R.id.tab_segment)
    QMUITabSegment tabSegment;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTopBarAndDrawer();
        initTabAndPager();
    }


    private void initTopBarAndDrawer() {
        topBar.setTitle("Today");
        setSupportActionBar(topBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, topBar, R.string.OPEN_DRAWER, R.string.CLOSE_DRAWER);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        View headerView = navView.getHeaderView(0);
        ImageView mAvatar = headerView.findViewById(R.id.ic_avatar);
        TextView nickNme = headerView.findViewById(R.id.nick_name);
        TextView mIntro = headerView.findViewById(R.id.tv_intro);
        RelativeLayout header_bg = headerView.findViewById(R.id.nav_header_bg);

//        header_bg.setBackground(getResources().getDrawable(R.drawable.herder_bg));
        mAvatar.setImageResource(R.drawable.sjl);
        nickNme.setText("斯力嘉");

        navView.setNavigationItemSelectedListener(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pager = null;
        drawerLayout = null;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.type_gank:
                ToastUtils.showShort("Gank界面");
                break;
            case R.id.type_wan_android:
                ToastUtils.showShort("WanAndroid");
                ARouter.getInstance().build(ARouterPaths.WAN_ANDROID).navigation();
                //                requestPermission();
                break;
            case R.id.type_weather:
                ToastUtils.showLong("heweatherfragment");
                FragmentUtils.show(HeWeatherFragment.newInstance());
                break;
                default:
                    break;

        }
        return false;
    }

    private void requestPermission() {
        PermissionUtils.permission(PermissionConstants.LOCATION)
                .rationale(new PermissionUtils.OnRationaleListener() {
                    @Override
                    public void rationale(ShouldRequest shouldRequest) {
                        DialogHelper.showRationalDialog(shouldRequest);
                    }
                }).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                   ToastUtils.showShort("Successfull");
            }

            @Override
            public void onDenied() {
                DialogHelper.showOpenAppSettingDialog();
                PermissionUtils.launchAppDetailsSettings();
            }
        }).request();
    }

    public Toolbar getTopBar() {
        return topBar;
    }
}
