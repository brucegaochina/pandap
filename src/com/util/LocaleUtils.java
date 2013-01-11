package com.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * internationalization 
 * 
 * @author Bruce
 *
 * @Date 2012-8-22
 */
public class LocaleUtils {
	
	public static String getLocal(String key){
		Locale locale = Locale.getDefault();
		ResourceBundle  bundle = ResourceBundle.getBundle("report", locale);
		return bundle.getString(key);
	}

}
