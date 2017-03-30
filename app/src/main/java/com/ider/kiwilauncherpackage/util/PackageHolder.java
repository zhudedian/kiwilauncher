package com.ider.kiwilauncherpackage.util;

/**
 * Created by Eric on 2017/3/13.
 */

public class PackageHolder {


    private Long id;
    private String packageName;
    private String tag;

    public PackageHolder(Long id, String packageName, String tag) {
        this.id = id;
        this.packageName = packageName;
        this.tag = tag;
    }

    public PackageHolder(String packageName, String tag) {
        this.packageName = packageName;
        this.tag = tag;
    }
    public PackageHolder(String packageName){
        this.packageName = packageName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PackageHolder){
            PackageHolder holder2=(PackageHolder)obj;
            if (holder2.getPackageName().equals(this.getPackageName())){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    public PackageHolder() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
