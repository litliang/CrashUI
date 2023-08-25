package com.open.auto.crashui;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hp on 2018/3/31.
 */

public class ReadFileUtil {

    public static String readsd(Context aty, String filesbpath) {
        File f = new File(filesbpath);
        InputStream inputStream = null;
        try {
            f.getParentFile().mkdirs();
            if (!f.exists()) {
                f.createNewFile();
            }
            inputStream = new FileInputStream(new File( filesbpath));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        InputStream inputStream = null;
//        try {
//            inputStream = am.open(name);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String json = null;
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json.replace("\r\n","");
    }

    public static String readres(Context aty, int name) {
        InputStream inputStream = aty.getResources().openRawResource(name);
//        InputStream inputStream = null;
//        try {
//            inputStream = am.open(name);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String json = null;
        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String readAss(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets().open(fileName);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }

}