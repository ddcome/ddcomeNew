package cn.ddcome.service;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.ArticlesDao;
import cn.ddcome.dao.ArticlesTagsDao;
import cn.ddcome.dao.TagsDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArticlesTagsService implements ServiceImpl{
	public static ArticlesTagsService articlesTagsService = null;
	public JSONObject condition = null;

	public static ArticlesTagsService getInstance() {
		if(articlesTagsService==null) {
			articlesTagsService = new ArticlesTagsService();
		}
		return articlesTagsService;
	}
	
	@Override
	public void init(HttpServletRequest req) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ����
	 * @return
	 */
	public JSONObject insert() {
		JSONObject condi = new JSONObject();
		condi.put("sign", 777);
		//1.�Ȳ�ѯ��ǩ��ID
		JSONObject tags = new TagsDao().selectSome(condi);
		//2.��ѯarticles��ID
		JSONObject article = new ArticlesDao().selectSome(condi);
		//3.��Ϲ�����insert������condition
		JSONArray res = new JSONArray();
		JSONArray tagsArr = tags.getJSONArray("data");
		JSONArray articleArr = article.getJSONArray("data");
		//�쳣�����
		if(articleArr.size()==0) {
			return null;
		}
		for(int i=0; i<tagsArr.size(); i++) {
			JSONObject one = new JSONObject();
			one.put("tid", tagsArr.getJSONObject(i).getInt("id"));
			one.put("aid", articleArr.getJSONObject(0).getInt("id"));
			res.add(one);
		}
		JSONObject condition = new JSONObject();
		condition.put("articles_tags",res);
		//4.ִ��������������������
		new ArticlesTagsDao().insertSome(condition);
		//5.�ָ��������signֵΪ0
		JSONObject conditionObj = new JSONObject();
		conditionObj.put("mark", "updateOne");
		new TagsDao().updateOne(conditionObj);
		new ArticlesDao().updateOne(conditionObj);
		//���
		return null;
	}

}
