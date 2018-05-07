package cn.ddcome.service;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.VisitIPCollectionDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import cn.ddcome.test.TestGetIP;
import net.sf.json.JSONObject;

public class VisitIPCollectionService implements ServiceImpl{
	public static VisitIPCollectionService visitIPCollectionService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static VisitIPCollectionService getInstance() {
		if(visitIPCollectionService==null) {
			visitIPCollectionService = new VisitIPCollectionService();
		}
		return visitIPCollectionService;
	}

	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
		//this.condition.put("ip", TestGetIP.getIpAddr(req));
	}
	
	public JSONObject insert() {
		VisitIPCollectionDao visitIPCollectionDao = new VisitIPCollectionDao();
		//这里新增一条记录有条件的，必须是sign在库里是唯一的
		JSONObject resultBySelect = visitIPCollectionDao.selectOne(this.condition);
		if(resultBySelect.getJSONArray("data").size()!=0) {
			return null;
		}
		
		JSONObject result = visitIPCollectionDao.insertOne(this.condition);
		return result;
	}

}
