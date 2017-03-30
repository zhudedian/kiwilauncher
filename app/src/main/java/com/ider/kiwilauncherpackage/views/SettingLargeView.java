package com.ider.kiwilauncherpackage.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ider.kiwilauncherpackage.R;
import com.ider.kiwilauncherpackage.util.EntryAnimation;
import com.ider.kiwilauncherpackage.util.MyApplication;
import com.ider.kiwilauncherpackage.util.PackageHolder;

import java.util.List;

/**
 * Created by Eric on 2017/3/13.
 */

public class SettingLargeView extends RelativeLayout {
    private static final String TAG = "SettingLargeView";

    public Context mContext;
    private ImageView thumbnailGrid;
    private TextView title;


    public SettingLargeView(Context context) {
        this(context, null);
    }

    public SettingLargeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        LayoutInflater.from(context).inflate(R.layout.setting_large_view, this);
        thumbnailGrid = (ImageView) findViewById(R.id.setting_large_image);
        title = (TextView) findViewById(R.id.setting_large_text);

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (((String)getTag()).equals("21")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_wifi_ico));
            title.setText("Wi-Fi");
        }
        if (((String)getTag()).equals("22")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_eth_ico));
            title.setText("Mạng Dây");
        }
        if (((String)getTag()).equals("23")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_display_ico));
            title.setText("Hiển Thị");
        }
        if (((String)getTag()).equals("24")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_moreset_ico));
            title.setText("Cài Đặt");
        }
        if (((String)getTag()).equals("25")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_app_ico));
            title.setText("Q.Lý Ứng Dụng");
        }
        if (((String)getTag()).equals("26")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_canle_ico));
            title.setText("Ngày & Giờ");
        }
        if (((String)getTag()).equals("27")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_upgrade_ico));
            title.setText("Cập Nhật");
        }
        if (((String)getTag()).equals("28")) {
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.set_lang_ico));
            title.setText("Ngôn Ngữ");
        }
    }



}
