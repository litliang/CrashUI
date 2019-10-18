package com.open.auto.crashui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/***
 * 弹框封装类
 * @author Administrator
 *
 */
public class DialogUtil {

    private static AlertDialog dialog;
    private static Dialog currentDialog;

    public static AlertDialog getCurrentDialog() {
        if (dialog!=null&&!dialog.isShowing()) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
        return dialog;
    }
    public static void cancelDialog() {
        Dialog dialog = getCurrentDialog();
        if(dialog!=null){
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }

    }

    public static Dialog showDialog(View view, int linecolor, ListView listview) {
        DialogInfo du = new DialogInfo(view.getContext());
        du.aty = view.getContext();
        du.view = listview;
        listview.setBackgroundResource(linecolor);
        du.neutralButtonClickListener = new DialogClicker();
        return DialogUtil.showNeutralDialog(du, true);
    }

    public static void setCurrentDialog(Dialog currentDialog) {
        DialogUtil.currentDialog = currentDialog;
    }

    public static class DialogClicker implements
            DialogInterface.OnClickListener {

        public void onClick(DialogInterface dialog, int arg1) {
            dialog.cancel();
            dialog.dismiss();
        }

    }

    public static final int loading = 0;

    public static class DialogInfo {
        public String title;
        public int iconId;
        public SpannableStringBuilder message;
        public String positiveButtonText;
        public DialogInterface.OnClickListener positiveButtonClickListener;
        public String negativeButtonText;
        public DialogInterface.OnClickListener negativeButtonClickListener;
        public String neutralButtonText;
        public DialogInterface.OnClickListener neutralButtonClickListener;
        public View view;

        public Context aty;

        public DialogInfo(Context context) {
            super();
            this.aty = context;
        }

    }

    public static DialogInterface.OnClickListener getNewCancelOption(
            Context activity) {
        return getNewCancelOption(activity, false);
    }

    public static DialogInterface.OnClickListener getNewCancelOption(
            final Context activity, final boolean destroyActivity) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();

            }
        };
    }

    public static AlertDialog showChoiceDialog(DialogInfo abtract) {

        AlertDialog dialog = showChoiceDialog(abtract, true);
        return dialog;
    }

    public static AlertDialog showChoiceDialog(DialogInfo abtract,
                                               boolean cancelable) {
        if (abtract.aty == null) {
            throw new IllegalStateException("contxet is null");
        }
        Builder dialog = new Builder(abtract.aty)
                .setCancelable(cancelable);
        if (abtract.positiveButtonText != null
                && abtract.positiveButtonClickListener != null) {
            dialog.setPositiveButton(abtract.positiveButtonText,

                    abtract.positiveButtonClickListener);

        }
        if (abtract.negativeButtonText != null
                && abtract.negativeButtonClickListener != null) {
            dialog.setNegativeButton(abtract.negativeButtonText,
                    abtract.negativeButtonClickListener);

        }

        if (abtract.neutralButtonText != null
                && abtract.neutralButtonClickListener != null) {
            dialog.setNeutralButton(abtract.neutralButtonText,
                    abtract.neutralButtonClickListener);

        }

        if (abtract.title != null) {
            dialog.setTitle(abtract.title);
        }
        if (abtract.iconId != 0) {
            dialog.setIcon(abtract.iconId);
        }
        if (abtract.message != null) {
            dialog.setMessage(abtract.message);
        }
        AlertDialog ad = dialog.create();

        return ad;
    }

    public static AlertDialog showNeutralDialog(DialogInfo abtract) {
        return showNeutralDialog(abtract, false);
    }

    public static AlertDialog showNeutralDialog(DialogInfo abtract,
                                                boolean cancable) {
        return new Builder(abtract.aty)
                .setTitle(abtract.title)
                .setIcon(abtract.iconId)
                .setMessage(abtract.message)
                .setNeutralButton(abtract.neutralButtonText,
                        abtract.neutralButtonClickListener)
                .setCancelable(cancable).setView(abtract.view).show();
    }

    public void closeHintNBackToLauncher(DialogInterface dialog, Context context) {
        dialog.dismiss();
        dialog.cancel();
        ((Activity) context).finish();
    }
}
