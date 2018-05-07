package cn.ddcome.daoImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Table表操作接口
 * 
 * @author ddcome
 *
 */
public interface TableDaoImpl {

	/**
	 * 查询所有数据
	 * @return
	 */
	public JSONObject selectAll();
	
	/**
	 * 查询一条数据
	 * @param condition
	 * @return
	 */
	public JSONObject selectOne(JSONObject condition);
	
	/**
	 * 查询一批数据
	 * @param condition
	 * @return
	 */
	public JSONObject selectSome(JSONObject condition);
	
	/**
	 * 插入一批数据
	 * @param condition
	 * @return
	 */
	public JSONObject insertSome(JSONObject condition);
	
	/**
	 * 插入一条数据
	 * @param condition
	 * @return
	 */
	public JSONObject insertOne(JSONObject condition);
	
	/**
	 * 更新一条数据
	 * @param condition
	 * @return
	 */
	public JSONObject updateOne(JSONObject condition);
	
	/**
	 * 更新一批数据
	 * @param condition
	 * @return
	 */
	public JSONObject updateSome(JSONObject condition);
	
	/**
	 * 删除一条数据
	 * @param condition
	 * @return
	 */
	public JSONObject deleteOne(JSONObject condition);
	
	/**
	 * 删除一批数据
	 * @param condition
	 * @return
	 */
	public JSONObject deleteSome(JSONObject condition);
	
}
