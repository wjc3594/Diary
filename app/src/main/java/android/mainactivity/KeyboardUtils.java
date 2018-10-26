package android.mainactivity;

/**
 * Created by 15711 on 2018/10/18.
 */
import android.app.Activity;

import android.content.Context;

import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    /**

     * 隐藏键盘的方法

     *

     * @param context

     */

    public static void hideKeyboard(Activity context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        // 隐藏软键盘

        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);

    }

}

