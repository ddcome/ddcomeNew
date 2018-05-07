package cn.ddcome.service;

import javax.servlet.http.HttpServletRequest;

import cn.ddcome.dao.BooksDao;
import cn.ddcome.dao.TagsDao;
import cn.ddcome.dao.WordsDao;
import cn.ddcome.serviceImpl.ServiceImpl;
import net.sf.json.JSONObject;

public class BooksService implements ServiceImpl{
	public static BooksService booksService = null;
	public JSONObject condition = null;

	public JSONObject getCondition() {
		return condition;
	}

	public void setCondition(JSONObject condition) {
		this.condition = condition;
	}

	public static BooksService getInstance() {
		if(booksService==null) {
			booksService = new BooksService();
		}
		return booksService;
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
		JSONObject result = new BooksDao().selectAll();
		System.out.println(result);
		return result;
	}
	
	public JSONObject insert() {
		JSONObject result = new BooksDao().insertOne(this.condition);
		System.out.println(result);
		return result;
	}

}
