package cn.ddcome.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apdplat.word.segmentation.Word;

import cn.ddcome.dao.ArticlesDao;
import cn.ddcome.dao.ArticlesTagsDao;
import cn.ddcome.dao.TagsDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import cn.ddcome.util.WordSegmenter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArticlesService implements ServiceImpl{
	public static ArticlesService articlesService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}
	
	public static ArticlesService getInstance() {
		if(articlesService==null) {
			articlesService = new ArticlesService();
		}
		return articlesService;
	}
	
	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
	}

	public JSONObject search() {
		//step1.��ȡ����
		String searchWords = condition.getString("searchWords");
		//step2.����Word�ִ�
		List<Word> words = WordSegmenter.segWithStopWords(searchWords);
		//step3.ƴ��SQL��WHERE�Ӿ�
		String sqlWhere = " WHERE (";
		for(int i=0; i<words.size(); i++) {
			sqlWhere += "tag LIKE '%"+words.get(i)+"%'";
			if(i<words.size()-1) {
				sqlWhere += " OR ";
			}
		}
		sqlWhere += ") AND status=1";
		//step4.����search����
		JSONObject res = new ArticlesDao().search(sqlWhere);
		
		return res;
	}
	
	/**
	 * ��ѯ���µ�limitNum������
	 * 
	 * @param limitNum
	 * @return
	 */
	public JSONObject select(int limitNum) {
		JSONObject result = new ArticlesDao().selectSome(limitNum);
		return result;
	}
	
	/**
	 * ��ѯ
	 * @return
	 */
	public JSONObject select() {
		JSONObject result = new ArticlesDao().selectAll();
		return result;
	}
	
	public JSONObject selectAllDefault() {
		JSONObject result = new ArticlesDao().selectAllDefault();
		return result;
	}
	
	public JSONObject selectAllPDF() {
		JSONObject result = new ArticlesDao().selectAllPDF();
		return result;
	}
	
	/**
	 * ����һ�����µ�����
	 * �ò�����һ�������ԵĲ���
	 * 1.����һ�����µ�����
	 * 2.��tags��������
	 * 		2.1.���������ݣ���ִ��insert����
	 * 		2.2.���������ݣ���ִ��update����
	 * 		2.3.��ѯsign=777�����ݵ�ID
	 * 3.��ѯarticle��signΪ777�����ݵ�ID
	 * 4.���������������ڱ�artilce_tag��
	 * 5.update��article�ֶ�sign
	 * 6.update��tags�ֶ�sign
	 * @return
	 */
	public JSONObject insert() {
		//��ȡ�ֶ�ֵ
		String title = condition.getString("title");
		String author = condition.getString("author");
		String content = condition.getString("content");
		JSONObject tags = condition.getJSONObject("tags");
		JSONArray oldTags = tags.getJSONArray("oldTags");
		JSONArray newTags = tags.getJSONArray("newTags");
		//����1
		JSONObject condi = new JSONObject();
		condi.put("title", title);
		condi.put("author", author);
		condi.put("file", "");
		condi.put("file_size", 0);
		condi.put("content", content);
		condi.put("browse_times", 0);
		condi.put("share_times", 0);
		condi.put("good_idea", 0);
		condi.put("type", "text");
		condi.put("sign", 777);
		new ArticlesDao().insertOne(condi);
		//����2
		TagsService.getInstance().setCondition(condition);
		TagsService.getInstance().insert();
		JSONObject tagsCondition = new JSONObject();
		tagsCondition.put("sign", 777);
		JSONObject tagsRes = new TagsDao().selectSome(tagsCondition);
		//����3
		JSONObject articlesRes = new ArticlesDao().selectSome(tagsCondition);
		//����4
		JSONObject articleTagsCondition = new JSONObject();
		JSONArray articleTagsArr = new JSONArray();
		for(int i=0; i<tagsRes.getJSONArray("data").size(); i++) {
			int tid = tagsRes.getJSONArray("data").getJSONObject(i).getInt("id");
			int aid = articlesRes.getJSONArray("data").getJSONObject(0).getInt("id");
			JSONObject obj = new JSONObject();
			obj.put("tid", tid);
			obj.put("aid", aid);
			articleTagsArr.add(obj);
		}
		articleTagsCondition.put("articles_tags", articleTagsArr);
		new ArticlesTagsDao().insertSome(articleTagsCondition);
		//����5
		JSONObject artCondi = new JSONObject();
		artCondi.put("mark", "updateOne");
		new ArticlesDao().updateOne(artCondi);
		//����6
		new TagsDao().updateOne(artCondi);
		
		return null;
	} 
	
	public JSONObject insertMd() {
		//��ȡ�ֶ�ֵ
		String title = condition.getString("title");
		String author = condition.getString("author");
		String content = condition.getString("content");
		JSONObject tags = condition.getJSONObject("tags");
		JSONArray oldTags = tags.getJSONArray("oldTags");
		JSONArray newTags = tags.getJSONArray("newTags");
		//����1
		JSONObject condi = new JSONObject();
		condi.put("title", title);
		condi.put("author", author);
		condi.put("file", "");
		condi.put("file_size", 0);
		condi.put("content", content);
		condi.put("browse_times", 0);
		condi.put("share_times", 0);
		condi.put("good_idea", 0);
		condi.put("type", "md");
		condi.put("sign", 777);
		System.out.println(condi.toString());
		new ArticlesDao().insertOne(condi);
		//����2
		TagsService.getInstance().setCondition(condition);
		TagsService.getInstance().insert();
		JSONObject tagsCondition = new JSONObject();
		tagsCondition.put("sign", 777);
		JSONObject tagsRes = new TagsDao().selectSome(tagsCondition);
		//����3
		JSONObject articlesRes = new ArticlesDao().selectSome(tagsCondition);
		//����4
		JSONObject articleTagsCondition = new JSONObject();
		JSONArray articleTagsArr = new JSONArray();
		for(int i=0; i<tagsRes.getJSONArray("data").size(); i++) {
			int tid = tagsRes.getJSONArray("data").getJSONObject(i).getInt("id");
			int aid = articlesRes.getJSONArray("data").getJSONObject(0).getInt("id");
			JSONObject obj = new JSONObject();
			obj.put("tid", tid);
			obj.put("aid", aid);
			articleTagsArr.add(obj);
		}
		articleTagsCondition.put("articles_tags", articleTagsArr);
		new ArticlesTagsDao().insertSome(articleTagsCondition);
		//����5
		JSONObject artCondi = new JSONObject();
		artCondi.put("mark", "updateOne");
		new ArticlesDao().updateOne(artCondi);
		//����6
		new TagsDao().updateOne(artCondi);
		return null;
	} 
	
	/**
	 * ����
	 * @return
	 */
	public JSONObject insertPdf(String filePath,String filename, double fileSize) {
		JSONObject condi = new JSONObject();
		condi.put("title", filename);
		condi.put("author", "ddcome");
		condi.put("file", filePath);
		condi.put("file_size", fileSize);
		condi.put("content", "");
		condi.put("browse_times", 0);
		condi.put("share_times", 0);
		condi.put("good_idea", 0);
		condi.put("type", "pdf");
		condi.put("sign", 777);
		new ArticlesDao().insertOne(condi);
		return null;
	}
	
	/**
	 * ��ѯһ��pdf������
	 * @return
	 */
	public JSONObject selectOnePdf() {
		JSONObject res = new ArticlesDao().selectOne(condition);
		JSONArray replys = CommentsService.getInstance().select(condition.getInt("id"));
		
		//׷��tagsԪ��
		JSONArray tagsArr = new TagsDao().selectSomeByAid(condition.getInt("id")).getJSONArray("data");
		String tags = "";
		for(int i=0; i<tagsArr.size(); i++) {
			tags += tagsArr.getJSONObject(i).getString("tag");
			if(i<tagsArr.size()-1) {
				tags += ",";
			}
		}
		
		res.getJSONArray("data").getJSONObject(0).put("reply", replys);
		res.getJSONArray("data").getJSONObject(0).put("tags", tags);
		return res;
	}
	
	/**
	 * ��ѯһ��txt���͵�����
	 * @return
	 */
	public JSONObject selectOneText() {
		JSONObject res = new ArticlesDao().selectOne(condition);
		JSONArray replys = CommentsService.getInstance().select(condition.getInt("id"));
		
		//׷��tagsԪ��
		JSONArray tagsArr = new TagsDao().selectSomeByAid(condition.getInt("id")).getJSONArray("data");
		String tags = "";
		for(int i=0; i<tagsArr.size(); i++) {
			tags += tagsArr.getJSONObject(i).getString("tag");
			if(i<tagsArr.size()-1) {
				tags += ",";
			}
		}
		
		res.getJSONArray("data").getJSONObject(0).put("reply", replys);
		res.getJSONArray("data").getJSONObject(0).put("tags", tags);
		return res;
	}

	public JSONObject selectOneMarkdown() {
		JSONObject res = new ArticlesDao().selectOne(condition);
		JSONArray replys = CommentsService.getInstance().select(condition.getInt("id"));

		//׷��tagsԪ��
		JSONArray tagsArr = new TagsDao().selectSomeByAid(condition.getInt("id")).getJSONArray("data");
		String tags = "";
		for(int i=0; i<tagsArr.size(); i++) {
			tags += tagsArr.getJSONObject(i).getString("tag");
			if(i<tagsArr.size()-1) {
				tags += ",";
			}
		}

		res.getJSONArray("data").getJSONObject(0).put("reply", replys);
		res.getJSONArray("data").getJSONObject(0).put("tags", tags);
		return res;
	}
	
	
}