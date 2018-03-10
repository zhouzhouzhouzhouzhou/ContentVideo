package com.example.content.Util;

import android.content.Context;
import android.util.TypedValue;

public class DensityUtils {

	/**
	 * dp转px
	 */
	public static int dp2px(Context ctx, float dp) {
		float density = ctx.getResources().getDisplayMetrics().density;
		int px = (int) (dp * density + 0.5f);// 4.9->5 4.4->4

		return px;
	}

	public static float px2dp(Context ctx, int px) {
		float density = ctx.getResources().getDisplayMetrics().density;
		float dp = px / density;

		return dp;
	}
	
	 public static int dpToPx(int dp, Context context) {
	        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	    }

	    public static int spToPx(int sp,Context context) {
	        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
	    }
	
	
}
