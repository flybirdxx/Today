package com.yangh.today.mvp.weight;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yangh.today.R;

/**
 * Created by yangH on 2019/3/17.
 */
public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context,R.style.dialog_progress_doalog);
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);
    }
}
