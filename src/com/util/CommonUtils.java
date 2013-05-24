/**
 * 
 */
package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: CommonUtils 
 * @Description: TODO
 * @author Bruce 
 * @date Oct 16, 2012 
 */
public class CommonUtils {
	/**
	 * 从需求字段中获取年份值
	 * 
	 * @param str
	 * @return
	 */
	public static String getYear(String str) {
		String res = "";
		if (str != null && str != "") {
			res = str.substring(2, 6);
		}

		return res;
	}

	/**
	 * 验证一个字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {

		Pattern p = Pattern.compile("\\d+");
		if (p.matcher(str).matches() && str.length() > 3 && str.length() < 6)
			return true;
		return false;
	}
	
	/**
	 * 当csv文件的某个列中存在分隔符,把其合并起来
	 */
	public static String[] formatCsvRow(String[] line){
		if(line == null || line.length == 0)
			return null;
		
		String[] tmp = line;
		int length = tmp.length;
		
		List<String> list = new ArrayList<String>();

		for(int i = 0;i < length;i++){
			
			StringBuffer buffer = new StringBuffer(tmp[i]);
			while(!isValidField(buffer.toString())){
				buffer.append(" " + tmp[++i]);
			}
			
			list.add(buffer.toString());
		}
		
		String[] result = list.toArray(new String[list.size()]);
		return result;
	}
	
	private static boolean isValidField(String str){
		if(str == null || "".equals(str.trim()))
			return true;
		
		int count = 0;
		for(int i = 0;i < str.length();i++){
			if(str.charAt(i) == '"')
				count ++;
		}
		
		if(count%2 == 0)
			return true;
		
		return false;
	}
	
	public static void main(String[] args){
		File file = new File("C:\\Users\\Bruce\\Desktop\\Outlook (6)\\test.txt");
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "gb2312"));
			
			String line = null;
			while((line = br.readLine()) != null ){
				String row[] = line.split(",", -1);
				
				String n[] = formatCsvRow(row);
				
				int i = 1;

				System.out.println(n.toString());
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/** 
	 * @Description: 转换日期格式 DD/MM/YYYY -> YYYY-MM-DD
	 * 
	 */  
	public static String formatDate(String lang) {
		String result = "";

		String str = "^(\\d{1,2}\\/\\d{1,2}\\/\\d{4})$";
		Pattern pattern = Pattern.compile(str);
		Matcher matcher = pattern.matcher(lang.trim());

		if (matcher.matches()) {
			String[] tmp = lang.split("/");
			result = tmp[2] + "-" + tmp[0] + '-' + tmp[1];
			return result;
		} else {
			return lang;
		}
	}
}
