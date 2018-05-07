package cn.ddcome.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestGetIP {

	
	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	 }
	
	public static String getIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        System.out.println(".......................................");
        System.out.println(ip);
        System.out.println(".......................................");
        return ip;
    }
	
	public static String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //��η���������ж��ipֵ����һ��ip������ʵip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        System.out.println("===============================");
        System.out.println(request.getRemoteAddr());
        System.out.println("===============================");
        return request.getRemoteAddr();
    }
	
	/** 
     * ��ȡ��ǰ����ip 
     * @param request 
     * @return 
     */  
//    public static String getIpAddr(HttpServletRequest request){  
//        String ipAddress = request.getHeader("x-forwarded-for");  
//            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//                ipAddress = request.getHeader("Proxy-Client-IP");  
//            }  
//            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
//            }  
//            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
//                ipAddress = request.getRemoteAddr();  
//                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
//                    //��������ȡ�������õ�IP  
//                    InetAddress inet=null;  
//                    try {  
//                        inet = InetAddress.getLocalHost();  
//                    } catch (UnknownHostException e) {  
//                        e.printStackTrace();  
//                    }  
//                    ipAddress= inet.getHostAddress();  
//                }  
//            }  
//            //����ͨ�����������������һ��IPΪ�ͻ�����ʵIP,���IP����','�ָ�  
//            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
//                if(ipAddress.indexOf(",")>0){  
//                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
//                }  
//            }  
//            System.out.println("===============================");
//            System.out.println(ipAddress);
//            System.out.println("===============================");
//            return ipAddress;   
//    }
    
    @Test
    public void testAddressUtils() {
    }
}
