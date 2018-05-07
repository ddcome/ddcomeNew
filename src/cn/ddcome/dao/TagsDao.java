package cn.ddcome.dao;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.ddcome.daoImpl.TableDaoImpl;
import cn.ddcome.data.JSONArrayResults;
import cn.ddcome.util.MysqlBeautyUtil;
import cn.ddcome.util.MysqlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TagsDao implements Serializable, TableDaoImpl {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TagsDao.class);

	@Override
	public JSONObject selectAll() {
		String sql = "SELECT id,tag,status,sign FROM tags WHERE status=1";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(1, result).values();
	}

	@Override
	public JSONObject selectOne(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * ���ݹ������ѯTag
	 * @param aid
	 * @return
	 */
	public JSONObject selectSomeByAid(int aid) {
		String sql = "SELECT "
				+ "tag "
				+ "FROM tags "
				+ "WHERE id IN "
				+ "(SELECT tid FROM articles_tags WHERE aid=?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, aid);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		System.out.println(result);
		return new JSONArrayResults(1, result).values();
	}

	@Override
	public JSONObject selectSome(JSONObject condition) {
		int sign = condition.getInt("sign");
		
		String sql = "SELECT id,tag,status,sign FROM tags WHERE status=1 AND sign="+sign;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			result = MysqlBeautyUtil.resultSetToJSONArray(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MysqlBeautyUtil.close();
		}
		System.out.println(result);
		return new JSONArrayResults(1, result).values();
	}

	@Override
	public JSONObject insertSome(JSONObject condition) {
		Long begin = new Date().getTime();
		String sql = "INSERT INTO tags (tag) VALUES (?)";
		//����˴�insert����Ҫ����sign�򸲸�sqlֵ
		if(condition.getString("mark")=="insertSome") {
			sql = "INSERT INTO tags (tag,sign) VALUES (?,?)";
		} else {//TODO ��Ҫ�޸�
			return null;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			JSONArray tags = condition.getJSONArray("tags");
			System.out.println(tags.toString());
			for (int i = 0; i < tags.size(); i++) {
				pstmt.setString(1, tags.get(i).toString());
				pstmt.setInt(2, condition.getInt("sign"));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// ����ع�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// ��¼��־
				e1.printStackTrace();
				// �׳��쳣
				throw new RuntimeException("����ع�ʧ��", e);
			}
		}
		Long end = new Date().getTime();
		System.out.println("cast : " + (end - begin) / 1000 + " s");
		return null;
	}

	@Override
	public JSONObject insertOne(JSONObject condition) {
		String sql = "INSERT INTO tags (" + "tag" + ") " + "VALUES (?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("tag"));

			pstmt.executeUpdate();
			conn.commit();// �ύ����
			return new JSONArrayResults(1, null).values();// �ɹ�����1
		} catch (SQLException e) {
			e.printStackTrace();
			// ����ع�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// ��¼��־
				e1.printStackTrace();
				// �׳��쳣
				throw new RuntimeException("����ع�ʧ��", e);
			}
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(0, null).values();// ʧ��
	}

	@Override
	public JSONObject updateOne(JSONObject condition) {
		String sql = null;
		
		JSONArray tags = null;
		int sign = 0;
		String mark = condition.getString("mark");
		if("...".equals(mark)) {//TODO ��Ҫ�޸�
			
		} else if("updateOne".equals(mark)) {	//����tag����sign
			sql = "UPDATE tags SET sign=0 WHERE sign=777";
		}
		
		//System.out.println(tags.toString());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// ����ع�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// ��¼��־
				e1.printStackTrace();
				// �׳��쳣
				throw new RuntimeException("����ع�ʧ��", e);
			}
		}
		return null;
	}

	@Override
	public JSONObject updateSome(JSONObject condition) {
		Long begin = new Date().getTime();
		String sql = null;
		
		JSONArray tags = null;
		int sign = 0;
		String mark = condition.getString("mark");
		if("...".equals(mark)) {
			
		} else if("updateSome".equals(mark)) {	//����tag����sign
			sql = "UPDATE tags SET sign=? WHERE tag IN (";
			sign = condition.getInt("sign");
			tags = condition.getJSONArray("tags");
			
			for(int i=0; i<tags.size(); i++) {
				sql += "'"+tags.get(i)+"'";
				if(i<tags.size()-1) {
					sql += ",";
				}
			}
			sql += ")";
			
		} else if(".......".equals(mark)) {	//����sign����sign
			sql = "UPDATE tags SET sign=? WHERE sign IN (?)";
			sign = condition.getInt("sign");
			tags = condition.getJSONArray("tags");
		}
		
		System.out.println(tags.toString());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sign);
			pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			// ����ع�
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// ��¼��־
				e1.printStackTrace();
				// �׳��쳣
				throw new RuntimeException("����ع�ʧ��", e);
			}
		}
		Long end = new Date().getTime();
		System.out.println("cast : " + (end - begin) / 1000 + " s");
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(selectAll());
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		return new JSONObject();
	}

	@Override
	public JSONObject deleteOne(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject deleteSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

}
