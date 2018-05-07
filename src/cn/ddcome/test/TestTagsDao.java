package cn.ddcome.test;

import org.junit.Test;

import cn.ddcome.dao.TagsDao;
import net.sf.json.JSONObject;

public class TestTagsDao {

	@Test
	public void test() {
		new TagsDao().selectAll();
	}
	
	@Test
	public void testInsert() {
		new TagsDao().selectAll();
		
		JSONObject param = new JSONObject();
		param.put("tag", "HTTPЭ��");
		new TagsDao().insertOne(param);
		
		new TagsDao().selectAll();
	}
}
