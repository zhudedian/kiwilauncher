package com.ider.kiwilauncherpackage;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ider.kiwilauncherpackage.util.EntryAnimation;
import com.ider.kiwilauncherpackage.util.FullscreenActivity;
import com.ider.kiwilauncherpackage.util.IntentCreator;
import com.ider.kiwilauncherpackage.util.MyApplication;
import com.ider.kiwilauncherpackage.util.PackageHolder;
import com.ider.kiwilauncherpackage.util.QueryApplication;
import com.ider.kiwilauncherpackage.util.SetImageView;
import com.ider.kiwilauncherpackage.views.HomeLargeView;
import com.ider.kiwilauncherpackage.views.SettingLargeView;
import com.ider.kiwilauncherpackage.views.SwipeLayout;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.ider.kiwilauncherpackage.util.MyApplication.getContext;

public class MainActivity extends FullscreenActivity implements View.OnFocusChangeListener{

    private static final String TAG = "MainActivity";


    private ImageView stateWifi, stateBluetooth, usb;
    private View mainContainer;
    private HomeLargeView vApps;
    private SwipeLayout settingView;
    private SettingLargeView vSwipeWifi,vSwipeNet, vSwipeDisplay, vSwipeUpGrade, vSwipeApps,vSwipeSet,vSwipeTime,vSwipeLang;
    private boolean focusFlag = true;
    private Button homkey,setkey;
    private boolean show;
    private ObjectAnimator animator = null;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (focusFlag) {
            vApps.requestFocus();
            settingView.enableFocusable();
            focusFlag = false;
        }
    }
    @Override
    public void onFocusChange(View view, boolean hasFocus) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=true;

        mainContainer = findViewById(R.id.home_view);
        vApps = (HomeLargeView) findViewById(R.id.main_apps);
        stateWifi = (ImageView) findViewById(R.id.state_wifi);
        stateBluetooth = (ImageView) findViewById(R.id.state_bluetooth);
        usb = (ImageView) findViewById(R.id.state_usb);
        homkey = (Button) findViewById(R.id.hom_key);
        setkey = (Button) findViewById(R.id.set_key);
        homkey.setSelected(true);

        settingView = (SwipeLayout) findViewById(R.id.setting_view);
        vSwipeWifi = (SettingLargeView) findViewById(R.id.setting_large_image21);
        vSwipeNet = (SettingLargeView) findViewById(R.id.setting_large_image22);
        vSwipeDisplay = (SettingLargeView) findViewById(R.id.setting_large_image23);
        vSwipeSet = (SettingLargeView) findViewById(R.id.setting_large_image24);
        vSwipeApps = (SettingLargeView) findViewById(R.id.setting_large_image25);
        vSwipeTime = (SettingLargeView) findViewById(R.id.setting_large_image26);
        vSwipeUpGrade = (SettingLargeView) findViewById(R.id.setting_large_image27);
        vSwipeLang= (SettingLargeView) findViewById(R.id.setting_large_image28);




        SharedPreferences preferences = getSharedPreferences("launcher_prefers", MODE_PRIVATE);
        boolean firstIn  = preferences.getBoolean("first_in", false);
        if (!firstIn){
            setMainPackage();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("first_in",true);
            editor.apply();

        }
//        setImage();

        setListeners();
        bindReceivers();
//        setUsbState();

//        BluetoothManager btManager = (BluetoothManager) getSystemService(Service.BLUETOOTH_SERVICE);
//        if (btManager.getAdapter()!=null){
//            setBtState(btManager.getAdapter().getState());
//        }
    }


    private  void setMainPackage(){
        SharedPreferences preferences = getSharedPreferences("launcher_prefers", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("11", "com.android.vending");
        editor.putString("12","net.fptplay.ottbox");
        editor.putString("13","com.google.android.ogyoutube");
        editor.putString("14","vn.vasc.its.mytvnet");
        editor.putString("15","com.hdviet.android");
        editor.putString("16","com.epi.baomoi.touch");
        editor.putString("17","com.android.music");
        editor.putString("18","com.android.rockchip");
        editor.apply();
    }

//    private void setImage(){
//        vSwipeWifi.setImageBitmap(SetImageView.setImageView(R.mipmap.set_wifi_ico,"Wi_Fi"));
//        vSwipeNet.setImageBitmap(SetImageView.setImageView(R.mipmap.set_eth_ico,"Mạng Dây"));
//        vSwipeDisplay.setImageBitmap(SetImageView.setImageView(R.mipmap.set_display_ico,"Hiển Thị"));
//        vSwipeSet.setImageBitmap(SetImageView.setImageView(R.mipmap.set_moreset_ico,"Cài Đặt"));
//        vSwipeApps.setImageBitmap(SetImageView.setImageView(R.mipmap.set_app_ico,"Q.Lý Ứng Dụng"));
//        vSwipeTime.setImageBitmap(SetImageView.setImageView(R.mipmap.set_canle_ico,"Ngày & Giờ"));
//        vSwipeUpGrade.setImageBitmap(SetImageView.setImageView(R.mipmap.set_upgrade_ico,"Cập Nhật"));
//        vSwipeLang.setImageBitmap(SetImageView.setImageView(R.mipmap.set_lang_ico,"Ngôn Ngữ"));
//
//    }

    private void setListeners() {
        //最右边五个快捷划开功能的回调
        settingView.setSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onSwipeOpen() {
                if (show) {
//                    Log.i(TAG, "onSwipeOpen: ");
                    settingView.setVisibility(View.VISIBLE);
                    mainContainer.setVisibility(View.INVISIBLE);
                    Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.swipe_layout_in);
                    animator.setTarget(settingView);
                    animator.start();
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.content_swipe_off);
                    set.setTarget(mainContainer);
                    set.start();
                    show = false;
                    setkey.setSelected(true);
                    homkey.setSelected(false);
                }
            }

            @Override
            public void onSwipeClose() {
                if (!show) {
//                    Log.i(TAG, "onSwipeClose: ");
                    mainContainer.setVisibility(View.VISIBLE);
                    settingView.setVisibility(View.INVISIBLE);
                    Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.swipe_layout_out);
                    animator.setTarget(settingView);
                    animator.start();
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.content_swipe_on);
                    set.setTarget(mainContainer);
                    set.start();
                    show = true;
                    setkey.setSelected(false);
                    homkey.setSelected(true);
                }
            }
        });


        homkey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!show){
                    show = true;
                    homkey.setSelected(true);
                    mainContainer.setVisibility(View.VISIBLE);
                    Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.swipe_layout_out);
                    animator.setTarget(settingView);
                    animator.start();
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.content_swipe_on);
                    set.setTarget(mainContainer);
                    set.start();
                    setkey.setSelected(false);
                    settingView.setVisibility(View.INVISIBLE);
                }
            }
        });
        setkey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(show){
                    show = false;
                    setkey.setSelected(true);
                    settingView.setVisibility(View.VISIBLE);
                    Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.swipe_layout_in);
                    animator.setTarget(settingView);
                    animator.start();
                    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.content_swipe_off);
                    set.setTarget(mainContainer);
                    set.start();
                    homkey.setSelected(false);
                    mainContainer.setVisibility(View.INVISIBLE);
                }
            }
        });


        vApps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AppListActivity.class);
                startActivity(intent);
            }
        });


        vSwipeLang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                intent.setComponent(new ComponentName("com.rk_itvui.settings", "com.rk_itvui.settings.language.LanguageInputmethod"));
                startActivity(intent);
            }
        });
        vSwipeSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                startActivity(intent);
            }
        });
        vSwipeTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                intent.setComponent(new ComponentName("com.rk_itvui.settings", "com.rk_itvui.settings.datetime.DateTimeSetting"));
                startActivity(intent);
            }
        });
        vSwipeNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new IntentCreator(MainActivity.this).createWifiIntent();
                startActivity(intent);
            }
        });
        vSwipeWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new IntentCreator(MainActivity.this).createWifiIntent();
                startActivity(intent);
            }
        });
        vSwipeDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new IntentCreator(MainActivity.this).createDisplayIntent();
                startActivity(intent);
            }
        });
        vSwipeUpGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setComponent(new ComponentName("com.rk_itvui.settings", "com.rk_itvui.settings.upgrade.UpgradeActivity"));
                startActivity(intent);
            }
        });
        vSwipeApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new IntentCreator(MainActivity.this).createAppsIntent();
                startActivity(intent);
            }
        });
    }


    private void setUsbState() {

    }

    private boolean isUsbExists() {
        if(Build.MANUFACTURER.toLowerCase().equals("amlogic")) {
            if(Build.VERSION.SDK_INT >= 23) {
                return checkAmlogic6Usb();
            }
        }
        return checkNormalUsbExists();
    }

    private void setBtState(int state) {
        switch (state) {
            case BluetoothAdapter.STATE_ON:
                stateBluetooth.setVisibility(View.VISIBLE);
                stateBluetooth.setImageResource(R.drawable.ic_bluetooth_white_36dp);
                break;
            case BluetoothAdapter.STATE_OFF:
                stateBluetooth.setVisibility(View.GONE);
                break;
            case BluetoothAdapter.STATE_CONNECTED:
                stateBluetooth.setVisibility(View.VISIBLE);
                stateBluetooth.setImageResource(R.drawable.ic_bluetooth_connected_white_36dp);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    public void unregisterReceivers() {
        try {
            unregisterReceiver(netReceiver);
            unregisterReceiver(mediaReciever);
            unregisterReceiver(btReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindReceivers() {
        IntentFilter filter;
        // 外接u盘广播
        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter.addAction(Intent.ACTION_MEDIA_CHECKING);
        filter.addDataScheme("file");
        registerReceiver(mediaReciever, filter);

        filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(netReceiver, filter);

        filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(btReceiver, filter);
    }

    public static boolean isFirstIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("launcher_prefers", Context.MODE_PRIVATE);
        boolean firstIn = preferences.getBoolean("first_in", true);
        if (firstIn) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("first_in", false);
            editor.apply();
        }
        return firstIn;
    }

    BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateNetInfo();
        }
    };

    public void updateNetInfo() {
        if (isEthernetConnected()) {
            stateWifi.setImageResource(R.drawable.eth_ok);
            return;
        }
        if (isWifiConnected()) {
            stateWifi.setImageResource(R.drawable.ic_wifi_white_36dp);
            return;
        }
        stateWifi.setImageResource(R.drawable.ic_signal_wifi_off_white_36dp);
    }

    public boolean isEthernetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        return info.isConnected() && info.isAvailable();
    }

    public boolean isWifiConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return info.isConnected() && info.isAvailable();

    }

    BroadcastReceiver mediaReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("tag", action);
            String data = intent.getDataString();
            String path = data.substring(7);
            Log.i("tag", "onReceive: " + path);

            if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {

                if (path.contains("USB_DISK0") || path.contains("udisk0")) {
                    usb.setVisibility(View.VISIBLE);
                } else if (path.contains("USB_DISK1") || path.contains("udisk1")) {
                    usb.setVisibility(View.VISIBLE);
                } else if (path.contains("sdcard1")) {
                    usb.setVisibility(View.VISIBLE);
                }
            } else if (action.equals(Intent.ACTION_MEDIA_REMOVED) || action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
                if (path.contains("USB_DISK0") || path.contains("udisk0")) {
                    usb.setVisibility(View.GONE);
                } else if (path.contains("USB_DISK1") || path.contains("udisk1")) {
                    usb.setVisibility(View.GONE);
                } else if (path.contains("sdcard1")) {
                    usb.setVisibility(View.GONE);
                }
            }
        }
    };

    BroadcastReceiver btReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                setBtState(blueState);
            }
        }
    };


    /* Amlogic平台6.0以上系统 */
    private boolean checkAmlogic6Usb() {
        StorageManager mStorageManager = (StorageManager) getSystemService(Service.STORAGE_SERVICE);
        try {
            Class StorageManager = Class.forName("android.os.storage.StorageManager");
            Class VolumeInfo = Class.forName("android.os.storage.VolumeInfo");
            Class DiskInfo = Class.forName("android.os.storage.DiskInfo");

            Method getVolumes = StorageManager.getMethod("getVolumes");
            Method isMountedReadable = VolumeInfo.getMethod("isMountedReadable");
            Method getType = VolumeInfo.getMethod("getType");
            Method getDisk = VolumeInfo.getMethod("getDisk");

            Method isUsb = DiskInfo.getMethod("isUsb");
            Method getDescription = DiskInfo.getMethod("getDescription");
            List volumes = (List) getVolumes.invoke(mStorageManager);
            for(int i = 0; i < volumes.size(); i++) {
                if(volumes.get(i) != null && (boolean) isMountedReadable.invoke(volumes.get(i))
                        && (int) getType.invoke(volumes.get(i)) == 0) {
                    Object diskInfo = getDisk.invoke(volumes.get(i));
                    boolean usbExists = (boolean) isUsb.invoke(diskInfo);
                    String description = (String) getDescription.invoke(diskInfo);
                    Log.i(TAG, "isUsbExists: " + usbExists + ":" + description);
                    if(usbExists) {
                        return true;
                    }
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean checkNormalUsbExists() {
        String[] usbPaths = {"/storage/external_storage"};
        for (String usbPath : usbPaths) {
            File dir = new File(usbPath);
            if (dir.exists() && dir.isDirectory()) {
                if (dir.listFiles() != null) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        if(!show){
            show = true;
            homkey.setSelected(true);
            mainContainer.setVisibility(View.VISIBLE);
            Animator animator = AnimatorInflater.loadAnimator(MainActivity.this, R.animator.swipe_layout_out);
            animator.setTarget(settingView);
            animator.start();
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.content_swipe_on);
            set.setTarget(mainContainer);
            set.start();
            setkey.setSelected(false);
            settingView.setVisibility(View.INVISIBLE);
        }

        if(settingView.isSwipeShown()) {
//            vFolder.requestFocus();
        } else {
            vApps.requestFocus();
        }

    }
}
