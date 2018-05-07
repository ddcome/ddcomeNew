package cn.ddcome.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.ddcome.daoImpl.TableDaoImpl;
import cn.ddcome.data.JSONArrayResults;
import cn.ddcome.util.MysqlBeautyUtil;
import cn.ddcome.util.MysqlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SpeakersDao  implements Serializable,TableDaoImpl{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SpeakersDao.class);

	@Override
	public JSONObject selectAll() {
		String sql = "SELECT "
				+ "id,"
				+ "ip,"
				+ "avatar_url_qq,"
				+ "address_qq,"
				+ "email,"
				+ "name_qq,"
				+ "open_id_qq,"
				+ "token_qq,"
				+ "user_code_qq,"
				+ "sex_qq,"
				+ "birthday_qq,"
				+ "website,"
				+ "latest_visit_time,"
				+ "first_visit_time,"
				+ "visit_times,"
				+ "is_qq_user,"
				+ "status,"
				+ "sign "
				+ "FROM speakers WHERE status=1";
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

	public JSONObject selectOneByOpenId(JSONObject condition) {
		String sql = "SELECT "
				+ "id,"
				+ "ip,"
				+ "avatar_url_qq,"
				+ "address_qq,"
				+ "email,"
				+ "name_qq,"
				+ "open_id_qq,"
				+ "token_qq,"
				+ "user_code_qq,"
				+ "sex_qq,"
				+ "birthday_qq,"
				+ "website,"
				+ "latest_visit_time,"
				+ "first_visit_time,"
				+ "visit_times,"
				+ "is_qq_user,"
				+ "status,"
				+ "sign "
				+ "FROM speakers WHERE status=1 AND open_id_qq='"+condition.getString("openId")+"'";
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

	@Override
	public JSONObject selectSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject insertSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject insertOne(JSONObject condition) {
		return null;
	}
	
	public JSONObject insertOneByQQ(JSONObject condition) {
		String sql = "INSERT INTO speakers (" 
				+ "ip,"
				+ "avatar_url_qq,"
				+ "address_qq,"
				+ "name_qq,"
				+ "open_id_qq,"
				+ "token_qq,"
				+ "user_code_qq,"
				+ "sex_qq,"
				+ "birthday_qq,"
				+ "latest_visit_time,"
				+ "first_visit_time,"
				+ "visit_times"
				+ ") " 
				+ "VALUES (?,?,?,?,?,?,?,?,?,now(),now(),1)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "");
			pstmt.setString(2, condition.getString("imageUrl"));
			pstmt.setString(3, "");
			pstmt.setString(4, condition.getString("name"));
			pstmt.setString(5, condition.getString("openID"));
			pstmt.setString(6, "");
			pstmt.setString(7, "");
			pstmt.setString(8, condition.getString("sex"));
			pstmt.setString(9, "");

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject updateSome(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
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
