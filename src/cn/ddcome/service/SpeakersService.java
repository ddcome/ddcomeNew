package cn.ddcome.service;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.ManagersDao;
import cn.ddcome.dao.SpeakersDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import net.sf.json.JSONObject;

public class SpeakersService implements ServiceImpl{
	public static SpeakersService speakersService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static SpeakersService getInstance() {
		if(speakersService==null) {
			speakersService = new SpeakersService();
		}
		return speakersService;
	}
	
	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
		System.out.println(condition);
	}
	
	/**
	 * ≤È—Ø
	 * @return
	 */
	public JSONObject select() {
		JSONObject result = new SpeakersDao().selectAll();
		System.out.println(result);
		return result;
	}
	
	public JSONObject selectOneByOpenId() {
		JSONObject result = new SpeakersDao().selectOneByOpenId(this.condition);
		System.out.println(result);
		return result;
	}
	
	public JSONObject insertQQUser(JSONObject qqUserInfo) {
		JSONObject result = new SpeakersDao().insertOneByQQ(qqUserInfo);
		return result;
	}
}
