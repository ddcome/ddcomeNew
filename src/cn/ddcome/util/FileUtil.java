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
		//创建一个产生随机数的对象
        Random r = new Random();
        //创建一个存储随机数的集合
        Vector<Integer> v = new Vector<Integer>();
        //定义一个统计变量
        int count = 0;
        while(count < 10){
            int number = r.nextInt(20) + 1;
            //判断number是否在集合中存在
            if(!v.contains(number)){
                //不在集合中，就添加
                v.add(number);
                count++;
            }
        }
        //遍历输出
        for(int i : v){
            results+=i;
        }
        return results;
	}
	
	/**
	 * 生成一个包含日期的唯一的文件名
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		Format format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(new Date());
		return today+randomNumber();
	}
	
	
	/**
	 * 创建一个可行的文件夹，无则创建。
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
