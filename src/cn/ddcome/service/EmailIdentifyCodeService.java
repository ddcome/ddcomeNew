package cn.ddcome.service;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ddcome.dao.EmailIdentifyCodeDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EmailIdentifyCodeService implements ServiceImpl{
	public static EmailIdentifyCodeService emailService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static EmailIdentifyCodeService getInstance() {
		if(emailService==null) {
			emailService = new EmailIdentifyCodeService();
		}
		return emailService;
	}
	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
		System.out.println(condition);
	}

	public JSONObject select(HttpServletResponse resp) {
		JSONObject result = new JSONObject();
		JSONObject res = new EmailIdentifyCodeDao().selectOne(condition);
		String code = this.condition.getString("code");
		String email = this.condition.getString("email");
		JSONArray jsonArray = res.getJSONArray("data");
		if(jsonArray.size()==1) {
			if(jsonArray.getJSONObject(0).getString("identify_code").equals(code)) {
				result.put("pass", true);
				//Ìí¼Ócookie
				Cookie email_authority = new Cookie("email_authority",UUID.randomUUID().toString()); 
				email_authority.setPath("/");
				resp.addCookie(email_authority);
				return result;
			}			
			result.put("pass", false);
		}
		return result;
	}
	
	/**
	 * insert²Ù×÷
	 * 
	 * @return
	 */
	public JSONObject insert(String identifyCode) {
		this.condition = new JSONObject();
		this.condition.put("identifyCode", identifyCode);
		
		EmailIdentifyCodeDao emailIdentifyCodeDao = new EmailIdentifyCodeDao();
		JSONObject res = emailIdentifyCodeDao.selectOne(condition);
		JSONArray resArr = res.getJSONArray("data");
		JSONObject result;
		if(resArr.size()==0) {
			result = emailIdentifyCodeDao.insertOne(condition);
		}else {
			result = emailIdentifyCodeDao.updateOne(condition);
		}
		return result;
	}
}
