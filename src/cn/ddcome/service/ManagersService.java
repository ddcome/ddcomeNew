package cn.ddcome.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.ManagersDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import cn.ddcome.util.MyCommonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ManagersService implements ServiceImpl{
	public static ManagersService managersService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static ManagersService getInstance() {
		if(managersService==null) {
			managersService = new ManagersService();
		}
		return managersService;
	}
	
	@Override
	public void init(HttpServletRequest req) {
		JSONObject data = JSONObject.fromObject(req.getParameter("params")).getJSONObject("data");
		this.condition = data;
		System.out.println(condition);
	}
	
	/**
	 * 查询
	 * 
	 * @return
	 */
	public JSONObject select() {
		JSONObject result = new ManagersDao().selectAll();
		System.out.println(result);
		return result;
	}
	
	/**
	 * 新增数据
	 * 
	 * @return
	 */
	public JSONObject insert() {
		JSONObject result = new ManagersDao().insertOne(this.condition);
		System.out.println(result);
		return result;
	}
	
	/**
	 * 登录操作
	 * 
	 * @return
	 */
	public JSONObject login() {
		String username = this.condition.getString("username");
		JSONObject result = new ManagersDao().selectOne(username);
		String password;
		try {
			password = MyCommonUtil.EncoderByMd5(this.condition.getString("password"));
			//开始判断
			JSONArray resArr = result.getJSONArray("data");
			if(resArr.size()==0) {
				result.put("error", "用户名不存在");
				result.put("code", 0);
			} else {
				if(!resArr.getJSONObject(0).getString("password").equals(password)) {
					result.put("error", "密码错误");
					result.put("code", 1);
				} else {
					result.put("code", 2);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
