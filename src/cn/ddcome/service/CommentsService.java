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
	 * 查询未被审核的评论
	 * 
	 * @return
	 */
	public JSONObject selectSome() {
		return new CommentsDao().selectSome();
	}
	
	/**
	 * 新增Comments数据
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
	 * 查询所有评论，
	 * 需要按照文章关联的aid去查询。
	 * 1.查询所有;
	 * 2.获取aid下的所有评论含有描述信息;
	 * 该方法返回value，key建议使用reply，然后put到一条文章数据中即可
	 * @param aid
	 * @return
	 */
	public JSONArray select(int aid) {
		//返回值
		JSONArray res = new JSONArray();
		
		//步骤1
		JSONObject condition = new JSONObject();
		condition.put("aid", aid);
		JSONObject result = new CommentsDao().selectSome(condition);
		JSONArray resArr = result.getJSONArray("data");
		System.out.println(resArr);
		
		//步骤2
		res = createDescribe(resArr,aid);
		
		res = cleanCommentsData(res);

		return res;
	}
	
	/**
	 * 为该id的文章下所有的评论信息
	 * 创建描述信息，例如“A对B说：”
	 * @param res
	 * @param aid
	 * @return
	 */
	public JSONArray createDescribe(JSONArray res,int aid) {
		//处理每一条
		for(int i=0; i<res.size(); i++) {
			JSONObject one = res.getJSONObject(i);
			int speakWith = one.getInt("speak_with");
			int idOne = one.getInt("id");
			
			if(speakWith==-1) {
				res.getJSONObject(i).put("describe", one.getString("speaker")+"：");
				
				continue;
			}
			//add描述信息
			for(int j=0; j<res.size(); j++) {
				JSONObject oneOther = res.getJSONObject(j);
				if(speakWith==oneOther.getInt("id")) {
					res.getJSONObject(i).put("describe", one.getString("speaker")+" Reply "+oneOther.getString("speaker")+"：");
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
			
			if(speakWith==-1) {//当speakWith为-1时，数据则是一条顶级评论
				//one.put("reply_array", "");
				//add描述信息
				
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
