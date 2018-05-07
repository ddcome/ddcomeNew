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

public class WordsDao implements Serializable,TableDaoImpl {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WordsDao.class);

	@Override
	public JSONObject selectAll() {
		String sql = "SELECT "
				+ "id,"
				+ "word,"
				+ "time,"
				+ "place,"
				+ "status,"
				+ "sign "
				+ "FROM words WHERE status=1";
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
		String sql = "INSERT INTO words (" 
				+ "word,time,place" 
				+ ") " 
				+ "VALUES (?,now(),?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("word"));
			pstmt.setString(2, condition.getString("place"));

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
