package com.open.auto.crashui;

/**
 * Created by hp on 2018/8/9.
 */

import android.content.Context;
import android.os.storage.StorageManager;
import android.util.Log;

import java.lang.reflect.Method;


public class StorageUtil {

    private static final String TAG = "===StorageUtil===";

    public static String getPrimaryStoragePath(Context context) {
        try {
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            String[] paths = (String[]) getVolumePathsMethod.invoke(sm);
            // first element in paths[] is primary storage path
            return paths[0];
        } catch (Exception e) {
            Log.e(TAG, "getPrimaryStoragePath() failed", e);
        }
        return null;
    }

    public static String getSecondaryStoragePath(Context context) {
        try {
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths");
            String[] paths = (String[]) getVolumePathsMethod.invoke(sm);
            // second element in paths[] is secondary storage path
            return paths[1];
        } catch (Exception e) {
            Log.e(TAG, "getSecondaryStoragePath() failed", e);
        }
        return null;
    }

    public static String getStorageState(Context context, String path) {
        try {
            StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Method getVolumeStateMethod = StorageManager.class.getMethod("getVolumeState", new Class[] {String.class});
            String state = (String) getVolumeStateMethod.invoke(sm, path);
            return state;
        } catch (Exception e) {
            Log.e(TAG, "getStorageState() failed", e);
        }
        return null;
    }
}