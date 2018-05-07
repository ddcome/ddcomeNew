package cn.ddcome.service;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.CommentsDao;
import cn.ddcome.dao.TagsDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CommentsService implements ServiceImpl{

	public static CommentsService commentsService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static CommentsService getInstance() {
		if(commentsService==null) {
			commentsService = new CommentsService();
		}
		return commentsService;
	}
	
	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
		System.out.println(condition);
	}
	
	/**
	 * ��ѯδ����˵�����
	 * 
	 * @return
	 */
	public JSONObject selectSome() {
		return new CommentsDao().selectSome();
	}
	
	/**
	 * ����Comments����
	 * 
	 * @return
	 */
	public JSONObject insert() {
		System.out.println("===========this.condition===========");
		System.out.println(this.condition);
		this.condition.getString("id");
		this.condition.getString("aid");
		this.condition.getString("speaker");
		
		return new CommentsDao().insertOne(condition);
	}
	
	/**
	 * ��ѯ�������ۣ�
	 * ��Ҫ�������¹�����aidȥ��ѯ��
	 * 1.��ѯ����;
	 * 2.��ȡaid�µ��������ۺ���������Ϣ;
	 * �÷�������value��key����ʹ��reply��Ȼ��put��һ�����������м���
	 * @param aid
	 * @return
	 */
	public JSONArray select(int aid) {
		//����ֵ
		JSONArray res = new JSONArray();
		
		//����1
		JSONObject condition = new JSONObject();
		condition.put("aid", aid);
		JSONObject result = new CommentsDao().selectSome(condition);
		JSONArray resArr = result.getJSONArray("data");
		System.out.println(resArr);
		
		//����2
		res = createDescribe(resArr,aid);
		
		res = cleanCommentsData(res);

		return res;
	}
	
	/**
	 * Ϊ��id�����������е�������Ϣ
	 * ����������Ϣ�����硰A��B˵����
	 * @param res
	 * @param aid
	 * @return
	 */
	public JSONArray createDescribe(JSONArray res,int aid) {
		//����ÿһ��
		for(int i=0; i<res.size(); i++) {
			JSONObject one = res.getJSONObject(i);
			int speakWith = one.getInt("speak_with");
			int idOne = one.getInt("id");
			
			if(speakWith==-1) {
				res.getJSONObject(i).put("describe", one.getString("speaker")+"��");
				
				continue;
			}
			//add������Ϣ
			for(int j=0; j<res.size(); j++) {
				JSONObject oneOther = res.getJSONObject(j);
				if(speakWith==oneOther.getInt("id")) {
					res.getJSONObject(i).put("describe", one.getString("speaker")+" Reply "+oneOther.getString("speaker")+"��");
					break;
				}
			}
		}
		return res;
	}
	
	public JSONArray cleanCommentsData(JSONArray res) {
		JSONArray results = new JSONArray();
		
		for(int i=0; i<res.size(); i++) {
			JSONObject one = res.getJSONObject(i);
			int speakWith = one.getInt("speak_with");
			int idOne = one.getInt("id");
			
			if(speakWith==-1) {//��speakWithΪ-1ʱ����������һ����������
				//one.put("reply_array", "");
				//add������Ϣ
				
				JSONArray replyArr = new JSONArray();
				for(int j=0; j<res.size(); j++) {
					JSONObject oneOther = res.getJSONObject(j);
					if(idOne==oneOther.getInt("belong_to")) {
						replyArr.add(oneOther);
					}
				}
				one.put("reply_array", replyArr);
				results.add(one);
			}
		}
		return results;
	}

	

}
