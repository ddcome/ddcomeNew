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

public class VisitIPCollectionDao implements Serializable,TableDaoImpl {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(VisitIPCollectionDao.class);

	@Override
	public JSONObject selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject selectOne(JSONObject condition) {
		String sql = "SELECT "
				+ "id,"
				+ "ip,"
				+ "place,"
				+ "time,"
				+ "sign "
				+ "FROM visit_ip_collection WHERE sign=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("sign"));
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
		String sql = "INSERT INTO visit_ip_collection (" 
				+ "ip,place,time,sign" 
				+ ") " 
				+ "VALUES (?,?,now(),?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("ip"));
			pstmt.setString(2, condition.getString("place"));
			pstmt.setString(3, condition.getString("sign"));

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
