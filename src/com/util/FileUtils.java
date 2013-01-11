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
	 * ����һ����ʱ�ļ���,���ҷ�������ļ��е�·��
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
	 * ɾ���ļ���
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
	 * ɾ������·�����ļ����Լ�Ŀ¼�µ��ļ�
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
	 * ɾ�������ļ�
	 * 
	 * @param str ��ɾ���ļ����ļ���
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
