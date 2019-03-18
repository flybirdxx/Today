package com.yangh.today.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.yangh.today.R;
import com.yangh.today.app.AppLifecyclesImpl;
import com.yangh.today.app.utils.IntentUtils;
import com.yangh.today.di.component.DaggerDetailComponent;
import com.yangh.today.di.module.DetailModule;
import com.yangh.today.mvp.contract.DetailContract;
import com.yangh.today.mvp.presenter.DetailPresenter;
import com.yangh.today.mvp.weight.ProgressDialog;

import butterknife.BindView;

import static com.yangh.today.app.ARouterPaths.MAIN_DETAIL;

/**
 * Created by yangH on 2019/3/1.
 */
@Route(path = MAIN_DETAIL)
public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.View {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.web)
    WebView web;
    //    private GankEntity.ResultsBean resultsBean;
    private String url;
    private String title;
    private String desc;
    private CustomPopWindow popWindow;
    private QMUIAlphaImageButton qmuiAlphaImageButton;
    private Dialog mDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDetailComponent.builder()
                .appComponent(appComponent)
                .detailModule(new DetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        String descFlag = intent.getStringExtra(IntentUtils.WebDescFlag);
        desc = intent.getStringExtra(IntentUtils.WebDesc);
        url = intent.getStringExtra(IntentUtils.WebUrl);
        LogUtils.e("地址是++" + url);
        ToastUtils.showLong("地址是++" + url);
        //        resultsBean = ((GankEntity.ResultsBean) getIntent().getExtras()
        //                .getSerializable(EXTRA_DETAIL));
        //        mPresenter.getGirl();
        topBar.addLeftBackImageButton().setOnClickListener(v -> this.finish());

        qmuiAlphaImageButton = topBar.addRightImageButton(R.mipmap.more_normal, R.id.more_menu);
        qmuiAlphaImageButton.setOnClickListener(v -> {
            showPopMenu();
            //            showBotoomSheetList();
        });
        topBar.setTitle(desc);
        initWebView();
        web.loadUrl(url);
    }

    private void showPopMenu() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);
        handClickEvent(view);
        popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(view)
                .enableBackgroundDark(true)
                .setOutsideTouchable(true)
                .size(400, 400)
                .create()
                .showAsDropDown(qmuiAlphaImageButton, 0, 10);
    }

    private void handClickEvent(View view) {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (popWindow != null) {
                    popWindow.dissmiss();
                }
                switch (v.getId()) {
                    case R.id.copy:

                        mPresenter.copy(url);
                        break;
                    case R.id.open:
                        mPresenter.openWithBrowser(url);
                        break;
                    case R.id.share:
                        ToastUtils.showShort("分享链接");
                        //                IntentUtils.getShareTextIntent("Today链接分享" + url);
                        break;
                    default:
                        break;
                }
            }
        };

        view.findViewById(R.id.copy).setOnClickListener(listener);
        view.findViewById(R.id.open).setOnClickListener(listener);
        view.findViewById(R.id.share).setOnClickListener(listener);
    }


    //    private void showBotoomSheetList() {
    //        new QMUIBottomSheet.BottomListSheetBuilder(AppLifecyclesImpl.getInstance())
    //                .addItem("复制链接")
    //                .addItem("在浏览器打开")
    //                .addItem("分享链接")
    //                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
    //                    @Override
    //                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
    //                        dialog.dismiss();
    //                        switch (position) {
    //                            case 0:
    //                                mPresenter.copy(url);
    //                                break;
    //                            case 1:
    //                                mPresenter.openWithBrowser(url);
    //                                break;
    //                            case 2:
    //                                ToastUtils.showShort("分享链接");
    //                                //                IntentUtils.getShareTextIntent("Today链接分享" + url);
    //                                break;
    //                            default:
    //                                break;
    //                        }
    //                    }
    //                }).build()
    //                .show();
    //    }

    private void initWebView() {
        WebSettings settings = web.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        if (NetworkUtils.isConnected()) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        web.setWebViewClient(new WebViewClient() {
                                 @Override
                                 public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                                     WebView.HitTestResult hitTestResult = view.getHitTestResult();
                                     if (!TextUtils.isEmpty(url) && hitTestResult == null) {

                                         view.loadUrl(url);
                                         return true;

                                     }
                                     return super.shouldOverrideUrlLoading(view, request);
                                 }
                             }
        );

        //        web.loadUrl(url);
        web.setWebChromeClient(chromeClient);
    }
    private WebChromeClient chromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(mDialog==null){
                return;
            }
            if (newProgress ==100){
                mDialog.dismiss();
            }else {
                mDialog.show();
            }
        }
    };


    @Override
    public void onFavoriteChange(boolean isFavorite) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();
            return;
        }
        super.onBackPressed();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        web.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        web.onPause();
//
//        //保证了webView退出后不再有声音
//        web.reload();
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (web != null) {
//            web.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            web.destroy();
//        }
//        super.onDestroy();
//
//    }
}

