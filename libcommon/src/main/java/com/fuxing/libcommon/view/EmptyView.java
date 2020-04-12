package com.fuxing.libcommon.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.fuxing.libcommon.R;

/**
 * @author ：Create by lichunfu
 * @Date : 2020-04-12
 * Description:空数据页面
 **/
public class EmptyView extends LinearLayout {
    private ImageView icon;
    private Button action;
    private TextView title;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @SuppressLint("NewApi")
    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true);
        icon = findViewById(R.id.empty_icon);
        action = findViewById(R.id.empty_action);
        title = findViewById(R.id.empty_title);
    }


    public void setEmptyIcon(@DrawableRes int iconRes) {
        icon.setImageResource(iconRes);
    }

    public void setTitle(String text) {
        if (TextUtils.isEmpty(text)) {
            title.setVisibility(GONE);
        } else {
            title.setText(text);
            title.setVisibility(VISIBLE);
        }
    }

    public void setButton(String text, OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            action.setVisibility(GONE);
        } else {
            action.setText(text);
            action.setVisibility(VISIBLE);
            action.setOnClickListener(listener);
        }
    }
}
