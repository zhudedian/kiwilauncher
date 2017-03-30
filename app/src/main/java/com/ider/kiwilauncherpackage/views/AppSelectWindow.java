package com.ider.kiwilauncherpackage.views;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.ider.kiwilauncherpackage.R;
import com.ider.kiwilauncherpackage.util.AppAdapter;
import com.ider.kiwilauncherpackage.util.PackageHolder;
import com.ider.kiwilauncherpackage.util.QueryApplication;

import java.util.List;

/**
 * Created by Eric on 2017/3/13.
 */

public class AppSelectWindow implements View.OnKeyListener, AdapterView.OnItemClickListener{

    private Context mContext;
    private List<PackageHolder> allApps;
    private View baseView;
    private PopupWindow popWindow;
    private static AppSelectWindow INSTANCE;
    private OnAppSelectListener onAppSelectListener;

    public interface OnAppSelectListener{
        void onAppSelected(PackageHolder holder);
    }
    public void setOnAppSelectListener(OnAppSelectListener onAppSelectListener) {
        this.onAppSelectListener = onAppSelectListener;
    }


    private AppSelectWindow(Context context) {
        this.mContext = context;
    }

    public static AppSelectWindow getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new AppSelectWindow(context);
        }
        return INSTANCE;
    }


    public void showAppPopWindow(List<PackageHolder> packages,View baseView) {
        this.baseView = baseView;

        View view = View.inflate(mContext, R.layout.app_select_window, null);
        view.setOnKeyListener(this);

        GridView gridView = (GridView) view.findViewById(R.id.app_select_grid);
        gridView.setOnKeyListener(this);
        gridView.setOnItemClickListener(this);
        setupAppGrid(packages,gridView);

        this.popWindow = new PopupWindow(view, -1, -1, true);
        this.popWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        this.popWindow.showAtLocation(baseView, Gravity.CENTER, 0, 0);
    }


    private void setupAppGrid(List<PackageHolder> packages,GridView gridView) {
        allApps = QueryApplication.query(mContext);
        AppAdapter adapter = new AppAdapter(mContext, R.layout.app_select_item, allApps,packages);
        gridView.setAdapter(adapter);
    }



    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            this.popWindow.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        onAppSelectListener.onAppSelected(allApps.get(i));
        popWindow.dismiss();
    }
}
