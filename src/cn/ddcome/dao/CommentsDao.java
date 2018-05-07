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

public class CommentsDao implements Serializable,TableDaoImpl{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CommentsDao.class);

	@Override
	public JSONObject selectAll() {
		String sql = "SELECT id,aid,is_top,speaker,words,speak_with,time,status,sign FROM comments WHERE status=1";
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
	 * ��ѯδ����˵�����
	 * �Ƿ������ͨ��sign�Ƿ����0Ĭ��ֵ�жϣ�
	 * �������0����˵�����Ѿ���˵ģ�
	 * ���������0����˵����δ����˵ġ�
	 * @return
	 */
	public JSONObject selectSome() {
		String sql = "SELECT "
				+ "id,"
				+ "aid,"
				+ "is_top,"
				+ "speaker,"
				+ "words,"
				+ "speak_with,"
				+ "belong_to,"
				+ "time,"
				+ "status,"
				+ "sign "
				+ "FROM comments "
				+ "WHERE status=1 AND sign!=0 "
				+ "ORDER BY time DESC";
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
	public JSONObject selectSome(JSONObject condition) {
		int aid = condition.getInt("aid");
		
		String sql = "SELECT "
				+ "id,"
				+ "aid,"
				+ "is_top,"
				+ "speaker,"
				+ "words,"
				+ "speak_with,"
				+ "belong_to,"
				+ "time,"
				+ "status,"
				+ "sign "
				+ "FROM comments "
				+ "WHERE status=1 AND aid=? "
				+ "ORDER BY time ASC";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		JSONArray result = null;
		System.out.println(sql);

		try {
			conn = MysqlBeautyUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,aid);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject insertOne(JSONObject condition) {
		String sql = "INSERT INTO comments (" 
				+ "aid,is_top,speaker,words,speak_with,belong_to,time,sign" 
				+ ") " 
				+ "VALUES (?,?,?,?,?,?,now(),-100)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, condition.getString("aid"));
			pstmt.setString(2, condition.getString("isTop"));
			pstmt.setString(3, condition.getString("speaker"));
			pstmt.setString(4, condition.getString("words"));
			pstmt.setString(5, condition.getString("id"));
			pstmt.setString(6, condition.getString("belongTo"));

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
