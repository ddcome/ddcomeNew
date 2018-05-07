package cn.ddcome.daoImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Table������ӿ�
 * 
 * @author ddcome
 *
 */
public interface TableDaoImpl {

	/**
	 * ��ѯ��������
	 * @return
	 */
	public JSONObject selectAll();
	
	/**
	 * ��ѯһ������
	 * @param condition
	 * @return
	 */
	public JSONObject selectOne(JSONObject condition);
	
	/**
	 * ��ѯһ������
	 * @param condition
	 * @return
	 */
	public JSONObject selectSome(JSONObject condition);
	
	/**
	 * ����һ������
	 * @param condition
	 * @return
	 */
	public JSONObject insertSome(JSONObject condition);
	
	/**
	 * ����һ������
	 * @param condition
	 * @return
	 */
	public JSONObject insertOne(JSONObject condition);
	
	/**
	 * ����һ������
	 * @param condition
	 * @return
	 */
	public JSONObject updateOne(JSONObject condition);
	
	/**
	 * ����һ������
	 * @param condition
	 * @return
	 */
	public JSONObject updateSome(JSONObject condition);
	
	/**
	 * ɾ��һ������
	 * @param condition
	 * @return
	 */
	public JSONObject deleteOne(JSONObject condition);
	
	/**
	 * ɾ��һ������
	 * @param condition
	 * @return
	 */
	public JSONObject deleteSome(JSONObject condition);
	
}
