package com.example.content.Util;

import android.util.Log;

import com.example.content.Global.Constant;

/**
 * @author Administrator
 *
 */
public class LogUtils {
	/**
	 * 
     V — 明细 (最低优先级)
     D — 调试
     I — 信息
     W — 警告
     E — 错误
     F — 严重错误
     S — 无记载 (最高优先级，没有什么会被记载)
	 * 
	 * 
	 * 
	 */
	

	/**
	 * infomation
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (Constant.debugable) {
			Log.i(tag, msg);
		} 
	}
	
	/**
	 * 警告
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (Constant.debugable) {
			Log.e(tag, msg);
		} 
	}
	
	/**
	 * debug
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (Constant.debugable) {
			Log.d(tag, msg);
		} 
	}
	
	/**warn 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (Constant.debugable) {
			Log.w(tag, msg);
		} 
	}
	
	/**
	 * 明细
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (Constant.debugable) {
			Log.v(tag, msg);
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
