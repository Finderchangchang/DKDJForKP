package liuliu.kp.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by Administrator on 2018/1/26.
 */

public class dd {
    AlertDialog dialog;

    boolean returnResult = false;
    //弹出框方法,确定按钮

    /**
     * Set a listener to be invoked when the positive button of the dialog is pressed.
     *
     * @param title          弹框标题
     * @param content        弹框内容
     * @param isShowCloseBtn True显示取消按钮，False只显示确定按钮 to use.
     * @return 返回True点击了确定，否则点击了取消
     */
    public boolean AlertDialog(android.content.Context active, String title, String content, boolean isShowCloseBtn) {
        AlertDialog dialog;
        if (isShowCloseBtn) {
            dialog = new AlertDialog.Builder(active)
                    .setTitle(title)
                    .setMessage(content)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            returnResult = true;
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            returnResult = false;
                        }
                    })
                    .show();
        } else {

            dialog = new AlertDialog.Builder(active)
                    .setTitle(title)
                    .setMessage(content)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            returnResult = true;
                        }
                    })
                    .show();
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);

        return returnResult;
    }

    ;
}
