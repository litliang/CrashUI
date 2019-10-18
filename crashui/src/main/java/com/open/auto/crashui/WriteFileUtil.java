package com.open.auto.crashui;

import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by hp on 2018/3/11.
 */

public class WriteFileUtil {
    public void initData() {
        String filePath = "/sdcard/Test/";
        String fileName = "log.txt";

        writeTxtToFile("txt content", filePath, fileName);
    }

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错

        makeFilePath(filePath, fileName);

        String strFilePath = filePath + "/" + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(0);
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + "/" + fileName);
            if (file.exists()) {
                file.delete();
            }
            if (!file.exists()) {
//                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
}
