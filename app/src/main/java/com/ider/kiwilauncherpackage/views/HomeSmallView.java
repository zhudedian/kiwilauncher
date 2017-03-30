package com.ider.kiwilauncherpackage.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ider.kiwilauncherpackage.R;
import com.ider.kiwilauncherpackage.util.EntryAnimation;
import com.ider.kiwilauncherpackage.util.MyApplication;
import com.ider.kiwilauncherpackage.util.PackageHolder;
import com.ider.kiwilauncherpackage.util.QueryApplication;
import com.ider.kiwilauncherpackage.util.SelectImage;

import java.util.List;

/**
 * Created by Eric on 2017/3/13.
 */

public class HomeSmallView extends RelativeLayout implements View.OnClickListener,View.OnLongClickListener{
    private static final String TAG = "HomeSmallView";


    public Context mContext;
    private ObjectAnimator animator = null;
    private List<PackageHolder> packages;
    private ImageView thumbnailGrid;
    private TextView title;
    private String packageName;


    public HomeSmallView(Context context) {
        this(context, null);
    }

    public HomeSmallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnClickListener(this);
        setOnLongClickListener(this);

        LayoutInflater.from(context).inflate(R.layout.home_small_view, this);
        thumbnailGrid = (ImageView) findViewById(R.id.home_small_image);
        title = (TextView) findViewById(R.id.home_small_text);
        packages=null;

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        SharedPreferences preferences = getContext().getSharedPreferences("launcher_prefers", Context.MODE_PRIVATE);
        packageName = preferences.getString((String) getTag(), null);
        if (packageName!=null) {
            List<PackageHolder> apps = QueryApplication.query(getContext());
            if (apps.contains(new PackageHolder(packageName))) {
                SelectImage.selectImage(thumbnailGrid, packageName);
                PackageManager packageManager = MyApplication.getContext().getPackageManager();
                ApplicationInfo appInfo = null;
                try {
                    appInfo = packageManager.getApplicationInfo(packageName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                title.setText(appInfo.loadLabel(packageManager));
            }else {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString((String) getTag(), null);
                editor.apply();
                packageName=null;
            }
        }
        if (packageName==null){
            thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.app_add));
            title.setText("add");
        }
    }
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {

        if(gainFocus) {
            animator = EntryAnimation.createFocusAnimator(this);
        } else {

            if(animator != null && animator.isRunning()) {
                animator.cancel();
            }
            animator = EntryAnimation.createLoseFocusAnimator(this);

        }
        animator.start();
    }
    @Override
    public void onClick(View view) {
        if (packageName==null){
            showAppSelectWindow();
        }else {
            Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(packageName);
            getContext().startActivity(intent);
        }
    }
    @Override
    public boolean onLongClick(View view){
        thumbnailGrid.setImageBitmap(BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.mipmap.app_add));
        title.setText("add");
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("launcher_prefers", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString((String) getTag(), null);
        editor.apply();
        packageName=null;
        return true;
    }

    public void showAppSelectWindow() {
        AppSelectWindow appSelectWindow = AppSelectWindow.getInstance(getContext());
        appSelectWindow.setOnAppSelectListener(new AppSelectWindow.OnAppSelectListener() {
            @Override
            public void onAppSelected(PackageHolder holder) {
                SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("launcher_prefers", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if(holder.getPackageName().equals(preferences.getString("11", null))||holder.getPackageName().equals(preferences.getString("12", null)) ||
                        holder.getPackageName().equals(preferences.getString("13", null))|| holder.getPackageName().equals(preferences.getString("14", null))||
                        holder.getPackageName().equals(preferences.getString("15", null)) ||holder.getPackageName().equals(preferences.getString("16", null))||
                        holder.getPackageName().equals(preferences.getString("17", null))||holder.getPackageName().equals(preferences.getString("18", null))){
                    Toast.makeText(getContext(),"App already exists",Toast.LENGTH_SHORT).show();
                }else {
                    editor.putString((String) getTag(), holder.getPackageName());
                    editor.apply();
                }
                Log.i("selectwindow",holder.getPackageName() );

            }
        });
        appSelectWindow.showAppPopWindow(packages,thumbnailGrid);
    }
}
