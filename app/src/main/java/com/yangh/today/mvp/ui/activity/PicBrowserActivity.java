package com.yangh.today.mvp.ui.activity;

import android.app.Application;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.yangh.today.R;
import com.yangh.today.app.AppLifecyclesImpl;
import com.yangh.today.app.Constant;
import com.yangh.today.app.base.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yangH on 2019/3/3.
 */
public class PicBrowserActivity extends BaseActivity {

    public final static String ImgList_key = "image_List";
    public final static String GankEntity_key = "gank_list";
    public final static String CurrentPosition = "current_position";
    @BindView(R.id.vp_img_show)
    ViewPager vpImgShow;
    @BindView(R.id.tv_img_num)
    TextView tvImgNum;

    //    private ArrayList<String> imgUrlList;
    //    private ArrayList<GankEntity.ResultsBean> resultsBeans;
    //    private int current_pos;
    private Application application;

    private int clickposition;
    private PhotoView currentImg;
    private int currentPos;
    private ArrayList<String> imgUrlList;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        setWindowFullScreen();
        return R.layout.activity_pic_brow;
    }

    private void setWindowFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        application = AppLifecyclesImpl.getInstance();
        imgUrlList = getIntent().getStringArrayListExtra(PicBrowserActivity.ImgList_key);
        currentPos = getIntent().getIntExtra(PicBrowserActivity.CurrentPosition, 1);
        tvImgNum.setText(String.valueOf(currentPos + 1) + "/" + imgUrlList.size());
        initViewPager();

    }

    private void initViewPager() {
        vpImgShow.setAdapter(new ImgShowAdapter());
        vpImgShow.setPageTransformer(true, new DepthPageTransformer());
        vpImgShow.setCurrentItem(currentPos);
        vpImgShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                tvImgNum.setText(String.valueOf(position + 1) + "/" + imgUrlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private class ImgShowAdapter extends PagerAdapter {

        private LayoutInflater inflater;
        private View view;

        public ImgShowAdapter() {
            inflater = LayoutInflater.from(application);
        }

        @Override
        public int getCount() {
            return imgUrlList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            view = inflater.inflate(R.layout.img_show_item, container, false);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
            ImageView iv_fail = (ImageView) view.findViewById(R.id.iv_fail);
            iv_fail.setVisibility(View.GONE);
            ArmsUtils.obtainAppComponentFromContext(application)
                    .imageLoader()
                    .loadImage(application, ImageConfigImpl.builder()
                            .url(imgUrlList.get(position))
                            .imageView(photoView)
                            .build());
            photoView.setOnClickListener(v -> finishBrower());

            photoView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickposition = position;
                    currentImg = photoView;
                    showBottomSheet();
                    ToastUtils.showShort("长按事件");
                    return false;
                }
            });
            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(((View) object));
        }
    }

    private void showBottomSheet() {
        Dialog dialog = new Dialog(this, R.style.Bottom_dialog);
        View view = View.inflate(this, R.layout.bottom_sheet, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.bottom_dialog_anim);
        //设置对话框的大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.bottom_save).setOnClickListener(v -> ToastUtils.showLong("保存"));
        dialog.findViewById(R.id.bottom_share).setOnClickListener(v -> ToastUtils.showShort("分享"));
        dialog.findViewById(R.id.bottom_wall_paper).setOnClickListener(v -> ToastUtils.showShort("设置壁纸"));

        //        new QMUIBottomSheet.BottomListSheetBuilder(application)
        //                .addItem("保存")
        //                .addItem("分享")
        //                .addItem("设为壁纸")
        //                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
        //                    @RequiresApi(api = Build.VERSION_CODES.M)
        //                    @Override
        //                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
        //                        dialog.dismiss();
        //                        switch (position) {
        //                            case 0:
        //                                //保存图片
        //                                PermissionUtils.requestWriteSettings(new PermissionUtils.SimpleCallback() {
        //                                    @Override
        //                                    public void onGranted() {
        //                                        savePhoto();
        //                                    }
        //
        //                                    @Override
        //                                    public void onDenied() {
        //                                        ToastUtils.showLong("权限获取失败，请前往设置页面打开存储权限");
        //                                    }
        //                                });
        //                                break;
        //                            case 1:
        //                                IntentUtils.getShareImageIntent("妹子图片分享", imgUrlList.get(position));
        //                                break;
        //                            case 2:
        //                                //                                ToastUtils.showShort("功能暂未开放");
        //                                setWallPaper();
        //                                break;
        //                            default:
        //                                break;
        //                        }
        //                    }
        //                })
        //                .build()
        //                .show();
    }

    private void setWallPaper() {
        ToastUtils.showShort("正在设置壁纸。。。");
        currentImg.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(currentImg.getDrawingCache());
        currentImg.setDrawingCacheEnabled(false);
        if (bitmap == null) {
            ToastUtils.showShort("设置失败");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                WallpaperManager manager = WallpaperManager.getInstance(application);
                try {
                    manager.setBitmap(bitmap);
                    flag = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    flag = false;
                } finally {
                    if (flag) {
                        AppLifecyclesImpl.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShort("设置成功");
                            }
                        });
                    } else {
                        AppLifecyclesImpl.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showShort("设置失败");
                            }
                        });
                    }
                }
            }
        });
    }

    private void savePhoto() {
        ToastUtils.showLong("正在保存图片");
        Bitmap bitmap = Bitmap.createBitmap(currentImg.getDrawingCache());
        currentImg.setDrawingCacheEnabled(false);
        if (bitmap == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean savePic2Local = ImageUtils.save(bitmap, Constant.IMG_PATH, Bitmap.CompressFormat.JPEG);
                AppLifecyclesImpl.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (savePic2Local) {
                            ToastUtils.showShort("保存成功");
                        } else {
                            ToastUtils.showShort("保存失败");
                        }
                    }
                });
            }
        }).start();

    }

    private void finishBrower() {
        tvImgNum.setVisibility(View.GONE);
        this.finish();
        this.overridePendingTransition(0, R.anim.activity_exit);

    }

    @Override
    protected void doOnBackPressed() {
        finishBrower();
    }
}

