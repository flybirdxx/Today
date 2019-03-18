package com.yangh.today.app.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yangh.today.mvp.contract.WelfareContract;
import com.yangh.today.mvp.model.entity.GankEntity;
import com.yangh.today.mvp.ui.activity.DetailActivity;
import com.yangh.today.mvp.ui.activity.PicBrowserActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangH
 * @date 2019/3/2
 */
public class IntentUtils {

    public static final String WebDescFlag = "WebDescFlag";
    public static final String WebDesc = "WebDesc";
    public static final String WebUrl = "WebUrl";

    public static void startToWebAactivity(Context context, String descFlag, String desc, String url) {
        Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
        intent.putExtra(WebDescFlag, descFlag);
        intent.putExtra(WebDesc, desc);
        intent.putExtra(WebUrl, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startToShowImg(Context context, ArrayList<String> data, ArrayList<GankEntity.ResultsBean> resultList, int pos, View view) {
        Intent intent = new Intent(context, PicBrowserActivity.class);
        intent.putExtra(PicBrowserActivity.ImgList_key, data);
        intent.putExtra(PicBrowserActivity.GankEntity_key, resultList);
        intent.putExtra(PicBrowserActivity.CurrentPosition, pos);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void start2ShowImg(Context context, ArrayList<String> data, int pos, View view) {
        Intent intent = new Intent(context, PicBrowserActivity.class);
        intent.putExtra(PicBrowserActivity.ImgList_key, data);
        intent.putExtra(PicBrowserActivity.CurrentPosition, pos);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
