package cn.ddcome.test;

import org.junit.Test;

import cn.ddcome.service.ArticlesService;

public class TestArticlesService {

	@Test
	public void testSelectOnePdf() {
		System.out.println(ArticlesService.getInstance().selectOnePdf());;
	}
}
