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
	 * 根据关联表查询Tag
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
		//如果此次insert操作要求标记sign则覆盖sql值
		if(condition.getString("mark")=="insertSome") {
			sql = "INSERT INTO tags (tag,sign) VALUES (?,?)";
		} else {//TODO 需要修改
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
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// 记录日志
				e1.printStackTrace();
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
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
			conn.commit();// 提交事务
			return new JSONArrayResults(1, null).values();// 成功返回1
		} catch (SQLException e) {
			e.printStackTrace();
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// 记录日志
				e1.printStackTrace();
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
			}
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(0, null).values();// 失败
	}

	@Override
	public JSONObject updateOne(JSONObject condition) {
		String sql = null;
		
		JSONArray tags = null;
		int sign = 0;
		String mark = condition.getString("mark");
		if("...".equals(mark)) {//TODO 需要修改
			
		} else if("updateOne".equals(mark)) {	//根据tag更新sign
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
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// 记录日志
				e1.printStackTrace();
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
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
			
		} else if("updateSome".equals(mark)) {	//根据tag更新sign
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
			
		} else if(".......".equals(mark)) {	//根据sign更新sign
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
			// 事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// 记录日志
				e1.printStackTrace();
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
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
