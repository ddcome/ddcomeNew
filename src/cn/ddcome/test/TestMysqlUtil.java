package cn.ddcome.test;

import org.junit.Test;

import cn.ddcome.util.MysqlBeautyUtil;
import cn.ddcome.util.MysqlUtil;

/**
 * ≤‚ ‘mysqlπ§æﬂ¿‡
 * 
 * @author ddcome
 *
 */
public class TestMysqlUtil {
	
	@Test
	public void testMysqlBeautyUtil() {
		MysqlBeautyUtil.getConnection();
	}

	@Test
	public void test() {
		MysqlUtil.getInstance().getConnection();
	}
}
