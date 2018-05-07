package cn.ddcome.test;

import org.junit.Test;

import cn.ddcome.service.CommentsService;

public class TestCommentsService {

	@Test
	public void testSelect() {
		System.out.println(CommentsService.getInstance().select(1));
	}
	
	@Test
	public void testFor() {
		for(int i=0; i<=10; i++) {
			if(i==5) {
				break;
			}
//			if(i<10) {
//				continue;
//			}
			System.out.println(i);
		}
	}
}
