package cn.ddcome.data;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 统一的返回值JSONArray Results
 * @author Administrator
 *
 */
public class JSONArrayResults {
	private String status= null;
	private JSONArray data = null;
	
	public JSONArrayResults(int status, JSONArray data) {
		this.status = status==0?"fail":"success";
		this.data = data;
	}
	
	public JSONObject values() {
		JSONObject val = new JSONObject();
		val.put("status", this.status);
		val.put("data", data);
		return val;
	}
}
