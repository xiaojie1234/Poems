package com.example.xiaojie.poem.Utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by xiaojie on 2017/7/11.
 */

public class Util {

    public static void skipActivity(Context context, Class another){
        Intent intent = new Intent();
        intent.setClass(context,another);
        context.startActivity(intent);
    }

}
