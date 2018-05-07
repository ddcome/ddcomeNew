package cn.ddcome.dao;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import cn.ddcome.daoImpl.TableDaoImpl;
import cn.ddcome.data.JSONArrayResults;
import cn.ddcome.util.MyCommonUtil;
import cn.ddcome.util.MysqlBeautyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ManagersDao implements Serializable,TableDaoImpl{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ManagersDao.class);

	@Override
	public JSONObject selectAll() {
		String sql = "SELECT "
				+ "id,"
				+ "image_url,"
				+ "username,"
				+ "password,"
				+ "rank,"
				+ "birthday,"
				+ "email,"
				+ "github,"
				+ "latest_login_ip,"
				+ "latest_login_time,"
				+ "status,"
				+ "sign "
				+ "FROM managers WHERE status=1";
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

	public JSONObject selectOne(String value) {
		String sql = "SELECT "
				+ "id,"
				+ "image_url,"
				+ "username,"
				+ "password,"
				+ "status,"
				+ "sign "
				+ "FROM managers WHERE status=1 AND username='"+value+"'";
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
		String sql = "INSERT INTO managers (" 
				+ "image_url,"
				+ "username,"
				+ "password,"
				+ "rank,"
				+ "birthday,"
				+ "email,"
				+ "github"
				+ ") " 
				+ "VALUES ('',?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("username"));
			pstmt.setString(2, MyCommonUtil.EncoderByMd5(condition.getString("password")));
			pstmt.setString(3, condition.getString("rank"));
			pstmt.setString(4, MyCommonUtil.beautySQLValue(condition,"birthday"));
			pstmt.setString(5, MyCommonUtil.beautySQLValue(condition,"email"));
			pstmt.setString(6, MyCommonUtil.beautySQLValue(condition,"github"));
			
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
				logger.error(e1.getMessage());
				e1.printStackTrace();
				// 抛出异常
				throw new RuntimeException("事务回滚失败", e);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
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
