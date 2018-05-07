package cn.ddcome.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import cn.ddcome.daoImpl.TableDaoImpl;
import cn.ddcome.data.JSONArrayResults;
import cn.ddcome.util.MysqlBeautyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArticlesTagsDao implements Serializable,TableDaoImpl{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ArticlesTagsDao.class);
	
	@Override
	public JSONObject selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject selectOne(JSONObject condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject selectSome(JSONObject condition) {
		return null;
	}

	@Override
	public JSONObject insertSome(JSONObject condition) {
		Long begin = new Date().getTime();
		String sql = "INSERT INTO articles_tags (aid,tid) VALUES (?,?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			JSONArray artagsArr = condition.getJSONArray("articles_tags");
			System.out.println(artagsArr.toString());
			for (int i = 0; i < artagsArr.size(); i++) {
				pstmt.setInt(1, artagsArr.getJSONObject(i).getInt("aid"));
				pstmt.setInt(2, artagsArr.getJSONObject(i).getInt("tid"));
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
		String sql = "INSERT INTO articles_tags (" +
				"aid," + 
				"tid" +
				") " +
				"values (?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,condition.getString("aid"));
			pstmt.setString(2,condition.getString("tid"));
			
			pstmt.executeUpdate();
			conn.commit();//提交事务
			return new JSONArrayResults(1, null).values();//成功返回1
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
			//事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				//记录日志
				e1.printStackTrace();
				logger.error(e.getMessage()); 
				//抛出异常
				throw new RuntimeException("事务回滚失败",e);
			}
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(0, null).values();//失败
	}

	@Override
	public JSONObject updateOne(JSONObject condition) {

		String sql = "UPDATE articles_tags SET " +
				"aid=?," + 
				"tid=? " +
				"WHERE id=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = MysqlBeautyUtil.getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,condition.getString("aid"));
			pstmt.setString(2,condition.getString("tid"));
			pstmt.setString(3,condition.getString("id"));
			
			pstmt.executeUpdate();
			conn.commit();//提交事务
			return new JSONArrayResults(1, null).values();//成功返回1
		} catch (SQLException e) {
			//e.printStackTrace();
			logger.error(e.getMessage());
			//事务回滚
			try {
				conn.rollback();
			} catch (SQLException e1) {
				//记录日志
				e1.printStackTrace();
				logger.error(e.getMessage()); 
				//抛出异常
				throw new RuntimeException("事务回滚失败",e);
			}
		} finally {
			MysqlBeautyUtil.close();
		}
		return new JSONArrayResults(0, null).values();//失败
		
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
