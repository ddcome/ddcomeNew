package cn.ddcome.service;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.TagsDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TagsService implements ServiceImpl{
	public static TagsService tagsService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static TagsService getInstance() {
		if(tagsService==null) {
			tagsService = new TagsService();
		}
		return tagsService;
	}

	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
		System.out.println(condition);
	}

	/**
	 * ��ѯ
	 * @return
	 */
	public JSONObject select() {
		JSONObject result = new TagsDao().selectAll();
		System.out.println(result);
		return result;
	}
	
	/**
	 * ����
	 * @return
	 */
	public JSONObject insert() {
		System.out.println(condition);
		//1.Ҫ�Դ��ݹ���������������
		JSONArray newTags = condition.getJSONObject("tags").getJSONArray("newTags");
		System.out.println(newTags);
		JSONArray oldTags = condition.getJSONObject("tags").getJSONArray("oldTags");
		System.out.println(oldTags);
		//2.���µ�����ִ��INSERT����
		JSONObject condi = new JSONObject();
		condi.put("mark", "insertSome");
		condi.put("tags", newTags);
		condi.put("sign", 777);
		System.out.println(condi.toString());
		new TagsDao().insertSome(condi);
		
		//3.�Ա�����������ִ�и��²���
		condi.put("mark", "updateSome");
		condi.put("tags", oldTags);
		condi.put("sign", 777);
		new TagsDao().updateSome(condi);
		
		//GC����
		condi = null;
		
		return null;
	}

}
