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
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
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
     * 获取当前网络ip 
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
//                    //根据网卡取本机配置的IP  
//                    InetAddress inet=null;  
//                    try {  
//                        inet = InetAddress.getLocalHost();  
//                    } catch (UnknownHostException e) {  
//                        e.printStackTrace();  
//                    }  
//                    ipAddress= inet.getHostAddress();  
//                }  
//            }  
//            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
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
