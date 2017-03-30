package com.ider.kiwilauncherpackage.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ider.kiwilauncherpackage.R;

import java.util.List;

/**
 * Created by Eric on 2017/3/13.
 */

public class AppAdapter extends BaseAdapter {
    private static final String TAG = "AppAdapter";

    private Context mContext;
    private int layoutId = R.layout.app_select_item;
    private List<PackageHolder> data;
    private List<PackageHolder> packages;

    public AppAdapter(Context mContext, List<PackageHolder> data) {
        this.mContext = mContext;
        this.data = data;
    }
    public AppAdapter(Context mContext, int layoutId, List<PackageHolder> data) {
        this(mContext, data);
        this.layoutId = layoutId;
    }

    public AppAdapter(Context mContext, int layoutId, List<PackageHolder> data,List<PackageHolder> packages) {
        this(mContext, data);
        this.layoutId = layoutId;
        this.packages= packages;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
            holder.image = (ImageView) view.findViewById(R.id.app_item_image);
            holder.image2 = (ImageView) view.findViewById(R.id.app_item_image2);
            holder.text = (TextView) view.findViewById(R.id.app_item_text);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String packageName = data.get(i).getPackageName();
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("launcher_prefers", Context.MODE_PRIVATE);
        if(packageName.equals(preferences.getString("11", null))||packageName.equals(preferences.getString("12", null))||packageName.equals(preferences.getString("13", null))||
                packageName.equals(preferences.getString("14", null))||packageName.equals(preferences.getString("15", null))||packageName.equals(preferences.getString("16", null))||
                packageName.equals(preferences.getString("17", null))||packageName.equals(preferences.getString("18", null))){
//            Log.i("adapter",packageName);
            holder.image2.setVisibility(View.VISIBLE);
        }else {
            holder.image2.setVisibility(View.GONE);
        }
        SelectImage.selectImage(holder.image,packageName);
        holder.text.setText(getPackageText(packageName));

        return view;
    }

    private Drawable getPackageDrawable(String pkgname) {
        try {
            PackageManager packageManager = mContext.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(pkgname, 0);
            return appInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getPackageText(String pkgname) {
        PackageManager packageManager = mContext.getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = packageManager.getApplicationInfo(pkgname, 0);
            return (String) appInfo.loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    class ViewHolder {
        private ImageView image;
        private ImageView image2;
        private TextView text;
    }
}
