package cn.ddcome.util;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class FileUtil {
	
	public static String randomNumber() {
		String results = "";
		//����һ������������Ķ���
        Random r = new Random();
        //����һ���洢������ļ���
        Vector<Integer> v = new Vector<Integer>();
        //����һ��ͳ�Ʊ���
        int count = 0;
        while(count < 10){
            int number = r.nextInt(20) + 1;
            //�ж�number�Ƿ��ڼ����д���
            if(!v.contains(number)){
                //���ڼ����У������
                v.add(number);
                count++;
            }
        }
        //�������
        for(int i : v){
            results+=i;
        }
        return results;
	}
	
	/**
	 * ����һ���������ڵ�Ψһ���ļ���
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		Format format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(new Date());
		return today+randomNumber();
	}
	
	
	/**
	 * ����һ�����е��ļ��У����򴴽���
	 * 
	 * @param path
	 */
	public static void createViableFolder(String path) {
		File file =new File(path);
		if (!file.exists() && !file.isDirectory()) {      
		    file.mkdir();   
		}
	}
}
