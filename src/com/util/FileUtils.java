package com.util;

import java.io.File;
import java.util.UUID;

public class FileUtils {
	
	public static void main(String[] args) {
		String str = "C:\\Users\\Bruce\\AppData\\Local\\Temp\\458e0b5b-10f7-42b0-8609-7ce750d8f5f7";
		deleteFolder(str);
		System.out.println(System.getProperty("java.io.tmpdir"));
	}
	
	/**
	 * 创建一个零时文件夹,并且返回这个文件夹的路径
	 * 
	 * @return
	 */
	public static String getTmpRandom(){
		String tmp = System.getProperty("java.io.tmpdir");
		String uuid = UUID.randomUUID().toString();
		tmp = tmp + uuid;
		File folder = new File(tmp);
		
		if(!folder.exists()){
			folder.mkdir();
		}
		
		return tmp;
	}
	
	/**
	 * 删除文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteFolder(String dir){
		File file = new File(dir);
		boolean flag = false;
		if(!file.exists()){
			return flag;
		} else {
			if(file.isFile())
				return deleteFile(dir);
			else
				return deleteDirectory(dir);
		}
	}
	
	/**
	 * 删除给定路径的文件夹以及目录下的文件
	 * 
	 * @param path
	 * @return
	 */
	private static boolean deleteDirectory(String path){
		if(!path.endsWith(File.separator)){
			path = path + File.separator;
		}
		
		File file = new File(path);
		if(!file.exists() || !file.isDirectory()){
			return false;
		}
		
		boolean flag = true;
		File[] files = file.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isFile()){
				flag = deleteFile(files[i].getAbsolutePath());
				if(!flag) break;
			} else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if(!flag) break;
			}
		}
		
		if(!flag) return false;
		
		if(file.delete())
			return true;
		else
			return false;
		
	}
	
	
	/**
	 * 删除单个文件
	 * 
	 * @param str 被删除文件的文件名
	 * @return
	 */
	public static boolean deleteFile(String str){
		boolean flag = false;
		
		File file = new File(str);
		if(file.isFile() && file.exists()){
			flag = file.delete();
		}
		
		return flag;
	}
}
