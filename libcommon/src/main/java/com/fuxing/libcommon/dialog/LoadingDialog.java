package com.fuxing.libcommon.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.fuxing.libcommon.R;
import com.fuxing.libcommon.utils.PixUtils;
import com.fuxing.libcommon.view.ViewHelper;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-12
 * Description:
 **/
public class LoadingDialog extends AlertDialog {
    private TextView loadingText;
    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context);
    }

    protected LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
    public  void  setLoadingText(String loadingText){
        if (this.loadingText!=null){
            this.loadingText.setText(loadingText);
        }
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.layout_loading_view);
        loadingText=findViewById(R.id.loading_text);
        Window window=getWindow();
        WindowManager.LayoutParams attributes=window.getAttributes();
        attributes.width=WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.height=WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity= Gravity.CENTER;
        attributes.dimAmount=0.5f;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ViewHelper.setViewOutLine(findViewById(R.id.loading_layout),
                PixUtils.dp2px(10),ViewHelper.RADIUS_ALL);

    }
}
