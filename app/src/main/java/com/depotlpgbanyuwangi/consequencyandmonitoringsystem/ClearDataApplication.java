package com.depotlpgbanyuwangi.consequencyandmonitoringsystem;

import android.app.Application;

import java.io.File;

public class ClearDataApplication extends Application{

    private static ClearDataApplication instance;

    public void onCreate(){
        super.onCreate();
        instance = this;
    }



    public static ClearDataApplication getInstance(){
        return instance;
    }

    public void clearApplicationData(){
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());

        if(appDir.exists()){
            String[] children = appDir.list();
            for (String s: children){
                if(!s.equals("lib")){
                    deleteDir(new File(appDir,s));
                }
            }
        }
    }

    public static boolean deleteDir(File dir){

        if(dir != null && dir.isDirectory()){
            String[] children = dir.list();

            for(int i = 0; i<children.length; ++i){
                boolean success = deleteDir(new File(dir,children[i]));
                if(!success){
                    return false;
                }
            }
        }

        return dir.delete();

    }

}
