package cn.ddcome.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import cn.ddcome.util.MyCommonUtil;

public class TestMyCommonUtil {

	@Test
	public void testEncoderByMd5() throws NoSuchAlgorithmException, UnsupportedEncodingException{ 
		System.out.println(MyCommonUtil.EncoderByMd5("123456"));
	}
}
