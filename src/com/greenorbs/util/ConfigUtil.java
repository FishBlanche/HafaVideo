package com.greenorbs.util;

import java.util.ResourceBundle;

/**
 * 项目参数工具类
 * 
 * @author liangxing
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("config");

	/**
	 * 通过键获取String值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}

	/**
	 * 通过键获取int值
	 * 
	 * @param key
	 * @return
	 */
	public static final int getInt(String key) {
		try {
			return Integer.parseInt(bundle.getString(key));
		} catch (NumberFormatException e) {
			return -1;
		}
	}


	/**
	 * 获得不同操作系统的根路径
	 * 
	 * @return
	 */
	public static String getRootPath() {
		if (OSUtil.isWindows()) {
			return get("root.file.path.windows");
		} else if (OSUtil.isLinux()) {
			return get("root.file.path.linux");
		}
		return null;
	}

}
