package com.celdev.thirtyjava.helpers;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/** This class contains Util methods for converting Dip to pixel
 *  (used in the custom View DiceCheckbox)
 * */
public class Utils {


    public static float convertDipToPixel(float dp, Context context){
        return dp * getDensityDPIDivDefault(context);
    }

    private static float getDensityDPIDivDefault(Context context) {
        return (float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT;
    }

}
