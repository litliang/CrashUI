package com.open.auto.crashui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 错误处理类
 */
public class CrashUI implements UncaughtExceptionHandler {

    private UncaughtExceptionHandler mDefaultHandler;
    //instance
    private static CrashUI INSTANCE;
    //varaiable
    private Context mContext;
    //open or close this func
    private boolean debug = true;


    private CrashUI() {
    }

    public static CrashUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashUI();
        }
        return INSTANCE;
    }

    private boolean test;

    public CrashUI setTest(boolean test) {
        this.test = test;
        return this;
    }

    /**
     * @param ctx
     */
    Activity currActy;

    public void setCurrActy(Activity currAt) {
        this.currActy = currAt;
    }

    public CrashUI listen(Context ctx) {
        if (mDefaultHandler == null) {
            if (ctx instanceof Activity) {
                setCurrActy((Activity) ctx);
            }
            mContext = ctx;
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        return this;
    }

    public void maketest() {
        if (!errorDialog("")) {
            throw new RuntimeException("testing effect");
        }
    }

    public void makeExcep(String message) {
        throw new RuntimeException(TextUtils.isEmpty(message)?"testing effect":message);
    }

    @SuppressWarnings("-access")
    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        if (debug) {
            String result = getError(arg1);
            WriteFileUtil.writeTxtToFile(result, StorageUtil.getPrimaryStoragePath(mContext), "log.d");
            System.exit(0);
        }

    }


    public String getError(Throwable arg1) {
        String errorMsg = "界面： "
                + "\n";
        errorMsg += "原因：" + arg1.getMessage() + "\n" + "\n\n";
        String result = "";
        try {
            StackTraceElement[] eles = arg1.getStackTrace();
            for (int i = 0; i < eles.length; i++) {
                errorMsg += i + 1 + "：" + eles[i].toString() + "\n";
            }
            Logs.i("----- " + errorMsg);
            String emsg = ReadFileUtil.readsd(mContext, StorageUtil.getPrimaryStoragePath(mContext) + "/log.d");
            result = emsg + "\n" + errorMsg;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public boolean errorDialog(Throwable t) {
        errorDialog(getError(t));
        return false;
    }

    public void clearReport() {
        WriteFileUtil.writeTxtToFile("", StorageUtil.getPrimaryStoragePath(mContext), "log.d");
    }

    public void report() {
        errorDialog("");
    }

    public void work(Activity context) {
        listen(context);
        errorDialog("");
    }

    boolean reportedtest;

    public void dotest(String message) {
        if (!reportedtest) {
            makeExcep(message);
        } else {
            clearReport();
        }
    }

    public boolean errorDialog(String out) {
        if (TextUtils.isEmpty(out)) {
            out = ReadFileUtil.readsd(mContext, StorageUtil.getPrimaryStoragePath(mContext) + "/log.d");

        }
        Logs.i("---------- out " + out);
        if (out.equals("")) {
            reportedtest = false;
            return false;
        }
        SpannableStringBuilder spannable = new TextHighLightDecorator(
                Color.YELLOW).setMatcher(mContext.getPackageName())
                .getDecorated(out, out, out);
        DialogUtil.DialogInfo dialogInfo = new DialogUtil.DialogInfo(this.currActy);
        dialogInfo.aty = this.currActy;
        dialogInfo.message = spannable;
        dialogInfo.negativeButtonText = "发送";
        final String finalOut = out;
        dialogInfo.negativeButtonClickListener = new DialogUtil.DialogClicker() {

            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                // TODO Auto-generated method stub
                if (finalOut != null && !finalOut.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_TEXT, new String(finalOut));
                    intent.setType("text/plain");
                    mContext.startActivity(Intent.createChooser(intent,
                            "分享").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                super.onClick(dialog, arg1);

            }
        };
        DialogUtil.showChoiceDialog(dialogInfo, true).show();
        WriteFileUtil.writeTxtToFile("", StorageUtil.getPrimaryStoragePath(mContext), "log.d");
        reportedtest = true;
        return true;
    }
}